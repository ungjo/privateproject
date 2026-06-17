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
import com.example.privateproject.dto.CommentDTO;
import com.example.privateproject.dto.MemberDTO;
import com.example.privateproject.service.BoardService;
import com.example.privateproject.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	
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
		
//		상세보기를 열때마다 조회수 + 1
//		- 조회수 증가와 조회를 따로 호출하는 이유
//		- 먼저 조회수를 올린 뒤 최신 조회수가 반영된 데이터를 가져오기 위해
		boardService.incrementHit(boardId);
		BoardDTO board = boardService.getBoardId(boardId);
		model.addAttribute("board", board);
		
//		상세보기 첫 로딩 시 기존 댓글을 Thymeleaf로 화면에 표시
//		이후 등록, 삭제는 AJAX로 처리해서 새로고침 없이 반영
		List<CommentDTO> commentList = commentService.getCommentsByBoardId(boardId);
		model.addAttribute("commentList", commentList);
		
//		로그인 회원 정보 - 수정/삭제 버튼, 댓글 입력 폼 표시 여부
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
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
	
//  [수정 폼으로 이동] GET /board/update/1 (로그인 + 작성자만)
	
//	로그인 확인 -> 작성자 확인 -> 폼 화면 이동
	@GetMapping("/update/{boardId}")
	public String updateForm(@PathVariable("boardId") int boardId,
			HttpSession session, Model model) {
//		1) 로그인 확인
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		if(loginMember == null) {
//			로그인이 안된 상태라면 로그인 화면으로 이동
			return "redirect:/member/login";
		}
//		게시글 내용 가져오기
		BoardDTO board = boardService.getBoardId(boardId);
		
//		2) 작성자 확인 : 로그인 회원번호와 게시글 작성자 번호 비교
		if(board.getMemberId() != loginMember.getMemberId()) {
//			작성자와 로그인 정보가 일치하지 않는 경우
			return "redirect:/board/detail/"+boardId;
		}
		
//		3) 수정 폼으로 이동
		model.addAttribute("board", board);
		return "board/update";
	}
	
//	[수정 처리] POST /board/update/{boardId}
	@PostMapping("/update/{boardId}")
	public String update(@PathVariable("boardId") int boardId,
						BoardDTO boardDTO, HttpSession session) {
//		로그인이 되어 있지 않은 상태라면, 로그인 화면으로 이동
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
//		작성자 본인인지 확인
		BoardDTO board = boardService.getBoardId(boardId);
		if(board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail/" + boardId;
		}
		
//		3) 게시판 수정에 필요한 게시판 번호를 DTO에 담아 전달
		boardDTO.setBoardId(boardId); 
//		URL의 boardId를 DTO에 저장
		boardService.updateBoard(boardDTO);
//		수정 완료 후 해당 게시글 상세보기로 이동
		return "redirect:/board/detail/" + boardId;
	}
	
//	[게시글 삭제] POST /board/delete/1 (로그인 + 작성자만)
	
//	삭제를 POST로 하는 이유
//	: GET 방식이면 /board/delete/1 
//	URL을 작성해서 타고 들어가는 것만으로도 삭제될 수 있어서 위험하다
	@PostMapping("/delete/{boardId}")
	public String delete(@PathVariable("boardId") int boardId, 
				HttpSession session) {
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		
//		1) 로그인 확인
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
//		2) 작성자 확인
		BoardDTO board = boardService.getBoardId(boardId);
		if(board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail/"+boardId;
		}
		
//		3) 삭제 실행
		boardService.deleteBoard(boardId);
		return "redirect:/board/list";
		
	}
}
