package com.example.privateproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

//	Comment 테이블 1개의 행의 데이터를 담는 클래스
	private int commentId; // 댓글번호(pk)
	private String commentContent; // 댓글 내용
	private String commentDate; // 작성일시
	private int boardId; // 어떤 게시글의 댓글인지(FK)
	private int memberId; // 댓글 작성자 번호(FK)
	
//	join으로 가져오는 필드 - 댓글 작성자 아이디 표시용
	private String memberLoginId;
}
