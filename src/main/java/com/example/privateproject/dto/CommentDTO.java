package com.example.privateproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
	private int commentId;
	private String commentContent;
	private String commentDate;
	private int boardId;
	private int memberId;
	
	// member테이블과 comment테이블의 memberId끼리 join해서 memberLoginId를 가져옴
	private String memberLoginId;
}
