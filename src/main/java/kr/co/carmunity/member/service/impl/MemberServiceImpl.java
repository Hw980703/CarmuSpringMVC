package kr.co.carmunity.member.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.carmunity.member.domain.Member;
import kr.co.carmunity.member.service.MemberService;
import kr.co.carmunity.member.store.MemberStore;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberStore mStore;
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public int insertMember(Member member) {
	
			int result = mStore.insertMember(sqlSession, member);
		return result;
	}

	@Override
	public Member selectCheckLogin(Member member) {
		
		Member mOne = mStore.selectCheckLogin(sqlSession, member);
		return mOne;
	}

	@Override
	public Member selectOneById(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteMember(String memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Member selectIdInfo(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nicupdateMember(Member member) {
		
		int result = mStore.nicupdateMember(sqlSession, member);
		
		return result;
	}

	@Override
	public int phoneUpdate(Member member) {
		int result = mStore.phoneUpdate(sqlSession, member);
		return result;
	}

	@Override
	public int pwChange(Member member) {
		int result = mStore.pwChange(sqlSession , member);
		
		return result;
	}

}
