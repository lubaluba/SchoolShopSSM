package com.pre.zlm.o2o.service;
import java.io.InputStream;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;
public interface ShopService {
	/**
	 *	添加店铺
	 */
	ShopExecution addShop(Shop shop,InputStream shopImg,String fileName);
	
	/**
	 * 根据店铺id获取店铺信息
	 */
	Shop getShopById(Long shopId);
	
	/**
	 * 更新店铺信息
	 */
	public ShopExecution updateShop(Shop shop, InputStream shopImgInputStream, String fileName);
}
