package com.example.privateproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.privateproject.dto.BoardDTO;
import com.example.privateproject.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
//	[게시글 목록]
	public List<BoardDTO> getBoardList(){
		return boardMapper.selectAll();
	}
	
//	[전체 게시글 수]
	public int getBoardCount(String keyword) {
		return boardMapper.selectCount(keyword);
	}
	
//	[게시글 상세 조회]
	
//	[조회수 증가]
	
//	[게시글 등록]
	public void insertBoard(BoardDTO boardDTO) {
		boardMapper.insertBoard(boardDTO);
	}
	
}
