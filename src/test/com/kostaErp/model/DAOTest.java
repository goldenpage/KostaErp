package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;
import com.kostaErp.model.menuDAO;
import com.kostaErp.model.menuVO;
import com.kostaErp.model.userInfoVO;

public class DAOTest {

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
    public void 마케팅테스트() throws ClassNotFoundException {
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