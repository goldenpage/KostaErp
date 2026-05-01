package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.foodMaterialDAO;

public class addFoodCategoryAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String foodCategory = request.getParameter("foodCategory");

        if (foodCategory == null || foodCategory.trim().isEmpty()) {
            request.setAttribute("ajaxResponse", "fail|카테고리명을 입력해주세요.");
            return null;
        }

        foodCategory = foodCategory.trim();

        foodMaterialDAO dao = new foodMaterialDAO();
        boolean result = dao.addFoodCategory(foodCategory);

        if (result) {
            request.setAttribute("ajaxResponse", "success|" + foodCategory);
        } else {
            request.setAttribute("ajaxResponse", "fail|카테고리 추가에 실패했습니다.");
        }

        return null;
    }
}
