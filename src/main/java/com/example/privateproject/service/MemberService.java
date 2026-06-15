package com.example.privateproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.privateproject.dto.MemberDTO;
import com.example.privateproject.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;

//	"insertMember"보다 "join"이 "회원가입"이라는 의미를 더 잘 표현
	public void join(MemberDTO memberDTO) {
		memberMapper.insertMember(memberDTO);
	}

	public boolean isLoginIdDuplicate(String loginId) {
		return memberMapper.countByLoginId(loginId) > 0;
	}

	public MemberDTO login(String loginId, String pwd) {
		return memberMapper.selectByLoginAndPwd(loginId, pwd);
	}
}