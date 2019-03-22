package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.ExpenseRecord;

public interface ExpenseRecordDao {
	
	public int insert(ExpenseRecord expenseRecord);
	
	public List<ExpenseRecord> getRecordByUserId(Long userId);
	
	public List<ExpenseRecord> getRecordByShopId(Long shopId);
	
	public List<ExpenseRecord> getRecordByOrderId(@Param("orderId")String orderId, @Param("userId")Long userId);
	
	public List<String> getAllOrderId();
}
