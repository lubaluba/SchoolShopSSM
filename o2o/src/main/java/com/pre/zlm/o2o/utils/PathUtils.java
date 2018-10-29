package com.pre.zlm.o2o.utils;

public class PathUtils {
	//获取当前系统的分隔符
	private static String seperator = System.getProperty("file.separator");
	
	public static String getImgBasePath() {
		//获得系统名称
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "C:/360极速浏览器下载/o2oimage/";
		} else {
			basePath = "/home/zlm/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	public static String getShopImagePath(long shopId){
		String imagePath = "upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}
	
	public static String getGoodsImgePath(long shopId) {
		String imagePath = "upload/item/shop/" + shopId + "/goods/";
		return imagePath.replace("/", seperator);
	}
}
