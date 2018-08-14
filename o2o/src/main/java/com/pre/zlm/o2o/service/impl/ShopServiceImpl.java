package com.pre.zlm.o2o.service.impl;
import java.io.InputStream;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pre.zlm.o2o.dao.ShopDao;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.enums.ShopStateEnum;
import com.pre.zlm.o2o.exception.ShopOperationException;
import com.pre.zlm.o2o.service.ShopService;
import com.pre.zlm.o2o.utils.ImgUtils;
import com.pre.zlm.o2o.utils.PathUtils;
@Service
public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDao dao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		if(shop.getShopName() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOPNAME);
		}
		shop.setEnableStatus(0);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		int result=dao.insertShop(shop);
		if(result<=0) {
			throw new ShopOperationException("店铺插入失败");
		}
		//插入图片并获得图片地址
		if(shopImgInputStream!=null) {
			try{
				addShopImg(shop,shopImgInputStream,fileName);
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
	
	private void addShopImg(Shop shop,InputStream shopImgInputStream,String fileName) {
		//获取shop图片目录的相对值路径
		String dest=PathUtils.getShopImagePath(shop.getShopId());
		String shopImgAddr =ImgUtils.generateThumbnail(shopImgInputStream,dest,fileName);
		shop.setShopImg(shopImgAddr);
	}

}