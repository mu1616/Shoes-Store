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
import com.example.project_01.model.product.dto.ProductDTO;
@Service
public class ManageProductService {
	@Value("${file.upload.directory}")
	String filePath;
	@Autowired
	ProductDAO dao;
	public String makeFileName(String filename) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = formatter.format(new java.util.Date());
		filename = today + UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		return filename;
	}
	
	@Transactional
	public void register(ProductDTO dto, MultipartFile files, int []mainDisplay) {
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
				files.transferTo(new File(filePath + filename));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setProduct_image(filePath + filename);
		dao.productRegister(dto);
		System.out.println(dto);
		System.out.println(mainDisplay);
		
		//display 테이블에 추가
		if(mainDisplay !=null) {
			for(int i=0; i<mainDisplay.length ; i++) {
				System.out.println(dto.getProduct_idx()+" "+mainDisplay[i]);
				dao.displayRegister(dto.getProduct_idx(), mainDisplay[i]);
			}
		}
	}
}
