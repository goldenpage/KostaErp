package com.kostaErp.model.DAO.Interface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kostaErp.model.VO.disposalVO;

public interface DisposalDAOInterface {
	List<disposalVO> getDisposals();
	List<String> getFoodMaterialNames();
	List<String> getCategories();
	List<disposalVO> getDisposalsFilteredPaging(
			@Param("bId") String bId, 
		    @Param("category") String category, 
		    @Param("reason") String reason, 
		    @Param("start") int start, 
		    @Param("end") int end);
	int getDisposalCount(String bId, String category, String reason);
	int getTotalCount(
			@Param("bId") String bId, 
		    @Param("category") String category, 
		    @Param("reason") String reason);
	List<String> getReasons();
	List<disposalVO> getDisposalsByCategoryAndBId(String category, String bId);
	List<disposalVO> getDisposalsPaging(String bId, int start, int end);
	boolean updateReason(String disposalId, String reasonId);
	List<String> getExpiredDisposalIds(String bId);
	double getDisposalRate(String bId, String startDate, String endDate);
	int getTotalDisposalPrice(String bId, String startDate, String endDate);
	List<disposalVO> getTop3DisposalItems(String bId, String startDate, String endDate);
	List<disposalVO> getDisposalReasonRatio(String bId, String startDate, String endDate);
	List<disposalVO> selectDailyDisposalAmount(String bId, String startDate, String endDate);
	List<disposalVO> selectDailyDisposalByType(String bId, String startDate, String endDate);
}
