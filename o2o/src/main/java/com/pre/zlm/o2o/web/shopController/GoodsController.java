 package com.pre.zlm.o2o.web.shopController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.dto.GoodsExecution;
import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.enums.GoodsStateEnum;
import com.pre.zlm.o2o.exception.GoodsOperationException;
import com.pre.zlm.o2o.service.GoodsService;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
import com.pre.zlm.o2o.web.BaseController;

@Controller
@RequestMapping("/shopAdmin")
public class GoodsController extends BaseController {
	
	@Autowired
	private GoodsService service;
	
	//支持商品详情图上传最大数量
	private static final int IMAGEMAXCOUNT = 6;
	
	@RequestMapping(value = "/addgoods", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addGood(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		if (!checkCode()) {
			return exceptionResult(result, "验证码错误");
		}
		List<ImageHolder> goodsImgList;	//商品详情图片
		ImageHolder thumbnail = null;	//商品缩略图
		try {
			thumbnail = ResolverImg("thumbnail");
			goodsImgList = ResolverImgList("goodsImg", IMAGEMAXCOUNT);
		} catch (IOException e1) {
			return exceptionResult(result, "处理商品详情图失败");
		}
		
		Goods goods;
		try {
			goods = (Goods) getObject("goodsStr", Goods.class);
		} catch (Exception e1) {
			return exceptionResult(result, "json参数转换异常");
		}
		
		//插入商品信息;
		if(goods == null || thumbnail == null || goodsImgList.size() <= 0) {
			return exceptionResult(result, "请输入商品信息");
		}
		try {
			Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
			Shop shop = new Shop();
			shop.setShopId(currentShop.getShopId());
			goods.setShop(shop);
			GoodsExecution pe = service.addGoods(goods, thumbnail, goodsImgList);
			if (pe.getState() == GoodsStateEnum.SUCCESS.getState()) {
				result.put("success", true);
				return result;
			} else {
				return exceptionResult(result, pe.getStateInfo());
			} 
		} catch (GoodsOperationException e) {
			return exceptionResult(result, e.toString());
		}		
	}
	
}
	
