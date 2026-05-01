package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.disposalDAO;
import com.kostaErp.model.disposalVO;

public class disposalStatisticsAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "disposalStatistics.jsp";
		
		HttpSession session = request.getSession();
		
		String bId = (String) session.getAttribute("loginOK");
		
		String startDate = "2026-04-01";
		String endDate = "2026-05-01";
		
		disposalDAO dao = new disposalDAO();
		try {
			double disposalRate = dao.getDisposalRate(bId, startDate, endDate);
			int totalDisposalPrice = dao.getTotalDisposalPrice(bId, startDate, endDate);
			List<disposalVO> top3List = dao.getTop3DisposalItems(bId, startDate, endDate);
			List<disposalVO> reasonRatioList = dao.getDisposalReasonRatio(bId, startDate, endDate);
			List<disposalVO> dailyTypeList = dao.selectDailyDisposalByType(bId, startDate, endDate);
			
			request.setAttribute("disposalRate", disposalRate);
			request.setAttribute("totalDisposalPrice", totalDisposalPrice);
			request.setAttribute("top3List", top3List);
			request.setAttribute("reasonRatioList", reasonRatioList);
			request.setAttribute("dailyTypeList", dailyTypeList);
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		return null;
	}

}
