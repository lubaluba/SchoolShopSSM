package com.pre.zlm.o2o.utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
public class ImgUtils {
	
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final Random r = new Random();
	
	public static String generateThumbnail(InputStream thumbnailInputStream, String targetAddr, String fileName) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName+extension;
		File dest = new File(PathUtils.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnailInputStream).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")), 0.75f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	//产生随机文件名
	public static String getRandomFileName() {
		//获得随机五位数
		int rannum = r.nextInt(89999)+10000;
		String nowTimestr = format.format(new Date());
		return nowTimestr + rannum;
	}
	//获取输入文件流的扩展名
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	//创建目标路径所涉及到的目录 
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtils.getImgBasePath()+targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}	
	
	/**
	 * @param storePath
	 * 先判断storePath是文件的路径还是目录的路径
	 * 如果storePath是文件路径,则删除该文件
	 * 如果storePath是目录的路径,则删除该目录下所有文件
	 * 删除图片文件
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtils.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				ArrayList<File> list =new ArrayList<>(Arrays.asList(fileOrPath.listFiles()));
				while(list.size() > 0) {
					File f = list.get(0);
					if(f.isDirectory()) {
						File[] files = fileOrPath.listFiles();
						for(int i = 0; i < files.length; i++) {
							list.add(files[i]);
						}	
					} else {
						f.delete();
					}
					list.remove(0);
				}
			} else {
				fileOrPath.delete();
			}
		}
	}
}
