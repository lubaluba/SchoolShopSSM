package com.pre.zlm.o2o.service;
import java.io.InputStream;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;
public interface ShopService {
	ShopExecution addShop(Shop shop,InputStream shopImg,String fileName);
}
