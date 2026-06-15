package com.example.privateproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
//	board 테이블 1행의 데이터를 담는 클래스
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private int boardHit;
	private String boardDate;
	private int memberId;
	
	// board테이블 컬럼은 아니지만 작성자의 id를 담기위한 변수
	private String memberLoginId;	//작성자 id
}
