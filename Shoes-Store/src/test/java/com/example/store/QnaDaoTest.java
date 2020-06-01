package com.example.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.store.model.product.qna.dao.QnaDAO;
import com.example.store.model.product.qna.dto.QnaDTO;
import com.example.store.model.product.qna.dto.SearchQnaDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class QnaDaoTest {
	
	@Autowired
	QnaDAO qnaDao;
	
	@Test
	public void selectQna() {
		List<QnaDTO> qnaList = qnaDao.selectQna(0, 5, new SearchQnaDTO());
		
		assertThat(qnaList.size()).isEqualTo(5);
	}
}
