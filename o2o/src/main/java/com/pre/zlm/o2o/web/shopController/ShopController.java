package com.pre.zlm.o2o.web.shopController;
import java.util.HashMap;
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
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.enums.ShopStateEnum;
import com.pre.zlm.o2o.service.ShopService;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
@Controller
@RequestMapping("/shopAdmin")
public class ShopController{
	@Autowired
	private ShopService service;
	@RequestMapping(value="/registershop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String,Object> modelMap =new HashMap<>();
		//接受并转化相应的参数,包括店铺信息以及图片信息
		String shopStr =HttpServletRequestUtils.getString(request,"shopstr");
		//jackson的用法,将json转换为POJOS
		ObjectMapper mapper =new ObjectMapper();
		Shop shop =null;
		try {
			shop=mapper.readValue(shopStr,Shop.class);
		}catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg","json转换异常");
			return modelMap;
		}
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver cmpr =new CommonsMultipartResolver(request.getSession().getServletContext());
		if(cmpr.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else{
			modelMap.put("success",false);
			modelMap.put("errMsg","上传图片不可为空");
			return modelMap;
		}
		//注册店铺
		try {
			if(shop!=null&&shopImg!=null) {
				ShopExecution se= service.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
				if(se.getState()==ShopStateEnum.CHECK.getState())
					modelMap.put("success", true);
				else
					throw new Exception("插入失败");
				}else 
					throw new Exception("插入失败");					
		}catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
}
