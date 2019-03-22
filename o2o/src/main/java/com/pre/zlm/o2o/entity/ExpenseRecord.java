package com.pre.zlm.o2o.entity;


import java.util.Date;

import lombok.Data;

/**
 * @author 路铭
 * 消费记录
 */
@Data
public class ExpenseRecord {
	
	private Long oid;
	
	private String orderId;
	
	private Double amount;
	
	private Long userId;
	
	private Goods goods;
	
	private Integer count;
	
	private Date buyTime;
	
	private Long shopId;
}
