package com.example.privateproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
	private int memberId;
	private String memberLoginId;
	private String memberName;
	private String memberEmail;
	private String memberPwd;
	private String memberPhone;
}