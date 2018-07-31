package com.pre.zlm.o2o.service;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.Area;
import com.pre.zlm.o2o.service.AreaService;
public class AreaServiceTest extends BaseTest{
	@Autowired
	AreaService service;
	@Test
	public void test() {
		List<Area> list =service.getAreaList();
		assertEquals("麓山南路", list.get(0).getAreaName());
	}
	
}
