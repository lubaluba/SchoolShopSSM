package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.HeadLine;

public class HeadLineDaoTest extends BaseTest {
	
	@Autowired
	HeadLineDao dao;
	
	@Test
	public void getHeadLineByCondition() {
		HeadLine headLineCondition = new HeadLine();
		headLineCondition.setEnableStatus(1);
		int res = dao.getHeadLineList(headLineCondition).size();
		assertEquals(1, res);
		headLineCondition.setEnableStatus(0);
		int res2 = dao.getHeadLineList(headLineCondition).size();
		assertEquals(0, res2);
	}
	
	@Test
	public void getHeadLineById() {
		HeadLine hl = dao.getHeadLineById(1L);
		assertEquals("1", hl.getLineName());
	}
}
