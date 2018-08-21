 package com.pre.zlm.o2o.web.shopController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
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

		//接收前端参数变量的初始化,包括商品,缩略图,详情图列表实体类
		String goodsStr = HttpServletRequestUtils.getString(request, "goodsStr");
		ObjectMapper mapper = new ObjectMapper();
		Goods goods = null;
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> goodsImgList = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		//处理前台传过来的图片流
		try {
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest)request;
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest;
				thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
				
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile goodsImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
					if (goodsImgFile != null) {
						ImageHolder goodsImg = new ImageHolder(goodsImgFile.getInputStream(), goodsImgFile.getOriginalFilename());
						goodsImgList.add(goodsImg);
					} else {
						break;
					}
				}
				
			} else {
				result.put("success", false);
				result.put("errMsg"," 上传图片不可为空");
				return result;
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.toString());
			return result;
		}
		
		try {
			goods = mapper.readValue(goodsStr, Goods.class);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", "参数json转换异常");
			return result;
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
	
