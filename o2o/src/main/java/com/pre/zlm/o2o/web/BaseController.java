package com.pre.zlm.o2o.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.utils.CodeUtil;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;

public class BaseController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	/**
	 *	每个Controller执行之前都会执行该方法,初始化request,response和session
	 */
	@ModelAttribute
	public void inject(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	/**
	 * 处理异常时的map
	 */
	protected Map<String, Object> exceptionResult(Map<String, Object> result, String errMsg){
		result.put("success", false);
		result.put("errMsg", errMsg);
		return result;
	}
	
	/**
	 * 将json参数转换为Object
	 */
	protected Object getObject(String requestAttr, Class<?> clazz) throws Exception {
		//从request中取出参数
		String jsonStr = HttpServletRequestUtils.getString(request, requestAttr);
		
		///jackson的用法,将json转换为POJOS,将参数转换为实体类
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(jsonStr, clazz);
		
		return object;
	}
	
	/**
	 * 验证码校验 
	 */
	protected boolean checkCode(){
		return CodeUtil.checkVerifyCode(request);
	}
	
	/**
	 * 批量处理图片
	 * @throws IOException 
	 */
	protected List<ImageHolder> ResolverImgList(String fileAttrName, int count) throws IOException {
		List<ImageHolder> goodsImgList = new ArrayList<>();
		CommonsMultipartResolver cmpr = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (cmpr.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			for (int i = 0; i < count; i++) {
				CommonsMultipartFile goodsImgFile = (CommonsMultipartFile)multipartHttpServletRequest.getFile(fileAttrName + i);
				if (goodsImgFile != null) {
					ImageHolder goodsImg = new ImageHolder(goodsImgFile.getInputStream(), goodsImgFile.getOriginalFilename());
					goodsImgList.add(goodsImg);
				} else {
					break;
				}
			}
		}
		return goodsImgList;
	}
	
	/**
	 * 处理图片上传(缩略图)
	 * return 
	 * @throws IOException 
	 */
	protected ImageHolder ResolverImg(String fileAttrName) throws IOException{
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver cmpr = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (cmpr.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile(fileAttrName);
			if(shopImg == null) {
				return null;
			}
		} else {
			throw new RuntimeException("图片为空");
		}
		return new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
	}
	
	/**
	 *	对于更新操作的图片处理,因为更新时图片可以为空 
	 */
	protected ImageHolder ResolverImgForUpdate(String fileAttrName)throws RuntimeException, IOException{
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver cmpr = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (cmpr.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile(fileAttrName);	
			if(shopImg == null) {
				return null;
			}
		} else {
			throw new RuntimeException("图片为空");
		}
		return (shopImg == null) ? null :new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
	}
	
	/**
	 * 处理图片上传(详情图片,批量)
	 */
}
