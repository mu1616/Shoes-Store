package com.example.project_01.controller.order;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.exception.UnauthorizedException;
import com.example.project_01.model.cart.dto.CartDTO;
import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;
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

	@ResponseBody
	@RequestMapping("/order/orderForm/setDest")
	public MemberDTO setDestination(Principal principal) {
		MemberDTO memberDto = memberDao.findById(principal.getName());
		memberDto.setMem_pw("");
		memberDto.setMem_birth(null);
		return memberDto;
	}

	@RequestMapping("/order/orderProcess")
	public String orderProcess(int[] size, int[] count, int[] product, MemberDTO memberDto, Model model,
			Principal principal) {
		List<CartDTO> soldOutList = orderService.checkSoldOut(size, product, count);
		// 품절인 상품이 존재할 때
		if (soldOutList.size() != 0) {
			model.addAttribute("soldOutList", soldOutList);
			return "order/soldOut";
		}
		memberDto.setMem_id(principal.getName());
		// 품절인 상품이 없을 때
		orderService.insertOrder(size, count, product, memberDto);
		return "order/orderComplete";
	}

	@RequestMapping("/order/list")
	public String orderList(Model model, Principal principal, String year) {
		Calendar calendar = Calendar.getInstance();
		if (year == null)
			year = Integer.toString(calendar.get(Calendar.YEAR));
		model.addAttribute("year", year);
		String startDate = year + "-01-01";
		String endDate = (Integer.parseInt(year) + 1) + "-01-01";
		List<OrderDTO> orderList = orderDao.selectById(principal.getName(), startDate, endDate);
		model.addAttribute("orderList", orderList);

		return "order/orderList";
	}

	@ResponseBody
	@RequestMapping("/order/cancel")
	public String orderCancel(Principal principal, String order_code) {
		String msg = null;
		if (orderDao.selectByCode(order_code).getMem_id().equals(principal.getName())) {
			orderService.deleteOne(order_code, principal.getName());
			msg = "구매를 취소하였습니다.";
		} else {
			msg = "권한없음";
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping("/order/updateState")
	public void updateState(String order_code) {
		orderService.updateState(order_code);		
	}
}
