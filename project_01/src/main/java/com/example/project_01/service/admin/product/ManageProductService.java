package com.example.project_01.service.admin.product;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.search.dto.SearchDTO;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.service.product.ProductService;
@Service
public class ManageProductService extends ProductService{
	@Value("${file.upload.directory}")
	String filePath;
	@Autowired
	ProductDAO productDao;
	@Autowired
	StockDAO stockDao; 
	public String makeFileName(String filename) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = formatter.format(new java.util.Date());
		filename = today + UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		return filename;
	}
	
	@Transactional
	public void register(ProductEntity productEntity, MultipartFile files, 
			int []mainDisplay, int size[], int count[]) {
		fileUpload(productEntity, files);
		//prorduct테이블에 추가
		
		//display 테이블에 추가
		if(mainDisplay !=null) {
			for(int i=0; i<mainDisplay.length ; i++) {
				productDao.addProductMainDisplay(productEntity.getProduct_idx(), mainDisplay[i]);
			}
		}
		
		//stock 테이블에 추가
		if(size!=null) {
			for(int i=0; i < size.length; i++) {
				stockDao.insertStock(productEntity.getProduct_idx(), size[i], count[i]);
			}
		}
	}
	
	public void fileUpload(ProductEntity productEntity, MultipartFile files) {
		String filename = files.getOriginalFilename();
		String file_ext = filename.substring(filename.lastIndexOf(".") + 1);
		file_ext = file_ext.toLowerCase();
		String[] allow_file = { "jpg", "png", "bmp", "gif" };
		try {
			int cnt = 0;
			for (int i = 0; i < allow_file.length; i++) {
				if (file_ext.equals(allow_file[i])) {
					cnt++;
				}
			}
			if (cnt == 0) {
				// 에러
			} else {
				filename = makeFileName(filename);
				files.transferTo(new File("C://shoesfactory/img/" + filename));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		productEntity.setProduct_image(filePath + filename);
	}
	
	@Transactional
	public void modifyProduct(ProductEntity productEntity, MultipartFile files, int product_idx) {
		if(files != null) {
			fileUpload(productEntity, files);
		} else {
			productEntity.setProduct_image(null);
		}
		productDao.updateProduct(productEntity, product_idx);
		if(productEntity.getProduct_isdisplay()==0)
			productDao.delProductMainDisplayByProduct(product_idx);
	}
	

}
