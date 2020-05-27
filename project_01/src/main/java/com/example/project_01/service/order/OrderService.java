package com.example.project_01.service.order;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_01.model.cart.dto.CartDTO;
import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
import com.example.project_01.model.order.dto.PaymentInfo;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.stock.dao.StockDAO;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Service
public class OrderService {
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	OrderDAO orderDao;
	@Autowired
	MemberDAO memberDao;
	@Value("${rest_key}")
	String rest_key;
	@Value("${secret_key}")
	String secret_key;

	// 품절 체크
	public List<CartDTO> checkSoldOut(int[] size, int[] product, int[] count) {
		List<CartDTO> soldOutList = new ArrayList<>();
		for (int i = 0; i < product.length; i++) {
			int stock = stockDao.getStock(product[i], size[i]);
			if (stock < count[i]) {
				CartDTO dto = new CartDTO();
				dto.setCart_count(stock);
				dto.setCart_size(size[i]);
				dto.setProduct_name(productDao.selectOne(product[i]).getProduct_name());
				soldOutList.add(dto);
			}
		}
		return soldOutList;
	}

	// 구매하려는 상품정보를 받아서 orderList로 반환
	public List<CartDTO> getOrderList(int[] product, int[] size, int[] count) {
		List<CartDTO> orderList = new ArrayList<>();
		for (int i = 0; i < product.length; i++) {
			ProductDTO productDto = productDao.selectProductDTO(product[i]);
			CartDTO cartDto = new CartDTO();
			cartDto.setCart_product(productDto.getProduct_idx());
			cartDto.setProduct_name(productDto.getProduct_name());
			cartDto.setCart_size(size[i]);
			cartDto.setCart_count(count[i]);
			cartDto.setProduct_brand(productDto.getProduct_brand());
			cartDto.setProduct_image(productDto.getProduct_image());
			cartDto.setProduct_price(productDto.getProduct_price());
			orderList.add(cartDto);
		}
		return orderList;
	}
	
