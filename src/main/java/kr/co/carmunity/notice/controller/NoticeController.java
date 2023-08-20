package kr.co.carmunity.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.freeboard.dmstcar.domain.dmstFreeBoard;
import kr.co.carmunity.notice.domain.Notice;
import kr.co.carmunity.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	NoticeService service;
	
	
	@RequestMapping(value="/notice/list.do",method=RequestMethod.GET)
	public String showKorFreeBoard(@RequestParam(value= "page", required = false, defaultValue="1") Integer curruntPage,
			Model model) {
		try {
//			int curruntPage = page != 0 ? page : 1;
			
			//쿼리문 SELECT COUNT(*) FROM NOTICE_TBL
			int totalCount = service.getListCount();
			PageInfo pInfo = this.getPageInfo(curruntPage, totalCount);
			List<Notice> nList = service.selectNoticeList(pInfo);
			// List 데이터 요소 체크
			// 1. isEmpty
			// 2. Size()

			if (nList.size() > 0) {
				model.addAttribute("pInfo",pInfo);
				model.addAttribute("nList", nList);
				return "userService/mainNotice";
			} else {
				model.addAttribute("error", "공지사항 조회에 실패하였습니다.");
				model.addAttribute("msg", "공지사항 조회 실패");
				model.addAttribute("url", "/main/main_member.jsp");
				return "common/errorPage";
			}

		} catch (Exception e) {
			model.addAttribute("error", "관리자 문의");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/main/main_member.jsp");
			return "common/errorPage";
		}
	}
	
	
	public PageInfo getPageInfo(int curruntPage, int totalCount) {

		PageInfo pi = null;

		// 10개씩 보여준다
		int recordCountPerPage = 10;
		int naviCountPerPage = 10;
		// 위에까지는 고정되는 값임

		// 100개를 10개씩 보여주면 몇페이지가 나오나요
		int naviTotalCount;

		// start,end 구할때 curruntpage 필요함
		// 페이지 시작값
		int startNavi;

		// 페이지 끝값
		int endNavi;

		// totalcount는 매개변수로 넘김
		// 102/10이면 10.2 나와서 올림 1페이지(나머지가 0보다 크면 +1)
		// 쉬운 방법은 + 0.9
		// double 로 소수점 계산 후 떨구기 위해 int 로 형변환
		// Math.ceil 도 가능(조금더 안정적이지만 형변환 공부 위해 int)
		naviTotalCount = (int) ((double) totalCount / recordCountPerPage + 0.9);

		// startNavi는 현재 페이지에서 나누고 0.9 더해줌
		// curruntPage 값이 1~5 일때 startNavi 가 1로 고정 되도록 구해주는 식임
		startNavi = (((int) ((double) curruntPage / naviCountPerPage + 0.9)) - 1) * naviCountPerPage + 1;

		// 엔드페이지구하기
		endNavi = startNavi + naviCountPerPage - 1;
		// endNavi 는 startNavi 에 naviCountPerPage 를 무조건 더하기때문에
		// 실제 최대값보다 커질 수 있음
		if (endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}

		// 리턴해주기 위해서 클래스를 하나 만들어서 넣어줌
//	 return recordCountPerPage,naviCountPerPage,naviTotalCount,startNavi,endNavi
		pi = new PageInfo(curruntPage, recordCountPerPage, naviCountPerPage, startNavi, endNavi, totalCount,
				naviTotalCount);
		return pi;
	}
	
	
	@RequestMapping(value="/notice/insert.do",method=RequestMethod.GET)
	public String ShowNoticeWrite() {
		
	
		return "notice/insert";
		
	}
	@RequestMapping(value="/notice/insert.do",method=RequestMethod.POST)
	public String NoticeWrite(
			@RequestParam("noticeSubject")String noticeSubject,@RequestParam("noticeContent")String noticeContent
			,Model model) {
		
		try {
			String write = "admin";
			Notice notice = new Notice(noticeSubject, noticeContent, write);
			int result = service.NoticeWrite(notice);
			
			if(result > 0) {
				
				//성공
				return "redirect:/notice/list.do";
			}
			
			else {
				model.addAttribute("error", "게시글 작성에 실패하였습니다.");
				model.addAttribute("msg", "게시글 작성 실패");
				model.addAttribute("url", "/main/main_member.jsp");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			model.addAttribute("error", "관리자 문의");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/main/main_member.jsp");
			return "common/errorPage";
		}
			
	}
	
	@RequestMapping(value="/notice/detail.do",method=RequestMethod.GET)
	public String NoticeDetail(@RequestParam("noticeNo")int noticeNo,Model model,HttpServletRequest request,HttpSession session) {
		
		try {
			Notice notice = service.selectByNo(noticeNo);
			if(notice != null) {
				//성공
				session = request.getSession();
				session.setAttribute("notice", notice);
				return "userService/noticeWrite";
			}else {
				//실패
				model.addAttribute("error", "페이지 조회에 실패하였습니다.");
				model.addAttribute("msg", "페이지  조회 실패 ㅋ");
				model.addAttribute("url", "/main/main_member.jsp");
				return "common/errorPage";		}
			
			
		} catch (Exception e) {
			model.addAttribute("error", "관리자 문의");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/main/main_member.jsp");
			return "common/errorPage";		}
		
	}
	
	
	@RequestMapping(value="/notice/delete.do",method=RequestMethod.GET)
	public String NoticeDelete(@RequestParam("noticeNo")int noticeNo,Model model) {
		
		try {
		int result = service.deleteByNo(noticeNo);
			if(result > 0 ) {
				//성공
				
				return "redirect:/notice/list.do";
			}else {
				//실패
				model.addAttribute("error", "공지사항 삭제 실패하였습니다.");
				model.addAttribute("msg", "공지사항 삭제 실패 ㅋ");
				model.addAttribute("url", "/main/main_member.jsp");
				return "common/errorPage";		}
			
			
		} catch (Exception e) {
			model.addAttribute("error", "관리자 문의");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/main/main_member.jsp");
			return "common/errorPage";		}
		
	}
	
	
	@RequestMapping(value="/notice/modify.do",method=RequestMethod.GET)
	public String showNoticeModify() {
		
		return "notice/modify";
	}
	
	@RequestMapping(value="/notice/modify.do",method=RequestMethod.POST)
	public String NoticeModify(@RequestParam("noticeNo")int noticeNo,
			@RequestParam("noticeSubject")String noticeSubject,
			@RequestParam("noticeContent")String noticeContent,Model model) {
		
		try {
			
			Notice notice = new Notice(noticeNo, noticeSubject, noticeContent);
			int result = service.updateByNo(notice);
			if(result > 0 ) {
				//성공
				
				return "redirect:/notice/list.do";
			}else {
				//실패
				model.addAttribute("error", "공지사항 수정에 실패하였습니다.");
				model.addAttribute("msg","공지사항 수정 실패");
				model.addAttribute("url", "/main/main_member.jsp");
				return "common/errorPage";		}
			
			
		} catch (Exception e) {
			model.addAttribute("error", "관리자 문의");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/main/main_member.jsp");
			return "common/errorPage";		}
		
	}
	
}
