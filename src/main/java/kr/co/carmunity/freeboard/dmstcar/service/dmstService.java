package kr.co.carmunity.freeboard.dmstcar.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.freeboard.dmstcar.domain.dmstFreeBoard;
import kr.co.carmunity.freeboard.dmstcar.store.dmstStore;
import kr.co.carmunity.notice.domain.Notice;

public interface dmstService {
	

	int korFreeWrite(dmstFreeBoard dsmt);

	int getListCount();

	List<dmstFreeBoard> selectNoticeList(PageInfo pInfo);

	dmstFreeBoard selectByNo(int korFreeBoardNo);

	int deleteByNo(int korFreeBoardNo);

	int updateByNo(dmstFreeBoard dmst);

	int getListCount(Map<String, String> paramMap);

	List<dmstFreeBoard> searchNoticeByKeyword(PageInfo pInfo, Map<String, String> paramMap);

	
	
}
