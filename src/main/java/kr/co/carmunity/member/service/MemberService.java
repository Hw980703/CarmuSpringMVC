package kr.co.carmunity.member.service;

import kr.co.carmunity.member.domain.Member;

public interface MemberService {

	public int insertMember(Member member);

	public Member selectCheckLogin(Member member);

	public Member selectOneById(String memberId);

	public int deleteMember(String memberId);

	public Member selectIdInfo(String memberId);

	public int nicupdateMember(Member member);

	public int phoneUpdate(Member member);

	public int pwChange(Member member);
	

}
