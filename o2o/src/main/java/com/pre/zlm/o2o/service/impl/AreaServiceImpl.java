package com.pre.zlm.o2o.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pre.zlm.o2o.cache.RedisCacheUtil;
import com.pre.zlm.o2o.dao.AreaDao;
import com.pre.zlm.o2o.entity.Area;
import com.pre.zlm.o2o.exception.AreaOpreationException;
import com.pre.zlm.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private RedisCacheUtil rcu;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Area> getAreaList(){
		List<Area> areaList = null;
		try {
			if (! rcu.isCacheExist("arealist")) {
				areaList = areaDao.listArea();
				rcu.putInCache(areaList, "arealist");
			} else {
				areaList = (List<Area>) rcu.getCache("arealist", ArrayList.class, Area.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AreaOpreationException(e.getMessage());
		}
		return areaList;
	}
}
