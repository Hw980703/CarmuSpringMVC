package kr.co.carmunity.notice.sotre.Logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.freeboard.dmstcar.domain.dmstFreeBoard;
import kr.co.carmunity.notice.domain.Notice;
import kr.co.carmunity.notice.sotre.NoticeStore;

@Repository
public class NoticeStoreLogic implements NoticeStore {

	@Override
	public int getListCount(SqlSession session) {
int result = session.selectOne("noticeMapper.selectNoticeCount");
		
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo) {
		//limit 은 행의 개수 제어하는거임 10개씩 가져오고싶으면 10
		int limit = pInfo.getRecordCountPerPage();
		
		// offset 은 변하는 default 값이라고 보면 됨
		// 시작하는 값을 세팅해줌 (1,2,3,4,5)
		//1페이지 볼때는 1~10을 봐야함
		//2페이지 볼때는 11~20 봐야함
		// 계속해서 값이 바껴야함
		// 그렇기때문에 curruntPage를 매개변수로 넘겨주고 -1 한 값에 limit을 곱해준다
		int offset = (pInfo.getCurruntPage() - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		//rowBounds 는 3번째 자리에 넣어야함, 가운데에는 아무것도 없을때 Null 넣어줌
		// 넘겨주는 값이 있으면 null 말고 매개변수 넣어주는거임, 여기는 매개변수가 없음
		List<Notice> nList = session.selectList("noticeMapper.mainNoticeList",null,rowBounds);
		return nList;

	}

	@Override
	public int NoticeWrite(SqlSession session, Notice notice) {
		
		int result = session.insert("noticeMapper.mainNotice",notice);
		
		return result;
	}

	@Override
	public Notice selectByNo(SqlSession session, int noticeNo) {
		
		Notice notice = session.selectOne("noticeMapper.selectOneByNo",noticeNo);
		
		return notice;
	}

	@Override
	public int deleteByNo(SqlSession session, int noticeNo) {
		
		int result = session.delete("noticeMapper.noticeDelete",noticeNo);
		
		return result;
	}

	@Override
	public int updateByNo(SqlSession session, Notice notice) {
		
		int result = session.update("noticeMapper.updateNotice",notice);
		
		return result;
	}

	@Override
	public int selectListCount(SqlSession session, Map<String, String> paramMap) {
	int result = session.selectOne("noticeMapper.selectListByKewordCount",paramMap);
		
		return result;
	}

	@Override
	public List<Notice> searchNoticeByKeyword(SqlSession session, PageInfo pInfo, Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurruntPage()-1)*limit;
		
		RowBounds rowbounds = new RowBounds(offset, limit);
		List<Notice> nList = session.selectList("noticeMapper.selectNoticeByKeyword",paramMap,rowbounds);
		
		return nList;
	}

}
