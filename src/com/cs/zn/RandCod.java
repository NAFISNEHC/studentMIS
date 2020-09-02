package com.cs.zn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class RandCod {
    
	public String strr;
	
	// 验证码字符集
	private static final char[] chars = { 
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
  
	// 字符数量
	private final int SIZE = 4;
  
	// 干扰线数量
	private final int LINES = 5;
  
	// 宽度
	private final int WIDTH = 80;
  
	// 高度
	private final int HEIGHT = 35;
  
	// 字体大小
	private final int FONT_SIZE = 30;	
	
	RandCod(int i) throws IOException {
  		Object[] objs = createImage();
  		BufferedImage image = (BufferedImage) objs[1];
  		String st;
  		String ts;
  		String ss = "00";
  		st = "./src/randCod/";
  		ts = ".png";
  		while(i-- > 0){
  			ss += "0";
  		}
  		OutputStream os = new FileOutputStream(st + ss + ts);
    
  		//	图片格式与创建格式匹配
  		ImageIO.write(image, "png", os);
  		os.close();
  	}
	
	public Object[] createImage() {
    StringBuffer sb = new StringBuffer();
    
    // 	创建空白图片
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    
    // 	获取图片画笔
    Graphics graphic = image.getGraphics();
    
    // 	设置画笔颜色
    graphic.setColor(Color.LIGHT_GRAY);
   
    // 	绘制矩形背景
    graphic.fillRect(0, 0, WIDTH, HEIGHT);
    
    // 	画随机字符
    Random ran = new Random();
    for (int i = 0; i <SIZE; i++) {
     
    	// 	取随机字符索引
    	int n = ran.nextInt(chars.length);
    	
    	// 	设置随机颜色
    	graphic.setColor(getRandomColor());
    	
    	// 	设置字体大小
    	graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
      
    	// 	画字符
    	graphic.drawString(chars[n] + "", i * WIDTH / SIZE, HEIGHT / 2 + 10);
      
    	// 	记录字符
    	sb.append(chars[n]);
    }
    
    // 画干扰线
    for (int i = 0; i < LINES; i++) {
      
    	// 	设置随机颜色
    	graphic.setColor(getRandomColor());
    		
    	//	 随机画线
    	graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
    }
    
    	//	接收图片验证码的值
    	strr = sb.toString();
   
    	//	返回验证码和图片
    	return new Object[]{strr, image};
	}
  
  	public Color getRandomColor() {
  		Random ran = new Random();
  		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
    	return color;
  	}
  
}
