package com.pre.zlm.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.entity.HeadLine;
import com.pre.zlm.o2o.entity.ShopCategory;
import com.pre.zlm.o2o.service.HeadLineService;
import com.pre.zlm.o2o.service.ShopCategoryService;
import com.pre.zlm.o2o.web.BaseController;
@Controller
@RequestMapping("/frontend")
public class MainPageController extends BaseController {

	@Autowired
	private ShopCategoryService scService;
	@Autowired
	private HeadLineService hlService;
	
	/**
	 *	初始化前端展示系统的主页信息,包括获取一级店铺类别以及头条列表
	 */
	@RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listMainPageInfo() {
		Map<String, Object> result = new HashMap<>();
		try {
			//获取一级店铺类别列表(parentId为空)
			List<ShopCategory> shopCategoryList = scService.listShopCategory(null);
			result.put("shopCategoryList", shopCategoryList);
		} catch (Exception e) {
			exceptionResult(result, e.getMessage());
		}
		try {
			//获取状态为可用(1)的头条列表
			HeadLine headLineCondition = new HeadLine();
			headLineCondition.setEnableStatus(1);
			List<HeadLine> headLineList = hlService.getHeadLineList(headLineCondition);
			result.put("headLineList", headLineList);
		} catch (Exception e) {
			exceptionResult(result, e.getMessage());
		}
		result.put("success", true);
		return result;
	}
}
