package kr.co.carmunity.freeboard.dmstcar.service.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.freeboard.dmstcar.domain.dmstFreeBoard;
import kr.co.carmunity.freeboard.dmstcar.service.dmstService;
import kr.co.carmunity.freeboard.dmstcar.store.dmstStore;

@Service
public class dmstServiceImpl implements dmstService {

	@Autowired
	dmstStore dStore;
	
	@Autowired
	SqlSession session;
	
	@Override
	public int korFreeWrite(dmstFreeBoard dsmt) {
		
		int result = dStore.korFreeWrite(session,dsmt);
		
		return result;
	}

	@Override
	public int getListCount() {
	int result = dStore.getListCount(session);
		
		return result;
	}

	@Override
	public List<dmstFreeBoard> selectNoticeList(PageInfo pInfo) {
List<dmstFreeBoard> dList = dStore.selectNoticeList(session,pInfo);
		
		return dList;
		
	}

	@Override
	public dmstFreeBoard selectByNo(int korFreeBoardNo) {
		
		
		dmstFreeBoard dmst = dStore.selectByNo(session,korFreeBoardNo);
		return dmst;
	}

	@Override
	public int deleteByNo(int korFreeBoardNo) {
		
		int result = dStore.deleteByNo(session,korFreeBoardNo);
		return result;
	}

	@Override
	public int updateByNo(dmstFreeBoard dmst) {
		
		int result = dStore.updateByNo(session,dmst);
		
		return result;
	}

}
