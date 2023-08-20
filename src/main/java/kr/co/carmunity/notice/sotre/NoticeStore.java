package kr.co.carmunity.notice.sotre;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.notice.domain.Notice;

public interface NoticeStore {

	int getListCount(SqlSession session);

	List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo);

	int NoticeWrite(SqlSession session, Notice notice);

	Notice selectByNo(SqlSession session, int noticeNo);

	int deleteByNo(SqlSession session, int noticeNo);

	int updateByNo(SqlSession session, Notice notice);

}
