package com.example.privateproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.privateproject.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	// [회원가입] member테이블에 새 행을 추가
	void insertMember(MemberDTO memberDTO);
			
	int countByLoginId(@Param("loginId") String loginId);
}
