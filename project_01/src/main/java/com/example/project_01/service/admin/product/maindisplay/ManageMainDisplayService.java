package com.example.project_01.service.admin.product.maindisplay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_01.model.product.dao.ProductDAO;

@Service
public class ManageMainDisplayService {
	@Autowired
	ProductDAO productDao;
	
	//메인진열 상태 저장
	@Transactional
	public void saveDisplay(int display_md, int[] product_idx) {
		productDao.delProductMainDisplay(display_md);
		for (int idx : product_idx) {
			productDao.addProductMainDisplay(idx, display_md);
		}
	}
}
