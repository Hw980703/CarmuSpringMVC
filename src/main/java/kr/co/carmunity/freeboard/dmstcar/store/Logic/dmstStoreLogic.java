package kr.co.carmunity.freeboard.dmstcar.store.Logic;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


import kr.co.carmunity.freeboard.dmstcar.domain.PageInfo;
import kr.co.carmunity.freeboard.dmstcar.domain.dmstFreeBoard;
import kr.co.carmunity.freeboard.dmstcar.store.dmstStore;

@Repository
public class dmstStoreLogic implements dmstStore{

	@Override
	public int korFreeWrite(SqlSession session, dmstFreeBoard dsmt) {
		
		int result = session.insert("korFreeBoard.writeKorFree", dsmt);
		
		return result;
	}

	@Override
	public int getListCount(SqlSession session) {
		
int result = session.selectOne("korFreeBoard.selectKorFreeCount");
		
		return result;
	}

	@Override
	public List<dmstFreeBoard> selectNoticeList(SqlSession session, PageInfo pInfo) {
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
				List<dmstFreeBoard> dList = session.selectList("korFreeBoard.korFreeAllSelect",null,rowBounds);
				return dList;
	
	}

	@Override
	public dmstFreeBoard selectByNo(SqlSession session, int korFreeBoardNo) {
		
		dmstFreeBoard dmst = session.selectOne("korFreeBoard.selectDetailByNo",korFreeBoardNo);
		
		return dmst;
	}

	@Override
	public int deleteByNo(SqlSession session, int korFreeBoardNo) {
		
		int result = session.delete("korFreeBoard.korFreeDelete",korFreeBoardNo);
		
		return result;
	}

	@Override
	public int updateByNo(SqlSession session, dmstFreeBoard dmst) {
		
		int result = session.update("korFreeBoard.korFreeChange",dmst);
		
		return result;
	}

}
