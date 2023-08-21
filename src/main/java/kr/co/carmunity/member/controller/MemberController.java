package kr.co.carmunity.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.carmunity.member.domain.Member;
import kr.co.carmunity.member.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpSession session;

	@RequestMapping(value = "/member/register.do", method = RequestMethod.GET)
	public String ShowRegisterForm() {
		return "user/signUp";

	}

	@RequestMapping(value = "/member/register.do", method = RequestMethod.POST)
	public String registerMember(@RequestParam("user-id") String memberId, @RequestParam("user-p2") String memberPw,
			@RequestParam("user-name") String memberName, @RequestParam("user-nic") String memberNicName,
			@RequestParam("user-email") String memberEmail, @RequestParam("user-tel1") String memberTel, Model model) {

		try {
			if (memberId != "") {

				Member member = new Member(memberId, memberPw, memberName, memberNicName, memberEmail, memberTel);
				int result = service.insertMember(member);

				if (result > 0) {
					model.addAttribute("member", member);
					return "main/main_member";
				} else {
					model.addAttribute("error", "회원가입 실패하였습니다.");
					model.addAttribute("msg", "회원가입 실패");
					model.addAttribute("url", "/member/register.do");
					return "common/errorPage";

				}

			} else {
				model.addAttribute("error", "회원가입 실패하였습니다.");
				model.addAttribute("msg", "회원가입 실패");
				model.addAttribute("url", "/member/register.do");
				return "common/errorPage";
			}

		} catch (Exception e) {
			model.addAttribute("error", "회원가입 실패하였습니다.");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/member/register.do");
			return "common/errorPage";
		}

	}

	@RequestMapping(value = "/member/memberMain.do", method = RequestMethod.GET)
	public String showMemberMain() {

//		session = request.getSession();
//		Member member = (Member)session.getAttribute("member");
//		session.setAttribute("member", member);
		return "main/main_member";
	}

	@RequestMapping(value = "/member/login.do", method = RequestMethod.GET)
	public String loginMemberShow() {
		return "user/login";
	}

	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public String loginMember(@RequestParam("userId") String memberId, @RequestParam("userPw") String memberPw,
			Model model) {

		try {

			Member member2 = new Member(memberId, memberPw);
			Member member = service.selectCheckLogin(member2);

			if (member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				return "main/main_member";
			} else {

				model.addAttribute("error", "로그인 실패하였습니다.");
				model.addAttribute("msg", "로그인 실패");
				model.addAttribute("url", "/member/login.do");
				return "common/errorPage";
			}

		} catch (Exception e) {
			model.addAttribute("error", "로그인 실패하였습니다.");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/member/login.do");
			return "common/errorPage";
		}

	}

	@RequestMapping(value = "/member/info.do", method = RequestMethod.GET)
	public String memberInfo() {

		HttpSession session = request.getSession();
		session.getAttribute("member");
		return "user/info";

	}

	@RequestMapping(value = "/member/infoChange.do", method = RequestMethod.GET)
	public String InfoChangeView() {
		return "user/infoChange";
	}

	@RequestMapping(value = "/member/nicName.do", method = RequestMethod.POST)
	public String nicNameChange(@RequestParam("userNik") String nicName, Model model) {

		try {

			HttpSession session = request.getSession();
			Member member = (Member) session.getAttribute("member");
			String[] nic = nicName.split("");
			if (nic.length > 6) {
				nicName = member.getMemberNicName();
			}
			member.setMemberNicName(nicName);
			int result = service.nicupdateMember(member);

			if (result > 0) {
				return "user/info";

			} else {
				model.addAttribute("error", "회원정보 수정 실패.");
				model.addAttribute("msg", "회원정보 수정 실패");
				model.addAttribute("url", "/member/info.do");
				return "common/errorPage";

			}

		} catch (Exception e) {
			model.addAttribute("error", "회원정보 수정 실패.");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/member/info.do");
			return "common/errorPage";
		}

	}

	@RequestMapping(value = "/member/phone.do", method = RequestMethod.POST)
	public String phoneChange1(@RequestParam("userPhone") String newPhoneNumber, Model model) {

		try {
			if (newPhoneNumber != "") {

				HttpSession session = request.getSession();
				Member member = (Member) session.getAttribute("member");
				String phoneNumber = member.getMemberPhone();
				String number = "2345";
				model.addAttribute("number", number);
				model.addAttribute("newPhone", newPhoneNumber);
				model.addAttribute("phone", phoneNumber);

				return "user/infoChange";

			} else {
				model.addAttribute("error", "인증번호 발송 실패");
				model.addAttribute("msg", "인증번호 발송 실패");
				model.addAttribute("url", "/member/infoChange.do");
				return "common/errorPage";
			}

		} catch (Exception e) {
			model.addAttribute("error", "인증번호 발송 실패.");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/member/infoChange.do");
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/member/phoneChange.do", method = RequestMethod.POST)
	public String phoneChange2(@RequestParam("userPhoneChec") String checkNum,
			@RequestParam("newPhone") String newPhone, @RequestParam("number") String number) {

		HttpSession session = request.getSession();
		if (checkNum.equals(number)) {
//			수정 성공
			Member member = (Member) session.getAttribute("member");
			member.setMemberPhone(newPhone);
			int result = service.phoneUpdate(member);
			return "user/info";
		} else {
//			수정 실패

			return "user/infoChange";
		}

	}

	@RequestMapping(value = "/member/pwChange", method = RequestMethod.POST)
	public String pwChange(@RequestParam("memberId") String memberId, @RequestParam("userPw") String memberPw,
			@RequestParam("userNewPw") String memberNewPw, @RequestParam("userNewPwRe") String memberNewPwRe,
			Model model) {

		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");

		if (member.getMemberPw().equals(memberPw)) {
			if (memberNewPw.equals(memberNewPwRe)) {
				// 성공
				member.setMemberPw(memberNewPw);
				int result = service.pwChange(member);
				model.addAttribute("msg", "[변경완료]");

			} else {
				model.addAttribute("error", "회원탈퇴 실패");
				model.addAttribute("msg", "[신규 비밀번호 및 재입력 오류]");
				model.addAttribute("url", "/member/infoChange.do");
				return "common/errorPage";
				
			}

		} else {
			
			model.addAttribute("error", "회원탈퇴 실패");
			model.addAttribute("msg", "[현재 비밀번호 불일치]");
			model.addAttribute("url", "/member/infoChange.do");
			return "common/errorPage";
		}

		return "user/infoChange";
	}

	@RequestMapping(value = "/member/emailChange.do", method = RequestMethod.POST)
	public String emailChange() {

		return null;
	}

	@RequestMapping(value = "/member/mypage.do", method = RequestMethod.GET)
	public String memberMypage() {

		return "user/mypage";
	}

	@RequestMapping(value = "/member/delete.do", method = RequestMethod.GET)
	public String memberDelete(@RequestParam("memberId") String memberId, Model model) {
		try {

			int result = service.deleteMember(memberId);
			if (result > 0) {
				// 성공
				return "redirect:/index.jsp";
			} else {
				model.addAttribute("error", "회원탈퇴 실패");
				model.addAttribute("msg", "회원탈퇴 실패");
				model.addAttribute("url", "/member/mypage.do");
				return "common/errorPage";
			}

		} catch (Exception e) {
			model.addAttribute("error", "회원 탈퇴 실패.");
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/member/mypage.do");
			return "common/errorPage";
		}

	}
	
	
	@RequestMapping(value="/member/getUserInfo.do",method=RequestMethod.GET)
	public String showGetUserInfo() {
		return "user/idGet";
	}

}
