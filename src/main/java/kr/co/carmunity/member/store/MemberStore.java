package kr.co.carmunity.member.store;

import org.apache.ibatis.session.SqlSession;

import kr.co.carmunity.member.domain.Member;

public interface MemberStore {
	
	// 회원가입 insert
		public int insertMember(SqlSession session, Member member);

		public int deleteMember(SqlSession session, String memberId) ;

		public Member selectCheckLogin(SqlSession session, Member member);
		
		public Member selectOneById(SqlSession session, String memberId) ;

		public Member selectIdInfo(SqlSession session, String memberId) ;

		public int nicupdateMember(SqlSession session, Member member) ;

		public int phoneUpdate(SqlSession session, Member member) ;

		public int pwChange(SqlSession sqlSession, Member member);

	

}
