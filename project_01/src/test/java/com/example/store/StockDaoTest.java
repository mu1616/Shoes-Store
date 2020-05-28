package com.example.store;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.store.model.stock.dao.StockDAO;
import com.example.store.model.stock.dto.StockDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class StockDaoTest {
	@Autowired
	StockDAO stockDao;
	
	
	public void selectByProduct() {
		List<StockDTO> list= stockDao.selectByProduct(327);
		for(StockDTO dto : list) {
			System.out.println(dto);
		}
	}
	
}
