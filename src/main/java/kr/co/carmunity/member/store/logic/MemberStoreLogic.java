package kr.co.carmunity.member.store.logic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.carmunity.member.domain.Member;
import kr.co.carmunity.member.store.MemberStore;

@Repository
public class MemberStoreLogic implements MemberStore {

	@Override
	public int insertMember(SqlSession session, Member member) {
		int result = session.insert("memberMappers.insertMember",member);
		return result;
	}

	@Override
	public int deleteMember(SqlSession session, String memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Member selectCheckLogin(SqlSession session, Member member) {
		Member mOne = session.selectOne("memberMappers.loginMember",member);
		
		return mOne;
	}

	@Override
	public Member selectOneById(SqlSession session, String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member selectIdInfo(SqlSession session, String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nicupdateMember(SqlSession session, Member member) {
		
		int result = session.update("memberMappers.updateNicName",member);
		
		return result;
	}

	@Override
	public int phoneUpdate(SqlSession session, Member member) {
		
		int result = session.update("memberMappers.updatePhone",member);
				
		return result;
	}

	@Override
	public int pwChange(SqlSession session, Member member) {
		
		int result = session.update("memberMappers.updatePw",member);
		return result;
	}

}
