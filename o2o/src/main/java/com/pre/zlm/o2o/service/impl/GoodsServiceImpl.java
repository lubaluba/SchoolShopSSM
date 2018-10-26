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
			addThumbnail(goods, thumbnail);			
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
	private void addThumbnail(Goods goods, ImageHolder thumbnail) {
		String dest = PathUtils.getGoodsImgePath(goods.getShop().getShopId(), goods.getGoodsId());
		String thumbnailAddr = null;
		try {
			thumbnailAddr = ImgUtils.generateThumbnail(thumbnail, dest);
		} catch (IOException e) {
			throw new GoodsOperationException("图片处理失败");
		}
		goods.setImgAddr(thumbnailAddr);
	}
	
	/**
	 * 批量添加图片
	 */
	private void addGoodsImgList(Goods goods, List<ImageHolder> goodsImgList) {
		//获取图片储存路径,这里直接存放在相应店铺文件夹底下
		String dest = PathUtils.getGoodsImgePath(goods.getShop().getShopId(), goods.getGoodsId());
		List<GoodsImg> imgList = new ArrayList<>();
		for (ImageHolder img : goodsImgList) {
			String imgAddr = ImgUtils.generateNormalThumbnail(img, dest);
			GoodsImg goodImg = new GoodsImg();
			goodImg.setCreateTime(new Date());
			goodImg.setPriority(0);
			goodImg.setImgAddress(imgAddr);
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

	/**
	 * 删除商品详情图片
	 */
	private void deleteGoodsImgs(Long goodsId) {
		List<GoodsImg> list = goodsDao.getGoodsById(goodsId).getImgList();
		for (GoodsImg img : list) {
			ImgUtils.deleteFileOrPath(img.getImgAddress());
		}
		goodsImgDao.deleteGoodsImgByGoodsId(goodsId);
	}
	
	/**
	 * 根据id查询商品
	 */
	@Override
	public Goods getGoodsById(Long goodsId) {
		return goodsDao.getGoodsById(goodsId);
	}
	
	/**
	 * 修改商品信息
	 */
	@Override
	public GoodsExecution modifyGoods(Goods goods, ImageHolder thumbnail, List<ImageHolder> goodsImgList) {
		if (goods != null && goods.getShop() != null && goods.getShop().getShopId() != null) {
			goods.setLastEditTime(new Date());
			//如果缩略图参数有值,从删除原有缩略图并更新
			if (thumbnail != null) {
				Goods oldGoods = goodsDao.getGoodsById(goods.getGoodsId());
				if (oldGoods.getImgAddr() != null) {
					ImgUtils.deleteFileOrPath(oldGoods.getImgAddr());
				}
				addThumbnail(goods, thumbnail);
			}
			//如果商品详情图不为空,则删除之前所有的重新添加
			if (goodsImgList != null && goodsImgList.size() > 0) {
				deleteGoodsImgs(goods.getGoodsId());
				addGoodsImgList(goods, goodsImgList);
			}
			//更新商品信息
			try {
				int effectNum = goodsDao.updateGoods(goods);
				if (effectNum <= 0) {
					throw new GoodsOperationException("更新商品信息失败");
				}
				return new GoodsExecution(GoodsStateEnum.SUCCESS, goods);
			} catch (Exception e) {
				throw new GoodsOperationException("更新商品信息失败" + e.getMessage());
			}
		} else {
			return new GoodsExecution(GoodsStateEnum.EMPTY);
		}
	}
	
}
