package com.example.privateproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
	private int boardId;	//게시글 번호(pk)
	private String boardTitle;	//게시글 제목
	private String boardContent;	//게시글 제목
	private int boardHit;	//게시글 조회수
	private String boardDate;	//게시글 작성일시
	private int memberId;	//작성자 번호(fk)
	
	//board테이블과 member테이블의 memberId끼리 join해서 memberLoginId를 가져옴 
	private String memberLoginId;	// 작성자 아이디(member테이블과 join)
}
