package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kostaErp.model.DBCP;
import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;
import com.kostaErp.model.menuDAO;
import com.kostaErp.model.userDAO;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;
import com.kostaErp.model.menuDAO;
import com.kostaErp.model.menuVO;
import com.kostaErp.model.userInfoVO;

public class DAOTest {

	private foodMaterialDAO dao;
	private menuDAO dao2;
	private userDAO dao3;
	private Connection conn;

	@Before
	public void setUp() throws Exception {
		dao = new foodMaterialDAO();
		dao2 = new menuDAO();
		dao3 = new userDAO();
		conn = DBCP.getConnection();
		conn.setAutoCommit(false);
	}

	// 1. 식자재 입력
	//@Test
	public void addFoodMaterial(){
		int result = dao.addFoodMaterial("단무지", "PP", 5, 1500, 15000, "하나로마트", "고체", 
				"2026-04-27", "2026-04-30", "0000000000");
		assertTrue("입력 실패", result > 0);

		//        int result = dao.addFoodMaterial("단무지", "PP", -1, 1500, 15000,"하나로마트", "고체", 
		//        		"2026-04-27", "2026-04-30", "0000000000");
		//            assertTrue("입력실패", 0);
	}

	// 2. 카테고리 추가
	//@Test
	public void addFoodCategory(){
		int result = dao.addFoodCategory("TE", "테스트");
		assertTrue("추가 성공", result > 0);
		//        int result = dao.addFoodCategory("PP", "가공식품");
		//        assertEquals("추가실패", 0, result);
	}

	// 3. 카테고리 삭제
	//@Test
	public void deleteFoodCategory(){
		int result = dao.deleteFoodCategory("기타");
		assertTrue("삭제성공", result > 0);
		//        int result = dao.deleteFoodCategory("없는카테고리");
		//        assertEquals("삭제실패", 0, result);
	}

	// 4. 식자재 검색
	//@Test
	public void getFoodMaterial(){
		List<foodMaterialVO> list = dao.getFoodMaterialByName("단무지");
		assertTrue("검색성공", list.size() > 0);
		//        List<foodMaterialVO> list = dao.getFoodMaterialByName("컴퓨터");
		//        assertTrue("검색실패", list.isEmpty());
	}
	

	// 5. 메뉴추가
    //@Test
    public void addMenu() {
        int result = dao2.addMenu("참치김밥", 4000, "M001");
        assertTrue("메뉴 추가 성공", result > 0);
//        int result = dao2.addMenu(null, 4000, "M001");
//        assertEquals("메뉴 추가 실패", 0, result);
    }

    
    // 6. 메뉴 카테고리 추가
    //@Test
    public void addMenuCategory() {
        int result = dao2.addMenuCategory("김밥류", "0000000000");
        assertTrue("카테고리 추가 성공", result > 0);
//        int result = dao2.addMenuCategory(null, "0000000000");
//        assertEquals("카테고리 추가 실패", 0, result);
    }

    // 7. 메뉴 카테고리 삭제
    //@Test
    public void deleteMenuCategory_성공() {
        int result = dao2.deleteMenuCategory("음료");
        assertTrue("카테고리 삭제 성공", result > 0);
//        int result = dao2.deleteMenuCategory("ㅁㅁㅁ");
//        assertEquals("카테고리 삭제 실패", 0, result);
    }


    // 8. 사용 식자재 추가
    //@Test
    public void addUsedMaterial_성공() {
        int result = dao2.addUsedMaterial(30, "FM001", "MI001");
        assertTrue("추가 성공", result > 0);
//        int result = dao2.addUsedMaterial(30, null, "MI001");
//        assertEquals("추가 실패", 0, result);
    }

    // 9. 사용 식자재 삭제
    //@Test
    public void deleteUsedMaterial_성공() {
        int result = dao2.deleteUsedMaterial("U002");
        assertTrue("삭제 성공", result > 0);
//        int result = dao2.deleteUsedMaterial("AAA");
//        assertEquals("삭제 실패", 0, result);
    }

