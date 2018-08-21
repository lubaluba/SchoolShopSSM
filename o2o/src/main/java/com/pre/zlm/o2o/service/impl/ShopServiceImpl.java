package com.pre.zlm.o2o.service.impl;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pre.zlm.o2o.dao.ShopDao;
import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.enums.ShopStateEnum;
import com.pre.zlm.o2o.exception.ShopOperationException;
import com.pre.zlm.o2o.service.ShopService;
import com.pre.zlm.o2o.utils.ImgUtils;
import com.pre.zlm.o2o.utils.PageCalculator;
import com.pre.zlm.o2o.utils.PathUtils;

@Service
public class ShopServiceImpl implements ShopService{
	
	@Autowired
	private ShopDao dao;

	@Override
	public ShopExecution listShopByCondition(Shop shopCondition, int pageIndex, int pageSize) {
		ShopExecution shopExecution = new ShopExecution();
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		int count = dao.getShopListCount(shopCondition);
		List<Shop> shopList = dao.getShopList(shopCondition, rowIndex, pageSize);
		if (shopList != null) {	
			shopExecution.setShopList(shopList);
			shopExecution.setCount(count);
			shopExecution.setState(ShopStateEnum.SUCCESS.getState());
		} else {
			shopExecution.setState(ShopStateEnum.QUERY_NULL.getState());
		}

		return shopExecution;
	}
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder shopImg) {
		
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		if(shop.getShopName() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOPNAME);
		}
		shop.setEnableStatus(0);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		
		//插入店铺
		int result=dao.insertShop(shop);
		
		if(result<=0) {
			throw new ShopOperationException("店铺插入失败");
		}
		
		//插入图片并获得图片地址
		if(shopImg.getImage()!=null) {
			try{
				addShopImg(shop, shopImg);
			}catch (Exception e) {
				throw new ShopOperationException("图片插入失败");
			}
		}
		result=dao.updateShop(shop);
		
		if(result<=0) {
			throw new RuntimeException("图片更新失败");
		}
		
		return new ShopExecution(ShopStateEnum.CHECK);
	}
	

	@Override
	public Shop getShopById(Long shopId) {
		return dao.getShopById(shopId);
	}

	@Override
	@Transactional
	public ShopExecution updateShop(Shop shop, ImageHolder shopImg) 
			throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//判断是否需要处理图片
			Shop oldShop = dao.getShopById(shop.getShopId());
			if (shopImg != null && shopImg.getImage() != null && shopImg.getImageName() != null && ! shopImg.getImageName().equals("")) {
				if (oldShop.getShopImg() != null) {
					ImgUtils.deleteFileOrPath(oldShop.getShopImg());
				}
				addShopImg(shop, shopImg);
			}
			//更新店铺
			shop.setLastEditTime(new Date());
			int effectedNum = dao.updateShop(shop);
			if (effectedNum < 1) {
				return new ShopExecution(ShopStateEnum.UPDATE_ERROR);
			} else {
				shop = dao.getShopById(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS, shop);
			}
		} catch(Exception e) {
			throw new ShopOperationException("店铺更新异常" + e.getMessage());
		}
	}

	private void addShopImg(Shop shop, ImageHolder imgHolder) {
		//获取shop图片目录的相对值路径
		String dest=PathUtils.getShopImagePath(shop.getShopId());
		String shopImgAddr =ImgUtils.generateThumbnail(imgHolder, dest);
		shop.setShopImg(shopImgAddr);
	}
	
}