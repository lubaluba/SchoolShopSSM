package com.pre.zlm.o2o.utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class hh {
	public static void main(String[] args) throws IOException {
		String basePath =Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File file =new File("C:\\Users\\a3325\\Desktop\\cc.jpg");
		File watermark =new File(basePath+"/watermark.jpg");
		Thumbnails.of(file).size(200, 200).
		watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.75f).outputQuality(0.8f).
		toFile("C:\\360极速浏览器下载\\o2oimage\\upload\\item\\shop\\hh.jpg");
	}
}
