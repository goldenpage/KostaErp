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
import com.kostaErp.model.DAO.foodMaterialDAO;
import com.kostaErp.model.VO.foodMaterialVO;

public class usedStatisticsAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "usedStatistics.jsp";

		HttpSession session = request.getSession();
		String bId = (String) session.getAttribute("loginOK");

		Gson gson = new Gson();
		request.setAttribute("rankLabelsJson", "[]");
		request.setAttribute("rankValuesJson", "[]");
		request.setAttribute("monthlyLabelsJson", "[]");
		request.setAttribute("monthlyValuesJson", "[]");

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
			} catch (ParseException ignore) {}
		}

//		String startDate = selectedMonth + "-01";
		String startDate = "2026-04-01";

		Calendar endCal = (Calendar) selectedCal.clone();
		endCal.add(Calendar.MONTH, 1);
//		String endDate = dateFormat.format(endCal.getTime());
		String endDate = "2026-04-30";

		request.setAttribute("selectedMonth", selectedMonth);

		if (bId == null) {
			request.setAttribute("totalExpense", 0);
			request.setAttribute("rankList", new ArrayList<foodMaterialVO>());
			return url;
		}

		foodMaterialDAO dao = new foodMaterialDAO();

		int totalExpense = dao.getFoodMaterialTotalAmount(bId, startDate, endDate);
		List<foodMaterialVO> rankList = dao.getFoodMaterialSpendingRank(bId, startDate, endDate);

		List<String> rankLabels = new ArrayList<String>();
		List<Integer> rankValues = new ArrayList<Integer>();

		for (foodMaterialVO vo : rankList) {
			rankLabels.add(vo.getFoodMaterialName());
			rankValues.add(vo.getTotalExpense());
		}

		List<String> monthlyLabels = new ArrayList<String>();
		List<Integer> monthlyValues = new ArrayList<Integer>();

		Calendar monthCal = (Calendar) selectedCal.clone();
		monthCal.add(Calendar.MONTH, -5);

		for (int i = 0; i < 6; i++) {
			String month = monthFormat.format(monthCal.getTime());
			String monthStart = month + "-01";

			Calendar nextMonthCal = (Calendar) monthCal.clone();
			nextMonthCal.add(Calendar.MONTH, 1);
			String monthEnd = dateFormat.format(nextMonthCal.getTime());

			monthlyLabels.add(month);
			monthlyValues.add(dao.getFoodMaterialTotalAmount(bId, monthStart, monthEnd));

			monthCal.add(Calendar.MONTH, 1);
		}

		request.setAttribute("totalExpense", totalExpense);
		request.setAttribute("rankList", rankList);
		request.setAttribute("rankLabelsJson", gson.toJson(rankLabels));
		request.setAttribute("rankValuesJson", gson.toJson(rankValues));
		request.setAttribute("monthlyLabelsJson", gson.toJson(monthlyLabels));
		request.setAttribute("monthlyValuesJson", gson.toJson(monthlyValues));

		return url;
	}
}