    // 10. 회원가입
 	//@Test
 	public void register(){
 		int result = dao3.register("1000000000","김사장","01000000000","kim@naver.com","김밥천국", 
 				"일반음식점","분식","kim123!","2026-04-27","2026-04-27","2026-04-27");
 		assertTrue("입력 성공", result > 0);
 	}
 	
 	// 11. 로그인 테스트
    //@Test
    public void 로그인테스트VO() throws Exception {
        userInfoVO member = dao3.checkMemberByVO("0000000001", "이사장", "lee123!");

        assertNotNull("회원 정보를 찾을 수 없습니다.", member);
        assertEquals("", member.getName());
        assertEquals("", member.getbId());
    }
 
 	// 12. 비밀번호 변경
 	//@Test
 	public void setPw(){
 		int result = dao3.setPw("kim123@", "0000000000", "김사장", "01000000000");
 		assertTrue("입력 성공", result > 0);
 	}
 	
 // 13. 마케팅 동의
    @Test
    public void 마케팅테스트() throws ClassNotFoundException {
        List<userInfoVO> list = dao3.getMarketingMembers();}
    private String bId = "0000000000";
    
    @Test
    public void getFoodMaterialCountTest() {
        foodMaterialDAO dao = new foodMaterialDAO();

        int count = dao.getFoodMaterialCount(bId);

        System.out.println("식자재 개수 : " + count);

        assertTrue(count >= 0);
    }

    

//    @Test
//    public void 로그인테스트VO() throws Exception {
//        foodMaterialDAO dao = new foodMaterialDAO();
//        userInfoVO member = dao.checkMemberByVO("", "", "");
//
//        assertNotNull("회원 정보를 찾을 수 없습니다.", member);
//        assertEquals("", member.getName());
//        assertEquals("", member.getbId());
//    }

   

    @Test
    public void getFoodMaterialListTest() {
        foodMaterialDAO dao = new foodMaterialDAO();

        List<foodMaterialVO> list = dao.getFoodMaterialList(bId, "idDesc", 1, 5);

        for (foodMaterialVO vo : list) {
            System.out.println(
                vo.getFoodMaterialId() + " / " +
                vo.getFoodMaterialName() + " / " +
                vo.getFoodCategory() + " / " +
                vo.getExpirationDate()
            );
        }

        assertNotNull(list);
        assertTrue(list.size() <= 5);
    }

    @Test
    public void 마케팅테스트2() throws ClassNotFoundException {
        foodMaterialDAO dao = new foodMaterialDAO();
        List<userInfoVO> list = dao.getMarketingMembers();
        assertNotNull("조회된 리스트가 null입니다.", list);
                assertTrue("마케팅 동의 회원이 존재하지 않습니다.", list.size() > 0);

        if (!list.isEmpty()) {
            userInfoVO firstMember = list.get(0);
            System.out.println("조회된 첫 번째 회원: " + firstMember.getName());
            System.out.println("마케팅 동의 날짜: " + firstMember.getMarketingDate());
            
            assertNotNull("이름이 누락되었습니다.", firstMember.getName());
            assertNotNull("동의 날짜가 누락되었습니다.", firstMember.getMarketingDate());
        }
    }
    
 	
	@After
	public void tearDown() throws Exception {
		conn.rollback();
		conn.setAutoCommit(true);
		conn.close();
	}
    @Test
    public void getFoodMaterialDetailTest() {
        foodMaterialDAO dao = new foodMaterialDAO();

        List<foodMaterialVO> list = dao.getFoodMaterialList(bId, "idDesc", 1, 5);

        if (list.size() > 0) {
            String foodMaterialId = list.get(0).getFoodMaterialId();

            foodMaterialVO vo = dao.getFoodMaterialDetail(foodMaterialId);

            System.out.println("상세 식자재 : " + vo.getFoodMaterialName());

            assertNotNull(vo);
            assertEquals(foodMaterialId, vo.getFoodMaterialId());
        }
    }

