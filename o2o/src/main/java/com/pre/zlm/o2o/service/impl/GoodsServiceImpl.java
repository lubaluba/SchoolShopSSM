package com.pre.zlm.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pre.zlm.o2o.dao.GoodsDao;
import com.pre.zlm.o2o.dao.GoodsImgDao;
import com.pre.zlm.o2o.dto.GoodsExecution;
import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.GoodsImg;
import com.pre.zlm.o2o.enums.GoodsStateEnum;
import com.pre.zlm.o2o.exception.GoodsOperationException;
import com.pre.zlm.o2o.service.GoodsService;
import com.pre.zlm.o2o.utils.ImgUtils;
import com.pre.zlm.o2o.utils.PathUtils;
@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsImgDao goodsImgDao;
	
	/**
	 * 插入商品信息
	 */
	@Override
	@Transactional
	public GoodsExecution addGoods(Goods goods, ImageHolder thumbnail, List<ImageHolder> goodsImgList)
			throws GoodsOperationException {
		if (goods != null && goods.getShop() != null && goods.getShop().getShopId() > 0) {
			goods.setCreateTime(new Date());
			goods.setLastEditTime(new Date());
			//默认上架
			goods.setEnableStatus(1);
			//处理图片
			if (thumbnail != null) {
				try {
					addThumbnail(goods, thumbnail);
				} catch (IOException e) {
					throw new GoodsOperationException("图片转换失败");
				}
			}
			
			try {
				int effectedNum = goodsDao.insertGoods(goods);
				if (effectedNum < 0) {
					throw new GoodsOperationException("添加商品失败");
				}
			} catch (Exception e) {
				throw new GoodsOperationException("添加商品失败" + e.toString());
			}
			
			//处理商品详情图片列表
			if (goodsImgList != null && goodsImgList.size() > 0) {
				addGoodsImgList(goods, goodsImgList);
			}
			return new GoodsExecution(GoodsStateEnum.SUCCESS, goods);
		} else {
			return new GoodsExecution(GoodsStateEnum.EMPTY);
		}
	}
	
	/**
	 * 处理文件缩略图
	 * @param goods
	 * @param thumbnail
	 * @throws IOException 
	 */
	private void addThumbnail(Goods goods, ImageHolder thumbnail) throws IOException {
		String dest = PathUtils.getShopImagePath(goods.getShop().getShopId());
		String thumbnailAddr;
		thumbnailAddr = ImgUtils.generateThumbnail(thumbnail, dest);
		goods.setImgAddr(thumbnailAddr);
	}
	
	/**
	 * 批量添加图片
	 */
	private void addGoodsImgList(Goods goods, List<ImageHolder> goodsImgList) {
		//获取图片储存路径,这里直接存放在相应店铺文件夹底下
		String dest = PathUtils.getShopImagePath(goods.getShop().getShopId());
		
		List<GoodsImg> imgList = new ArrayList<>();
		
		for (ImageHolder img : goodsImgList) {
			String imgAddr = ImgUtils.generateNormalThumbnail(img, dest);
			GoodsImg goodImg = new GoodsImg();
			goodImg.setCreateTime(new Date());
			goodImg.setPriority(0);
			goodImg.setImgAddr(imgAddr);
			goodImg.setGoodsId(goods.getGoodsId());
			imgList.add(goodImg);
		}
		
		if (imgList.size() > 0) {
			try {
				int effectNum = goodsImgDao.batchInsertGoodsImg(imgList);
				if(effectNum < 0) {
					throw new GoodsOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new GoodsOperationException("创建商品详情图片失败" + e.toString());
			}
		}
	}
	
}
