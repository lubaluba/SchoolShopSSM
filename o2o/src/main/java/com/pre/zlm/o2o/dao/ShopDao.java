package com.pre.zlm.o2o.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.Shop;
public interface ShopDao {
	
	/**
	 * 新增店铺
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺信息
	 */
	int updateShop(Shop shop);
	
	/**
	 * 根据shopId查询店铺信息
	 */
	Shop getShopById(Long shopId);
	
	/**
	 * 分页查询店铺列表,可指定条件：店铺名(模糊),店铺状态,店铺类别,区域id,owner
	 * @param shopCondition :查询条件
	 * @param rowIndex : 从第几行开始查询
	 * @param pageSize : 每页显示条数
	 * @return
	 */
	List<Shop> getShopList(@Param("shopCondition")Shop shopCondition, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	
	/**
	 * 条件查询
	 * @return 返回分页条件查询(getShopList)的总条目数
	 */
	int getShopListCount(@Param("shopCondition")Shop shopCondition);
}