    @Test
    public void getMenuListTest() {
        menuDAO dao = new menuDAO();

        List<menuVO> list = dao.getMenuList(bId);

        for (menuVO vo : list) {
            System.out.println(
                vo.getMenuId() + " / " +
                vo.getMenuName() + " / " +
                vo.getMenuCategory() + " / " +
                vo.getMenuPrice()
            );
        }

        assertNotNull(list);
    }

    @Test
    public void getMenuDetailTest() {
        menuDAO dao = new menuDAO();

        List<menuVO> menuList = dao.getMenuList(bId);

        if (menuList.size() > 0) {
            String menuId = menuList.get(0).getMenuId();

            List<menuVO> detailList = dao.getMenuDetail(menuId);

            for (menuVO vo : detailList) {
                System.out.println(
                    vo.getFoodMaterialName() + " / " +
                    vo.getUsedCount() + "g / " +
                    vo.getFoodMaterialPrice() + "원 / " +
                    vo.getUsedPrice() + "원"
                );
            }

            assertNotNull(detailList);
        }
    }
    @Test
    public void getFoodMaterialPageTest() {
        foodMaterialDAO dao = new foodMaterialDAO();

        List<foodMaterialVO> page1 = dao.getFoodMaterialList(bId, "idDesc", 1, 5);
        List<foodMaterialVO> page2 = dao.getFoodMaterialList(bId, "idDesc", 2, 5);

        assertNotNull(page1);
        assertNotNull(page2);

        assertTrue(page1.size() <= 5);
        assertTrue(page2.size() <= 5);

        System.out.println("1페이지 개수 : " + page1.size());
        System.out.println("2페이지 개수 : " + page2.size());
    }
    @Test
    public void getFoodMaterialListSortTest() {
        foodMaterialDAO dao = new foodMaterialDAO();

        List<foodMaterialVO> idDescList = dao.getFoodMaterialList(bId, "idDesc", 1, 5);
        List<foodMaterialVO> idAscList = dao.getFoodMaterialList(bId, "idAsc", 1, 5);
        List<foodMaterialVO> expAscList = dao.getFoodMaterialList(bId, "expAsc", 1, 5);
        List<foodMaterialVO> expDescList = dao.getFoodMaterialList(bId, "expDesc", 1, 5);

        assertNotNull(idDescList);
        assertNotNull(idAscList);
        assertNotNull(expAscList);
        assertNotNull(expDescList);

        System.out.println("idDesc : " + idDescList.size());
        System.out.println("idAsc : " + idAscList.size());
        System.out.println("expAsc : " + expAscList.size());
        System.out.println("expDesc : " + expDescList.size());
    }
    
    @Test
    public void updateFoodMaterialAfterSaleTest() {
        menuDAO dao = new menuDAO();

        List<menuVO> menuList = dao.getMenuList(bId);

        assertNotNull(menuList);
        assertTrue("메뉴 데이터가 없습니다.", menuList.size() > 0);

        String menuId = null;
        for (menuVO menu : menuList) {
            List<menuVO> detailList = dao.getMenuDetail(menu.getMenuId());

            if (detailList != null && detailList.size() > 0) {
                menuId = menu.getMenuId();
                break;
            }
        }
        assertNotNull("USED에 연결된 메뉴가 없습니다.", menuId);

        boolean result = dao.updateFoodMaterialAfterSale(menuId, 1, bId);
        System.out.println("자동 차감 메뉴ID : " + menuId);
        System.out.println("자동 차감 결과 : " + result);

        assertTrue("식자재 자동 차감 실패", result);
    }

    @Test
    public void updateFoodMaterialAfterSaleFailTest() {
        menuDAO dao = new menuDAO();

        boolean result = dao.updateFoodMaterialAfterSale("NO_MENU", 1, bId);

        System.out.println("없는 메뉴 자동 차감 결과 : " + result);

        assertFalse(result);
    }
}