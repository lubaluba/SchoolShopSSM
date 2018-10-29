package com.pre.zlm.o2o.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pre.zlm.o2o.dao.HeadLineDao;
import com.pre.zlm.o2o.entity.HeadLine;
import com.pre.zlm.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	HeadLineDao dao;
	
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		return dao.getHeadLineList(headLineCondition);
	}

}
