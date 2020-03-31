package com.example.project_01;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.search.dto.SearchDTO;
import com.example.project_01.service.admin.product.ManageProductService;

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
	
	public void productRegister() {
		/*
		 * (product_name,product_price,product_brand,product_stock,product_category,
			product_contents,product_image,product_isdisplay,)
		*/
		ProductEntity dto = new ProductEntity();
		dto.setProduct_name("상품명");
		dto.setProduct_price(10000);
		dto.setProduct_brand(1);
		dto.setProduct_category(1);
		dto.setProduct_contents("상세설명");
		dto.setProduct_image("이미지경로");
		dto.setProduct_isDisplay(1);
		productDao.productRegister(dto);
	
	}

	public void displayRegister() {
		productDao.addProductMainDisplay(1, 1);

	}
	
	public void countProduct() {
		PageDTO pageDto = productService.calPage(21, new SearchDTO());
		System.out.println(pageDto);
		
	}
	
	public void selectProduct() {
		List<ProductDTO> list = productDao.selectProduct(0, 9, new SearchDTO());
		for(ProductDTO dto : list) {
			System.out.println(dto);
		}
	}
	
}
