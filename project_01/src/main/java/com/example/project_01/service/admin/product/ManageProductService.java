package com.example.project_01.service.admin.product;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.service.admin.file.ManageFileService;
import com.example.project_01.service.product.ProductService;

@Service
public class ManageProductService extends ProductService {
	@Autowired
	ProductDAO productDao;
	@Autowired
	StockDAO stockDao;
	@Autowired
	ManageFileService fileService;

	//상품 등록
	@Transactional
	public void register(ProductEntity productEntity, MultipartFile files, int[] mainDisplay, int size[], int count[]) {
		String fullName = fileService.fileUpload(files);
		productEntity.setProduct_image(fullName);
		// prorduct테이블에 추가
		productDao.productRegister(productEntity);
		// display 테이블에 추가
		if (mainDisplay != null) {
			for (int i = 0; i < mainDisplay.length; i++) {
				productDao.addProductMainDisplay(productEntity.getProduct_idx(), mainDisplay[i]);
			}
		}
		// stock 테이블에 추가
		if (size != null) {
			for (int i = 0; i < size.length; i++) {
				stockDao.insertStock(productEntity.getProduct_idx(), size[i], count[i]);
			}
		}
	}
	
	//상품정보 수정
	@Transactional
	public void modifyProduct(ProductEntity productEntity, MultipartFile files, int product_idx) {
		if (files != null) {
			String fullName = fileService.fileUpload(files);
			productEntity.setProduct_image(fullName);
		} else {
			productEntity.setProduct_image(null);
		}
		productDao.updateProduct(productEntity, product_idx);
		if (productEntity.getProduct_isdisplay() == 0)
			productDao.delProductMainDisplayByProduct(product_idx);
	}

}
