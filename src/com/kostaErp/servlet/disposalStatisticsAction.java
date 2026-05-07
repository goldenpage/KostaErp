package com.kostaErp.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kostaErp.model.DAO.disposalDAO;
import com.kostaErp.model.VO.disposalVO;

public class disposalStatisticsAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "/jsp/disposalStatistics.jsp";

		HttpSession session = request.getSession();
		String bId = (String) session.getAttribute("loginOK");
		
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		monthFormat.setLenient(false);
		dateFormat.setLenient(false);
		Calendar selectedCal = Calendar.getInstance();
		String selectedMonth = request.getParameter("month");
		
		String startDate = "2026-04-01";
		String endDate = "2026-05-01";

		Gson gson = new Gson();

		request.setAttribute("dailyLabelsJson", "[]");
		request.setAttribute("dailyDatasetsJson", "[]");
		request.setAttribute("reasonLabelsJson", "[]");
		request.setAttribute("reasonValuesJson", "[]");

		disposalDAO dao = new disposalDAO();

		try {
			double disposalRate = dao.getDisposalRate(bId, startDate, endDate);
			int totalDisposalPrice = dao.getTotalDisposalPrice(bId, startDate, endDate);
			List<disposalVO> top3List = dao.getTop3DisposalItems(bId, startDate, endDate);
			List<disposalVO> reasonRatioList = dao.getDisposalReasonRatio(bId, startDate, endDate);
			List<disposalVO> dailyTypeList = dao.selectDailyDisposalByType(bId, startDate, endDate);

			List<String> reasonLabels = new ArrayList<String>();
			List<Double> reasonValues = new ArrayList<Double>();

			for (disposalVO vo : reasonRatioList) {
				reasonLabels.add(vo.getReason());
				reasonValues.add(vo.getReasonRatio());
			}

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
			Map<String, Map<String, Integer>> dailyMap = new LinkedHashMap<String, Map<String, Integer>>();
			List<String> typeList = new ArrayList<String>();

			for (disposalVO vo : dailyTypeList) {
				String day = sdf.format(vo.getDisposalDay());
				String type = vo.getFoodMaterialType();

				if (!dailyMap.containsKey(day)) {
					dailyMap.put(day, new LinkedHashMap<String, Integer>());
				}

				if (!typeList.contains(type)) {
					typeList.add(type);
				}

				dailyMap.get(day).put(type, vo.getDisposalCount());
			}

			List<String> dailyLabels = new ArrayList<String>(dailyMap.keySet());
			List<Map<String, Object>> dailyDatasets = new ArrayList<Map<String, Object>>();

			String[] colors = { "#2563eb", "#dc2626", "#16a34a", "#f59e0b" };

			for (int i = 0; i < typeList.size(); i++) {
				String type = typeList.get(i);
				List<Integer> data = new ArrayList<Integer>();

				for (String day : dailyLabels) {
					Integer count = dailyMap.get(day).get(type);
					data.add(count == null ? 0 : count);
				}

				Map<String, Object> dataset = new LinkedHashMap<String, Object>();
				dataset.put("label", type);
				dataset.put("data", data);
				dataset.put("borderColor", colors[i % colors.length]);
				dataset.put("backgroundColor", colors[i % colors.length]);
				dataset.put("borderDash", Arrays.asList(6, 4));
				dataset.put("tension", 0.3);

				dailyDatasets.add(dataset);
			}

			request.setAttribute("disposalRate", disposalRate);
			request.setAttribute("totalDisposalPrice", totalDisposalPrice);
			request.setAttribute("top3List", top3List);
			request.setAttribute("reasonRatioList", reasonRatioList);
			request.setAttribute("dailyTypeList", dailyTypeList);

			request.setAttribute("reasonLabelsJson", gson.toJson(reasonLabels));
			request.setAttribute("reasonValuesJson", gson.toJson(reasonValues));
			request.setAttribute("dailyLabelsJson", gson.toJson(dailyLabels));
			request.setAttribute("dailyDatasetsJson", gson.toJson(dailyDatasets));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return url;
	}
}
