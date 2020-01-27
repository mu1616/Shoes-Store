package com.example.project_01;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProductDaoTest {
	@Autowired
	ProductDAO productDao;
	
	
	public void productRegister() {
		/*
		 * (product_name,product_price,product_brand,product_stock,product_category,
			product_contents,product_image,product_isdisplay,)
		*/
		ProductDTO dto = new ProductDTO();
		dto.setProduct_name("상품명");
		dto.setProduct_price(10000);
		dto.setProduct_brand(1);
		dto.setProduct_category(1);
		dto.setProduct_contents("상세설명");
		dto.setProduct_image("이미지경로");
		dto.setProduct_isDisplay(1);
		productDao.productRegister(dto);
	
	}
	@Test
	public void displayRegister() {
		productDao.displayRegister(1, 1);
	}
}
