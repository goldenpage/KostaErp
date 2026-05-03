package com.kostaErp.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kostaErp.model.DAO.revenueDAO;
import com.kostaErp.model.VO.revenueVO;

public class revenueStatisticsAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "/jsp/revenueStatistics.jsp";

		HttpSession session = request.getSession();
		String bId = (String) session.getAttribute("loginOK");

		Gson gson = new Gson();

		request.setAttribute("rankLabelsJson", "[]");
		request.setAttribute("rankSalesAmountJson", "[]");
		request.setAttribute("rankSaleCountJson", "[]");
		request.setAttribute("monthlyLabelsJson", "[]");
		request.setAttribute("monthlyRevenueJson", "[]");

		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		monthFormat.setLenient(false);
		dateFormat.setLenient(false);

		Calendar selectedCal = Calendar.getInstance();
		String selectedMonth = request.getParameter("month");

		if (selectedMonth == null || !selectedMonth.matches("\\d{4}-\\d{2}")) {
			selectedMonth = monthFormat.format(selectedCal.getTime());
		}

		try {
			selectedCal.setTime(monthFormat.parse(selectedMonth));
		} catch (ParseException e) {
			selectedMonth = monthFormat.format(Calendar.getInstance().getTime());
			try {
				selectedCal.setTime(monthFormat.parse(selectedMonth));
			} catch (ParseException ignore) {
			}
		}

		String startDate = selectedMonth + "-01";

		Calendar endCal = (Calendar) selectedCal.clone();
		endCal.add(Calendar.MONTH, 1);
		String endDate = dateFormat.format(endCal.getTime());

		request.setAttribute("selectedMonth", selectedMonth);

		if (bId == null) {
			request.setAttribute("totalRevenue", 0);
			request.setAttribute("rankList", new ArrayList<revenueVO>());
			return url;
		}

		revenueDAO dao = new revenueDAO();

		int totalRevenue = dao.getMonthlyRevenue(bId, startDate, endDate);
		List<revenueVO> rankList = dao.getMonthlyMenuSalesRank(bId, startDate, endDate);

		List<String> rankLabels = new ArrayList<String>();
		List<Integer> rankSalesAmount = new ArrayList<Integer>();
		List<Integer> rankSaleCount = new ArrayList<Integer>();

		for (revenueVO vo : rankList) {
			rankLabels.add(vo.getMenuName());
			rankSalesAmount.add(vo.getTotalSalesAmount());
			rankSaleCount.add(vo.getTotalSaleCount());
		}

		List<String> monthlyLabels = new ArrayList<String>();
		List<Integer> monthlyRevenue = new ArrayList<Integer>();

		Calendar monthCal = (Calendar) selectedCal.clone();
		monthCal.add(Calendar.MONTH, -5);

		for (int i = 0; i < 6; i++) {
			String month = monthFormat.format(monthCal.getTime());
			String monthStart = month + "-01";

			Calendar nextMonthCal = (Calendar) monthCal.clone();
			nextMonthCal.add(Calendar.MONTH, 1);
			String monthEnd = dateFormat.format(nextMonthCal.getTime());

			monthlyLabels.add(month);
			monthlyRevenue.add(dao.getMonthlyRevenue(bId, monthStart, monthEnd));

			monthCal.add(Calendar.MONTH, 1);
		}

		request.setAttribute("totalRevenue", totalRevenue);
		request.setAttribute("rankList", rankList);
		request.setAttribute("rankLabelsJson", gson.toJson(rankLabels));
		request.setAttribute("rankSalesAmountJson", gson.toJson(rankSalesAmount));
		request.setAttribute("rankSaleCountJson", gson.toJson(rankSaleCount));
		request.setAttribute("monthlyLabelsJson", gson.toJson(monthlyLabels));
		request.setAttribute("monthlyRevenueJson", gson.toJson(monthlyRevenue));

		return url;
	}

}
