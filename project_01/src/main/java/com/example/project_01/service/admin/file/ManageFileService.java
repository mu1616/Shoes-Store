package com.example.project_01.service.admin.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_01.model.product.dto.ProductEntity;
@Service
public class ManageFileService {
	@Value("${file.upload.directory}")
	String filePath;
	
	public String makeFileName(String filename) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = formatter.format(new java.util.Date());
		filename = today + UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		return filename;
	}
	
	//이미지 파일 업로드
	public String fileUpload(MultipartFile files) {
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
		
		// ex) /shoesfactory/img/filename 
		return filePath + filename;   
	}
	
}
