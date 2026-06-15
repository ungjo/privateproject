package com.example.privateproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.privateproject.dto.MemberDTO;
import com.example.privateproject.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	// [회원가입 폼] GET /member/join
	@GetMapping("/join") // 주소창에 http://localhost:8810/member/join을 입력(GET요청)했을 때 joinForm()메서드 실행
	public String joinForm() { // return 할 파일의 이름이 문자이기 때문에 String타입을 사용
		return "member/join"; // templates.member의 join.html을 의미. 이 파일을 찾아서 화면에 띄움.
	}

	// [회원가입 처리] POST /member/join
	// 회원가입 폼에 정보를 입력하고 제출했을 때 POST방식으로 요청되는 join주소를 아래의 메서드가 담당. 자동으로
	// join(MemberDTO memberDTO, Model model)이 호출됨.
	@PostMapping("/join")
	public String join(MemberDTO memberDTO, Model model) {

		// 중복되는 아이디가 있다면 /member/join 페이지로 이동
		if (memberService.isLoginIdDuplicate(memberDTO.getMemberLoginId())) {
			model.addAttribute("error", "duplicate");
			return "/member/join";
		}

//		중복되는 아이디가 없다면 로그인 페이지로 이동
		memberService.join(memberDTO);
		return "redirect:/member/login";
	}

//	------------------------
//	[로그인 폼] GET /member/login
	@GetMapping("/login")
	public String loginForm() {
		return "member/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd, HttpSession session,
			Model model) {

		MemberDTO loginMember = memberService.login(loginId, pwd);

		if (loginMember == null) {
			model.addAttribute("error", "fail");
			return "member/login";
		}

		// 세션에 회원 정보를 저장
		session.setAttribute("loginMember", loginMember);
		// System.out.println(loginMember.getMemberLoginId());
		// System.out.println(loginMember.getMemberId());
		return "redirect:/board/list";
	}
	
//	------------
//	[로그아웃] GET /member/logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/board/list";
	}

}
