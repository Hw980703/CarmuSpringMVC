package kr.co.carmunity.freeboard.dmstcar.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.freeboard.dmstcar.domain.dmstFreeBoard;

public interface dmstStore {

	int korFreeWrite(SqlSession session, dmstFreeBoard dsmt);

	int getListCount(SqlSession session);

	List<dmstFreeBoard> selectNoticeList(SqlSession session, PageInfo pInfo);

	dmstFreeBoard selectByNo(SqlSession session, int korFreeBoardNo);

	int deleteByNo(SqlSession session, int korFreeBoardNo);

	int updateByNo(SqlSession session, dmstFreeBoard dmst);

	int selectListCount(SqlSession session, Map<String, String> paramMap);

	List<dmstFreeBoard> searchNoticeByKeyword(SqlSession session, PageInfo pInfo, Map<String, String> paramMap);

}
