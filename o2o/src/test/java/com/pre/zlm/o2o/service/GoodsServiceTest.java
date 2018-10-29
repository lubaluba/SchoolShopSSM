package com.pre.zlm.o2o.service;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.dto.GoodsExecution;
import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.enums.GoodsStateEnum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsServiceTest extends BaseTest{
	@Autowired
	private GoodsService service;
	
	@Test
	@Ignore
	public void testAAddShop() throws IOException {
		Goods goods = new Goods();
		goods.setCreateTime(new Date());
		goods.setEnableStatus(0);
		goods.setGoodsId(3L);
		
		Shop shop = new Shop();
		shop.setShopId(1L);
		goods.setShop(shop);
		
		GoodsCategory gc = new GoodsCategory();
		gc.setGoodsCategoryId(1L);
		goods.setGoodsCategory(gc);
		
		goods.setGoodsDesc("service测试");
		goods.setGoodsName("service测试啊");
		goods.setImgAddr("都说了service测试嘛");
		goods.setNormalPrice(1000);
		goods.setPriority(2);
		goods.setPromotionPrice(800);
		
		//创建缩略图文件流
		File thumbnailFile = new File("C:/360极速浏览器下载/壁纸/1.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail= new ImageHolder(is, thumbnailFile.getName());
	
		File goodsImg1 = new File("C:/360极速浏览器下载/壁纸/2.jpg");
		File goodsImg2 = new File("C:/360极速浏览器下载/壁纸/3.jpg");
		InputStream is1 = new FileInputStream(goodsImg1);
		InputStream is2 = new FileInputStream(goodsImg2);
		List<ImageHolder> goodsImgList = new ArrayList<>();
		goodsImgList.add(new ImageHolder(is1, goodsImg1.getName()));
		goodsImgList.add(new ImageHolder(is2, goodsImg2.getName()));
		
		GoodsExecution ge = service.addGoods(goods, thumbnail, goodsImgList);
		assertEquals(GoodsStateEnum.SUCCESS.getState(), ge.getState());
	}
	
	@Test
	public void testBModifyGoods() throws Exception {
		Goods goods  = service.getGoodsById(3L);
		goods.setGoodsName("更新");
		File thumbnaliFile = new File("C:/360极速浏览器下载/壁纸/4.jpg");
		ImageHolder thumbnail = new ImageHolder(new FileInputStream(thumbnaliFile), thumbnaliFile.getName());
		File im1 = new File("C:/360极速浏览器下载/壁纸/5.jpg");
		File im2 = new File("C:/360极速浏览器下载/壁纸/6.jpg");
		List<ImageHolder> list = new ArrayList<>();
		list.add(new ImageHolder(new FileInputStream(im1), im1.getName()));
		list.add(new ImageHolder(new FileInputStream(im2), im2.getName()));
		GoodsExecution ge = service.modifyGoods(goods, thumbnail, list);	
		assertEquals(GoodsStateEnum.SUCCESS.getState(), ge.getState());
	}
}
