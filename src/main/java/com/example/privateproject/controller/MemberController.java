package com.example.privateproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.privateproject.dto.MemberDTO;
import com.example.privateproject.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// [회원가입 폼] GET /member/join
	@GetMapping("/join")
	public String joinForm() {
		return "member/join";
	}
	
	// [회원가입 처리] POST /member/join
	@PostMapping("/join")
	public String join(MemberDTO memberDTO, Model model) {
		// Model : DAO, DTO => 데이터처리(DB 연결, SQL 실행)
//	    Model은 데이터를 담아서 보낼 때 사용. Model 객체는 Controller에서 생성된 데이터를 View로 전달할 때 사용하는 객체다.
		
		if(memberService.)
	}
}
