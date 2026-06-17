package com.example.privateproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.privateproject.dto.BoardDTO;
import com.example.privateproject.dto.MemberDTO;
import com.example.privateproject.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
//	-----------------
//	[게시글 목록]
	@GetMapping("/list")
	public String list(Model model,
							@RequestParam(value="page", defaultValue = "1") int page,
							@RequestParam(value="keyword", defaultValue = "") String keyword){
		
		int size = 10;
		int offset = (page - 1) * size;
		
		List<BoardDTO> boardList = boardService.getBoardList(offset, size, keyword);
		int totalCount = boardService.getBoardCount(keyword);
		
//		페이지 수 계산
		int totalPages = (int)Math.ceil((double)totalCount / size);
		if(totalPages == 0) {
			totalPages = 1;
		}
		
//		Model.addAttribute("키", 값)
		model.addAttribute("boardList", boardList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("keyword", keyword);
		
		return "board/list";
	}
	
//	----------------------
//	[상세보기] GET /board/detail/1
	@GetMapping("/detail/{boardId}")
	public String detail(@PathVariable("boardId") int boardId,
			Model model, HttpSession session) {
		
//		조회수 증가
		boardService.incrementHit(boardId);
		BoardDTO board = boardService.getBoardId(boardId);
		model.addAttribute("board", board);
		System.out.println(board.getMemberId());
		
		
//		수정, 삭제
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		if(loginMember != null) {
			model.addAttribute("loginMemberId", loginMember.getMemberId());
			model.addAttribute("loginMemberLoginId", loginMember.getMemberLoginId());
		}
		
		return "board/detail";		
	}
	
//	------------------
//	[게시글 작성 폼 이동] GET /board/write (로그인 필요)
	@GetMapping("/write")
	public String writeForm(HttpSession session) {
		
//		로그인 여부 확인
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/member/login";
		}
		return "board/write";
	}
	
//	---------------------
//	[게시글 작성 처리] POST /board/write
	@PostMapping("/write")
	public String write(BoardDTO boardDTO, HttpSession session) {
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		boardDTO.setMemberId(loginMember.getMemberId());
		boardService.insertBoard(boardDTO);
//		System.out.println(loginMember.getMemberId());
		
		return "redirect:/board/list";
	}
	
//	[수정 처리] POST /board/update/{boardId}
//	
}
