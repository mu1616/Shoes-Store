package com.example.store.service.admin.product.maindisplay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.product.dao.ProductDAO;

@Service
public class ManageMainDisplayService {
	@Autowired
	ProductDAO productDao;
	
	//메인진열 상태 저장
	@Transactional
	public void saveDisplay(int display_md, int[] product_idx) {
		//전체 삭제
		productDao.delProductMainDisplay(display_md);
		
		//전체 추가 (순서 재지정)
		for (int idx : product_idx) {
			productDao.addProductMainDisplay(idx, display_md);
		}
	}
}
