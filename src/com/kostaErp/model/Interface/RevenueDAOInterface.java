package com.kostaErp.model.Interface;

import java.util.List;

import com.kostaErp.model.VO.revenueVO;

public interface RevenueDAOInterface {
	List<revenueVO> getMonthlyMenuSalesRank(String bId, String startDate, String endDate);
	int getRevenue(String bId, String startDate, String endDate);
	int getMonthlyRevenue(String bId, String startDate, String endDate);
}
