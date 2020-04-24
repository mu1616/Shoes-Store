package com.example.project_01.model.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.notice.dto.NoticeDTO;

@Mapper
public interface NoticeDAO {
	//레코드 삽입
	public void insertOne(NoticeDTO noticeDto);
	//전체 레코드 select
	public List<String> selectNoticeType();
	//특정 notice_idx를 가지는 레코드 select
	public NoticeDTO selectOne(int notice_idx);
	//전체 레코드 개수 출력
	public int countRecord();
	//전체 레코드 select (start ~ start+length) 
	public List<NoticeDTO> selectNotice(int start, int length);
	//레코드 수정
	public void updateOne(NoticeDTO noticeDto);
	//레코드 삭제
	public void deleteOne(int notice_idx);
	//공지글 타입에 맞는 레코드 select
	public List<NoticeDTO> selectByType(String notice_type, int start, int length);
	//공지글 타입에 맞는 레코드 개수 select
	public int selectCountByType(String notice_type);
}
