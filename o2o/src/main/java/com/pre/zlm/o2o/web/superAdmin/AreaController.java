package com.pre.zlm.o2o.web.superAdmin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.entity.Area;
import com.pre.zlm.o2o.service.AreaService;
@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger =LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService service;
	@RequestMapping(value= {"/listarea"},method=RequestMethod.GET)
	@ResponseBody//自动将返回自转换为json格式给前端
	public Map<String,Object> listArea(){
		logger.info("===start===");
		long startTime =System.currentTimeMillis();
		Map<String,Object> modelMap=new HashMap<>();
		List<Area> list =service.getAreaList();
		modelMap.put("rows", list);
		modelMap.put("total", list.size());
		long endTime =System.currentTimeMillis();
		logger.error("test error");
		logger.debug("costTime:[{}ms]",endTime-startTime);
		logger.info("===end===");
		return modelMap;
	}
}
