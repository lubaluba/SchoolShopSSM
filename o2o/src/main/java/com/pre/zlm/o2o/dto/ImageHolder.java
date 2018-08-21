package com.pre.zlm.o2o.dto;

import java.io.InputStream;

import lombok.Data;

/**
 * 对于图片处理的封装类
 */
@Data
public class ImageHolder {
	
	/**
	 * 图片流
	 */
	private InputStream image;
	
	/**
	 * 图片名
	 */
	private String imageName;

	public ImageHolder(InputStream image, String imageName) {
		this.image = image;
		this.imageName = imageName;
	}
	
	
}
