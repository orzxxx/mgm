package com.centerm.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.centerm.exception.BusinessException;

public class ImageUtils {
	private final static String[] legalFormats = new String[]{"gif", "jpg", "jpeg", "png"};
	public final static int MENU = 1;
	private final static int MENU_WIDTH = 540;
	private final static int MENU_HEIGHT = 405;
	public final static int PROMOTION = 2;
	private final static int PROMOTION_WIDTH = 450;
	private final static int PROMOTION_HEIGHT = 600;
	public final static int LOGO = 3;
	private final static int LOGO_WIDTH = 100;
	private final static int LOGO_HEIGHT = 100;
	public static void compress(){
		
	}
	
	public static String getImageFormat(){
		return "";
	}
	
	public static File getFile(){
		return null;
	}
	
	public static boolean imageFormatValidate(MultipartFile image){
		return imageFormatValidate(image, legalFormats);
	}
	
	public static boolean imageFormatValidate(String fileName){
		return imageFormatValidate(fileName, legalFormats);
	}
	
	public static boolean imageFormatValidate(String fileName, String[] formats){
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		for (String format : formats) {
			if (format.equals(suffix)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean imageFormatValidate(MultipartFile image, String[] formats){
		String fileName = image.getOriginalFilename().toLowerCase();
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		for (String format : formats) {
			if (format.equals(suffix)) {
				return true;
			}
		}
		return false;
	}
	
	/*public static File getImageFile(String mchntCd, InputStream is) throws Exception{
		//对图片数据进行处理
		byte[] bPictureData = Base64.decodeBase64(IOUtils.toString(is));
		
		//图片不允许大于100KB
		if(bPictureData.length > 100000)
		{
			throw new BusinessException("图片数据过大");
		}
		
		String filePath = PropertyUtils.getProperties("imageSavePath");
		//重命名
		Random random = new Random();
		String newFileName = mchntCd+"_"+DateUtils.getCurrentDate("yyyyMMddhhmmss")+"_"+
			String.format("%04d",random.nextInt(9999))+".jpg";
		//保存图片
		File newPictFile = new File(filePath+"/"+newFileName);
		FileOutputStream fileOutputStream = new FileOutputStream(newPictFile);
		fileOutputStream.write(bPictureData);
		fileOutputStream.flush();
		fileOutputStream.close();
		//return new File(filePath+"/"+newFileName);
		return newPictFile;
	}*/
	public static File getImageFile(String mchntCd, InputStream is, int type) throws Exception{
		try{
			String filePath = "";
			int width = 0;
			int height = 0;
			if (type == MENU) {
				filePath = PropertyUtils.getProperties("imageSavePath");
				width = MENU_WIDTH;
				height = MENU_HEIGHT;
			} else if(type == PROMOTION){
				filePath = PropertyUtils.getProperties("imageSavePath");
				width = PROMOTION_WIDTH;
				height = PROMOTION_HEIGHT;
			} else {
				filePath = PropertyUtils.getProperties("logoSavePath");
				width = LOGO_WIDTH;
				height = LOGO_HEIGHT;
			}
			BufferedImage image = compress(is, width, height);
		    
		    //重命名
			String newFileName = getFileName(mchntCd);
			//保存图片
			File newPictFile = new File(filePath+"/"+mchntCd+"/"+newFileName);
			File dir = new File(filePath+"/"+mchntCd+"/");
			if (!dir.exists()) {
				dir.mkdir();
			}
			ImageIO.write(image, "jpg", newPictFile);
			//return new File(filePath+"/"+newFileName);
			return newPictFile;
		}catch(Exception e){
			throw new BusinessException("图片解析失败");	
		}
	}
	
	public static String getFileName(String mchntCd){
		Random random = new Random();
		return mchntCd+"_"+DateUtils.getCurrentDate("yyyyMMddhhmmss")+"_"+
				String.format("%04d",random.nextInt(9999))+".jpg";
	}
	
	private static BufferedImage compress(InputStream is, int width, int height) throws Exception{
		Image img = ImageIO.read(is);
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		w = width;
		h = height;
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
	    image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
	    return image;
	}
	
}
