package com.example.store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.member.dao.MemberDAO;
import com.example.store.model.member.dto.MemberDTO;
import com.example.store.model.order.dto.PaymentInfo;
import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;
import com.example.store.model.stock.dao.StockDAO;
import com.example.store.service.order.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback(true)
public class OrderServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	OrderService orderService;
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	@Autowired 
	MemberDAO memberDao;
	
	@Test
	@Transactional
	public void test() {
		ProductDTO productDto = productDao.selectProductDTO(337);
		int product[] = {337, 337};
		int count[] = {1, 2};
		int size[] = {250, 260};
		int stock = stockDao.getStock(337, 260);
		int saleCount = productDao.selectProductDTO(337).getProduct_salecount();
		MemberDTO memberDto = memberDao.findById("mu1616");
		int total = memberDto.getMem_total();

		PaymentInfo payment = orderService.insertOrder(memberDto, size, count, product);
		
		//결제 금액 
		assertThat(payment.getAmount()).isEqualTo(productDto.getProduct_price()*3);
		
		//재고
		assertThat(stock).isEqualTo(stockDao.getStock(337,260)+count[1]);
		
		//판매 수량
		assertThat(saleCount).isEqualTo(productDao.selectProductDTO(337).getProduct_salecount()-(count[0]+count[1]));
		
		//회원의 총 결제금액
		assertThat(total).isEqualTo(memberDao.findById("mu1616").getMem_total()-(productDto.getProduct_price()*3));
	}
}
