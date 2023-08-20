package kr.co.carmunity.notice.service;

import java.util.List;

import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.notice.domain.Notice;

public interface NoticeService {

	int getListCount();

	List<Notice> selectNoticeList(PageInfo pInfo);

	int NoticeWrite(Notice notice);

	Notice selectByNo(int noticeNo);

	int deleteByNo(int noticeNo);

	int updateByNo(Notice notice);

}
