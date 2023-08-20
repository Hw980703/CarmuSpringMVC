package kr.co.carmunity.notice.service.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.freeboard.dmstcar.domain.dmstFreeBoard;
import kr.co.carmunity.notice.domain.Notice;
import kr.co.carmunity.notice.service.NoticeService;
import kr.co.carmunity.notice.sotre.NoticeStore;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeStore nStore;
	
	@Autowired
	SqlSession session;

	@Override
	public int getListCount() {
int result = nStore.getListCount(session);
		
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(PageInfo pInfo) {
List<Notice> nList = nStore.selectNoticeList(session,pInfo);
		
		return nList;
	}

	@Override
	public int NoticeWrite (Notice notice) {
		
		int result = nStore.NoticeWrite(session,notice);
		
		return result;
	}

	@Override
	public Notice selectByNo(int noticeNo) {
		
		Notice notice = nStore.selectByNo(session,noticeNo);
		
		return notice;
	}

	@Override
	public int deleteByNo(int noticeNo) {
		
		int result = nStore.deleteByNo(session,noticeNo);
		
		return result;
	}

	@Override
	public int updateByNo(Notice notice) {
		
		int result = nStore.updateByNo(session,notice);
		
		return result;
	}

}