	//결제 요청 시 주문요청을 DB에 넣고, 결제 정보를 담아 Payment 객체 반환
	@Transactional
	public PaymentInfo insertOrder(MemberDTO memberDto, int[] size, int[] count, int[] product) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String time = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		String member_idx = Integer.toString(memberDao.findById(memberDto.getMem_id()).getMem_idx());
		OrderDTO[] orderList = new OrderDTO[product.length];
		String merchant_uid = memberDto.getMem_id() + time;
		int amount = 0;
		for (int i = 0; i < orderList.length; i++) {
			orderList[i] = new OrderDTO();
			orderList[i].setProduct_idx(product[i]);
			orderList[i].setSize(size[i]);
			orderList[i].setCount(count[i]);
			orderList[i].setMem_id(memberDto.getMem_id());
			orderList[i].setMem_name(memberDto.getMem_name());
			orderList[i].setOrder_addr1(memberDto.getMem_addr1());
			orderList[i].setOrder_addr2(memberDto.getMem_addr2());
			orderList[i].setOrder_phone(memberDto.getMem_phone());
			orderList[i].setOrder_postcode(memberDto.getMem_postcode());
			orderList[i].setPay(productDao.selectOne(product[i]).getProduct_price() * count[i]);
			String orderCode = time + member_idx + product[i] + i;
			orderList[i].setOrder_code(orderCode);
			orderList[i].setMerchant_uid(merchant_uid);
			//멤버의 총 주문금액 업데이트
			memberDao.updateTotal(memberDto.getMem_id(), orderList[i].getPay());
			amount = amount + orderList[i].getPay();
		}
		//주문 테이블에 추가, 재고업데이트, 상품판매수 업데이트
		for (OrderDTO orderDto : orderList) {
			orderDao.insertOrder(orderDto);
			int stock = stockDao.getStock(orderDto.getProduct_idx(), orderDto.getSize());
			stockDao.updateStock(orderDto.getProduct_idx(), orderDto.getSize(), stock - orderDto.getCount());
			productDao.updateSaleCount(orderDto.getProduct_idx(), orderDto.getCount());
		}
		//결제정보 Payment 객체에 담고 반환
		PaymentInfo payment = new PaymentInfo();
		payment.setMerchant_uid(merchant_uid);
		payment.setBuyer_tel(memberDto.getMem_phone());
		payment.setAmount(amount);
		return payment;
	}

	// 구매 취소(한 상품 취소)
	@Transactional
	public void cancelOne(MemberDTO memberDto, OrderDTO orderDto) {
		IamportClient client = new IamportClient(rest_key, secret_key);
		//주문 취소 (DB작업)
		orderCancel(memberDto, orderDto);
		//결제취소
		String merchant_uid = orderDto.getMerchant_uid();
		getToken();
		CancelData cancel_data = new CancelData(merchant_uid, false, BigDecimal.valueOf(orderDto.getPay()));
		try {
			IamportResponse<Payment> cancel_response = client.cancelPaymentByImpUid(cancel_data);
			//System.out.println(cancel_response.getResponse().getCancelAmount());
			//System.out.println(cancel_response.getResponse().getCancelHistory());
		} catch (IamportResponseException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 구매취소시 테이블에서 내역 삭제
	@Transactional
	public void orderCancel(MemberDTO memberDto, OrderDTO orderDto) {
		//오더 테이블에서 주문 삭제
		orderDao.deleteByCode(orderDto.getOrder_code());
		//멤버 테이블에서 총주문금액 업데이트
		memberDao.updateTotal(memberDto.getMem_id(), -orderDto.getPay());
		//상품재고 업데이트
		int stock = stockDao.getStock(orderDto.getProduct_idx(), orderDto.getSize());
		stockDao.updateStock(orderDto.getProduct_idx(), orderDto.getSize(), stock + orderDto.getCount());
		//상품 판매수 업데이트
		productDao.updateSaleCount(orderDto.getProduct_idx(), -orderDto.getCount());
	}
	
	//주문상태 업데이트
	public void updateState(String order_code) {
		String order_state = orderDao.selectByCode(order_code).getOrder_state();
		if (order_state.equals("배송완료")) {
			order_state = "구매확정";
			orderDao.updateState(order_code, order_state);
		}

	}
	
	//올바른 주문요청인지 확인
	public int validate(String imp_uid, String merchant_uid, MemberDTO memberDto) {
		IamportClient client = new IamportClient(rest_key, secret_key);
		int amount = 0;
		List<OrderDTO> orderList = orderDao.selectByMerchantUid(merchant_uid);
		// 인증 체크
		if (!orderList.get(0).getMem_id().equals(memberDto.getMem_id()))
			return 0;
		for (OrderDTO orderDto : orderList)
			amount = amount + orderDto.getPay();
		getToken();
		try {
			IamportResponse<Payment> payment_response = client.paymentByImpUid(imp_uid);
			assertNotNull(payment_response.getResponse());
			//System.out.println(payment_response.getResponse().getImpUid());
			//System.out.println(payment_response.getResponse().getAmount());
			// 총 결제금액과 테이블에 저장된 금액이 다르다면 전액 취소, 테이블 롤백
			if (payment_response.getResponse().getAmount().intValue() != amount) {
				for (OrderDTO orderDto : orderList)
					orderCancel(memberDto, orderDto);
				CancelData cancel_data = new CancelData(imp_uid, true);
				IamportResponse<Payment> cancel_response = client.cancelPaymentByImpUid(cancel_data);
				//System.out.println(cancel_response.getMessage());
				return 0;
			}
		} catch (IamportResponseException e) {
			System.out.println("2");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	public void getToken() {
		IamportClient client = new IamportClient(rest_key, secret_key);
		try {
			IamportResponse<AccessToken> auth_response = client.getAuth();

			assertNotNull(auth_response.getResponse());
			assertNotNull(auth_response.getResponse().getToken());
		} catch (IamportResponseException e) {
			System.out.println("1");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// 서버 연결 실패
			e.printStackTrace();
		}
	}
}
