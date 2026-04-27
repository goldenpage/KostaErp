package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;
import com.kostaErp.model.menuDAO;
import com.kostaErp.model.menuVO;

public class DAOTest {

    private String bId = "1020260424";

    @Test
    public void getFoodMaterialCountTest() {
        foodMaterialDAO dao = new foodMaterialDAO();

        int count = dao.getFoodMaterialCount(bId);

        System.out.println("식자재 개수 : " + count);

        assertTrue(count >= 0);
    }

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
}