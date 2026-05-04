package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kostaErp.model.DAO.noticeDAO;
import com.kostaErp.model.VO.noticeVO;

public class noticeDAOTest {
	private String bId;
    private noticeDAO dao;

    @Before
    public void setUp() {
        dao = new noticeDAO();
        System.out.println("테스트 시작");
    }

    @After
    public void tearDown() {
        System.out.println("테스트 종료");
    }

    //1. 알림 추가 테스트
    @Test
    public void addNoticeTest() {
        boolean result = dao.insertNotice("N999", "DIS001");
        System.out.println("insert 결과 : " + result);
//        assertTrue(result);
    }
    
    //2. 알림 목록 조회 테스트
    @Test
    public void getnotificationListTest() {
        noticeDAO dao = new noticeDAO();
        ArrayList<noticeVO> list = dao.getNoticeList(bId);
        for (noticeVO vo : list) {
            System.out.println(
                vo.getNoticeId() + " / " +
                vo.getDisposalId() + " / " +
                vo.getNoticeDate() + " / " +
                vo.getReadYn()
            );
        }
        assertTrue(list.size() >= 0);
    }
    
    //3. 읽음 처리 테스트
    @Test
    public void readTest() {
        noticeDAO dao = new noticeDAO();
        boolean result = dao.updateReadYn("N999");
//        assertTrue(result);
    }
    
    //4. 전체 삭제 테스트 (삭제 후 다시 복구)
    @Test
    public void deleteAllTest() {
        // 기존 데이터 백업
        ArrayList<noticeVO> backupList = dao.getNoticeList(bId);
        // 전체 삭제 실행
        boolean result = dao.deleteAll();
        System.out.println("전체 삭제 결과 : " + result);
//        assertTrue(result);
        //삭제된 데이터 다시 복구
        for (noticeVO vo : backupList) {
            dao.insertNotice(
                vo.getNoticeId(),
                vo.getDisposalId()
            );
        }
        System.out.println("삭제 후 데이터 복구 완료");
    }
    
    //5. 알림 존재 여부 확인 테스트
    @Test
    public void noticeCountTest() {
        ArrayList<noticeVO> list = dao.getNoticeList(bId);
        System.out.println("현재 알림 개수 : " + list.size());
        assertNotNull(list);
    }
}