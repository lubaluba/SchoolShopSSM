package com.pre.zlm.o2o.dto;

import java.util.Date;
import java.util.List;

import com.pre.zlm.o2o.entity.ExpenseRecord;

import lombok.Data;

@Data
public class Order {
	
	private String orderId;
	
	private Date orderTime;
	
	private List<ExpenseRecord> recordList;
	
	private Double amount;
	
	private Integer count;
	
	private ExpenseRecord record;
}
