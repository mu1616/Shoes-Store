package com.example.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;
import com.example.store.model.product.dto.ProductEntity;
import com.example.store.model.search.dto.SearchDTO;
import com.example.store.service.admin.product.ManageProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProductDaoTest {
	@Autowired
	ProductDAO productDao;
	@Value("${file.upload.directory}")
	String filePath;
	@Value("${smarteditor.upload.directory")
	String editorPath;
	@Autowired
	ManageProductService productService;
	@Rollback(true)
	
	@Transactional
	public void productRegister() {
		/*
		 * (product_name,product_price,product_brand,product_stock,product_category,
			product_contents,product_image,product_isdisplay,)
		*/
		ProductEntity dto = new ProductEntity();
		dto.setProduct_name("상품명");
		dto.setProduct_price(10000);
		dto.setProduct_brand("Nike");
		dto.setProduct_category("운동화");
		dto.setProduct_contents("상세설명");
		dto.setProduct_image("이미지경로");
		dto.setProduct_isDisplay(1);
		
		productDao.productRegister(dto);
	}
	

	@Transactional
	public void displayRegister() {
		productDao.addProductMainDisplay(338, 1);
		List<ProductDTO> list = productDao.selectProductByDisplay(1);
		assertThat(338).isEqualTo(list.get(list.size()-1).getProduct_idx());
	}
	
	public void countProduct() {
		
	}
	
	@Test
	public void selectProduct() {
		List<ProductDTO> list = productDao.selectProduct(0, 3, new SearchDTO());
		assertThat(list.size()).isEqualTo(3);
	}
	
}
