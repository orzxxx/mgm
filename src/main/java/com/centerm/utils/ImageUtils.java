package com.centerm.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.web.multipart.MultipartFile;

import com.centerm.exception.BusinessException;
import com.centerm.model.menu.Sreenshot;

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
	
	public static boolean imageFormatValidate(MultipartFile image){
		String fileName = image.getOriginalFilename().toLowerCase();
		return imageFormatValidate(fileName, legalFormats);
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
	/**
	 * 生成临时文件名称
	 */
	public static File getTempImageFile() throws Exception{
		try{
			//重命名
			String newFileName = PropertyUtils.getProperties("tempSavePath")+"/"+UUID.randomUUID()+".jpg";
			return new File(newFileName);
		}catch(Exception e){
			throw new BusinessException("图片解析失败");	
		}
	}
	/**
	 * 通过文件解析图片
	 */
	public static File getImageFile(String mchntCd, Sreenshot ss, int type) throws Exception{
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
			File tempFile = new File(ss.getPath());
			if (!tempFile.exists()) {
				throw new BusinessException("图片解析异常:找不到临时文件");	
			}
			/*if (width <  ss.getWidth()|| height < ss.getHeight()) {
				throw new BusinessException("图片解析异常:截图大小大于原图");	
			}*/
			BufferedImage image;
			try {
				image = ImageIO.read(tempFile);
			} catch (IIOException e) {
				image = RGB2CMYK(tempFile);
			}
			
			image = snapshot(image, ss.getX(), ss.getY(), ss.getWidth(), ss.getHeight());
			image = compress(image, width, height);
			
			//重命名
			String newFileName = getFileName(mchntCd, false);
			//保存图片
			File newPictFile = new File(filePath+"/"+mchntCd+"/"+newFileName);
			File dir = new File(filePath+"/"+mchntCd+"/");
			if (!dir.exists()) {
				dir.mkdir();
			}
			ImageIO.write(image, "jpg", newPictFile);
			//清除临时图片
			//tempFile.delete();
			return newPictFile;
		}catch(BusinessException e){
			throw new BusinessException(e.getMessage());	
		}catch(Exception e){
			throw new BusinessException("图片解析失败");	
		}
	}
	
	private static BufferedImage snapshot(BufferedImage image, Double x, Double y,
			Double width, Double height) {
		return snapshot(image, x.intValue(), y.intValue(), width.intValue(), height.intValue());
	}

	/**
	 * 通过输入流解析图片
	 *//*
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
			String newFileName = getFileName(mchntCd, false);
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
			System.out.println(e);
			throw new BusinessException("图片解析失败");	
		}
	}*/
	
	public static String getFileName(String mchntCd, boolean isTemp){
		String temp = isTemp ? "_temp" : "";
		Random random = new Random();
		return mchntCd+"_"+DateUtils.getCurrentDate("yyyyMMddhhmmss")+"_"+
				String.format("%04d",random.nextInt(9999))+temp+".jpg";
	}
	/**
	 * 图片截取
	 */
	private static BufferedImage snapshot(BufferedImage img, int x, int y, 
			int width, int height){
		//截取
		Rectangle bound = new Rectangle(x, y, width, height);  
		BufferedImage newImg = new BufferedImage(bound.width, bound.height, 1);
		Graphics g = newImg.getGraphics();
		g.drawImage(img.getSubimage(bound.x, bound.y,  
		     bound.width, bound.height), 0, 0, null);  
		g.dispose();  
		return newImg;  
	}
	/**
	 * 图片压缩
	 */
	private static BufferedImage compress(BufferedImage img, int width, int height) throws Exception{
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		w = width;
		h = height;
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
	    image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
	    return image;
	}
	/**
	 * 图片压缩
	 */
	private static BufferedImage compress(InputStream is, int width, int height) throws Exception{
		BufferedImage img; 
		try {
			img = ImageIO.read(is);//RGB
		} catch (IIOException e) {
			//img = RGB2CMYK(is);//无法通过is解析CMYK。。
			throw new BusinessException("图片解析失败");	
		}
		
	    return compress(img, width, height);
	}
	
	private static BufferedImage RGB2CMYK(File f) throws Exception{
		Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
	    ImageReader reader = null;
	    while(readers.hasNext()) {
	        reader = (ImageReader)readers.next();
	        if(reader.canReadRaster()) {
	            break;
	        }
	    }
	    //File f = new File("E:/nginx-1.8.1/1.jpg");
	    
	    ImageInputStream input =   ImageIO.createImageInputStream(f); 
	    //Stream the image file (the original CMYK image)
	    reader.setInput(input); 

	    //Read the image raster
	    Raster raster = reader.readRaster(0, null); 

	    //Create a new RGB image
	    BufferedImage bi = new BufferedImage(raster.getWidth(), raster.getHeight(), 
	    BufferedImage.TYPE_4BYTE_ABGR); 

	    //Fill the new image with the old raster
	    bi.getRaster().setRect(raster);
	    
	    return bi;
	}
}
