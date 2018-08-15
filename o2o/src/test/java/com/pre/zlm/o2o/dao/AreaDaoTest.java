package com.pre.zlm.o2o.dao;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.Area;
public class AreaDaoTest extends BaseTest{
	@Autowired
	private AreaDao areaDao;
	@Test
	public void test() {
		List<Area> list =areaDao.listArea();
		assertEquals(2,list.size());
	}
}
