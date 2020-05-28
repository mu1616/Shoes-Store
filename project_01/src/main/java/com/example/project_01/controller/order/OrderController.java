package com.example.project_01.controller.order;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.cart.dto.CartDTO;
import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
import com.example.project_01.model.order.dto.PaymentInfo;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.service.order.OrderService;

@Controller
public class OrderController {
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	OrderService orderService;
	@Autowired
	MemberDAO memberDao;
	@Autowired
	OrderDAO orderDao;
	
	//주문 폼 페이지
	@RequestMapping("/order/orderForm")
	public String orderForm(int[] size, int[] count, int[] product, Model model) {
		List<CartDTO> soldOutList = orderService.checkSoldOut(size, product, count);
		
		// 품절인 상품이 존재할 때
		if (soldOutList.size() != 0) {
			model.addAttribute("soldOutList", soldOutList);
			return "order/soldOut";
		}
		
		// 품절인 상품이 없을 때
		List<CartDTO> orderList = orderService.getOrderList(product, size, count);
		model.addAttribute("orderList", orderList);
		
		return "order/orderForm";
	}

	//기존 주소지 체크시 처리
	@ResponseBody
	@RequestMapping("/order/orderForm/setDest")
	public MemberDTO setDestination(Principal principal) {
		MemberDTO memberDto = memberDao.findById(principal.getName());
		memberDto.setMem_pw("");
		memberDto.setMem_birth(null);
		return memberDto;
	}

	//주문조회 페이지
	@RequestMapping("/order/list")
	public String orderList(Model model, Principal principal, String year) {
		Calendar calendar = Calendar.getInstance();
		
		//처음 요청 시 현재년도로 필터링
		if (year == null)
			year = Integer.toString(calendar.get(Calendar.YEAR));
		
		String startDate = year + "-01-01";
		String endDate = (Integer.parseInt(year) + 1) + "-01-01";
		List<OrderDTO> orderList = orderDao.selectById(principal.getName(), startDate, endDate);
		
		model.addAttribute("year", year);
		model.addAttribute("orderList", orderList);
		
		return "order/orderList";
	}

	//주문취소 요청시 처리
	@ResponseBody
	@RequestMapping("/order/cancel")
	public String orderCancel(Principal principal, String order_code) {
		String msg = null;
		String mem_id = principal.getName();
		
		MemberDTO memberDto = memberDao.findById(mem_id);
		OrderDTO orderDto = orderDao.selectByCode(order_code);
		
		//취소요청한 회원과 상품을 주문한 회원의 정보가 일치하는지 확인
		if (orderDto.getMem_id().equals(mem_id)) { 
			orderService.cancelOne(memberDto, orderDto);
			msg = "구매를 취소하였습니다.";
		} else {
			msg = "권한없음";
		}
		
		return msg;
	}
	
	//주문상태 변경시 처리
	@ResponseBody
	@RequestMapping("/order/updateState")
	public void updateState(String order_code) {
		orderService.updateState(order_code);		
	}
	
	//주문 상세정보 페이지
	@ResponseBody
	@RequestMapping("/order/detail")
	public OrderDTO orderDetail(String order_code) {
		OrderDTO orderDto = orderDao.selectByCode(order_code);
		orderDto.setDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderDto.getOrder_date())));
		return orderDto;
	}
	
	//결제요청 유효성 검사
	@ResponseBody
	@RequestMapping("/order/validate")
	public int paymentComplete(String imp_uid, String merchant_uid, Principal principal) {
		MemberDTO memberDto = new MemberDTO();
		memberDto.setMem_id(principal.getName());
		int complete = orderService.validate(imp_uid, merchant_uid, memberDto);
		return complete;
	}
	
	//결제정보 생성 후 리턴
	@ResponseBody
	@RequestMapping("/order/makeInfo")
	public PaymentInfo makeInfo(int[] size, int[] count, int[] product,String mem_name, String mem_addr1, 
			String mem_addr2, String mem_postcode, String mem_phone, Principal principal) {
		PaymentInfo payment = new PaymentInfo();
		MemberDTO memberDto = new MemberDTO();
		
		//주문을 위한 고객정보(이름,연락처 등)
		memberDto.setMem_addr1(mem_addr1);
		memberDto.setMem_addr2(mem_addr2);
		memberDto.setMem_name(mem_name);
		memberDto.setMem_postcode(mem_postcode);
		memberDto.setMem_phone(mem_phone);
		memberDto.setMem_id(principal.getName());
		memberDto.setMem_role(memberDao.findById(memberDto.getMem_id()).getMem_role());
		List<CartDTO> soldOutList = orderService.checkSoldOut(size, product, count);
		
		// 품절인 상품이 존재할 때
		if (soldOutList.size() != 0) {
			String message = "품절입니다. 구매수량이 현재 남아있는 재고보다 많은지 확인해주세요.";
			for(int i=0; i<soldOutList.size(); i++){
				message += "\n";
				message += soldOutList.get(i).getProduct_name()+" ";
				message += soldOutList.get(i).getCart_size();
				message += " (남은수량: "+soldOutList.get(i).getCart_count()+"개)";
			}
			payment.setMessage(message);
			return payment;
		}	
		
		//품절인 상품이 없을 때
		payment = orderService.insertOrder(memberDto, size, count, product);
		return payment;
	}
	
	//결제 취소요청시 처리
	@ResponseBody
	@RequestMapping("/order/payCancel")
	public void payCancel(String merchant_uid, Principal principal) {
		List<OrderDTO> orderList = orderDao.selectByMerchantUid(merchant_uid);
		
		// 재인증
		if(!orderList.get(0).getMem_id().equals(principal.getName())) {
			return;
		}
		
		MemberDTO memberDto = new MemberDTO();
		memberDto.setMem_id(principal.getName());
		
		for(int i=0; i< orderList.size(); i++) {
			orderService.orderCancel(memberDto, orderList.get(i));
		}
	}
	
	//주문완료 시 처리
	@RequestMapping("/order/complete")
	public String orderComplete() {
		return "/order/orderComplete";
	}
	
}
