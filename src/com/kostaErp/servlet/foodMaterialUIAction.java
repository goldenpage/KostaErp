package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class foodMaterialUIAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        return "foodMaterials.jsp";
    }
}