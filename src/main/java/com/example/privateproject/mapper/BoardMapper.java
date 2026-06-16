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
	
//	[게시글 작성]
	void insertBoard(BoardDTO boardDTO);
}
