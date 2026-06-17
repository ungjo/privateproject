package com.example.privateproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.privateproject.dto.BoardDTO;

@Mapper
public interface BoardMapper {
//	[게시글 목록]
	List<BoardDTO> selectAll(@Param("offset") int offset,
							@Param("size") int size,
							@Param("keyword") String keyword);
//	[전체 게시글 수]
	int selectCount(@Param("keyword") String keyword);
	
//	[게시글 상세 조회] 게시글 번호 1개 조회
	BoardDTO selectById(@Param("boardId") int boardId);
	
//	[조회수 증가] 상
	void updateHit(@Param("boardId") int boardId);
	
//	[게시글 작성]
	void insertBoard(BoardDTO boardDTO);
}
