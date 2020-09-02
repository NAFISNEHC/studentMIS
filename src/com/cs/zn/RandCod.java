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
	
	// ��֤���ַ���
	private static final char[] chars = { 
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
  
	// �ַ�����
	private final int SIZE = 4;
  
	// ����������
	private final int LINES = 5;
  
	// ���
	private final int WIDTH = 80;
  
	// �߶�
	private final int HEIGHT = 35;
  
	// �����С
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
    
  		//	ͼƬ��ʽ�봴����ʽƥ��
  		ImageIO.write(image, "png", os);
  		os.close();
  	}
	
	public Object[] createImage() {
    StringBuffer sb = new StringBuffer();
    
    // 	�����հ�ͼƬ
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    
    // 	��ȡͼƬ����
    Graphics graphic = image.getGraphics();
    
    // 	���û�����ɫ
    graphic.setColor(Color.LIGHT_GRAY);
   
    // 	���ƾ��α���
    graphic.fillRect(0, 0, WIDTH, HEIGHT);
    
    // 	������ַ�
    Random ran = new Random();
    for (int i = 0; i <SIZE; i++) {
     
    	// 	ȡ����ַ�����
    	int n = ran.nextInt(chars.length);
    	
    	// 	���������ɫ
    	graphic.setColor(getRandomColor());
    	
    	// 	���������С
    	graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
      
    	// 	���ַ�
    	graphic.drawString(chars[n] + "", i * WIDTH / SIZE, HEIGHT / 2 + 10);
      
    	// 	��¼�ַ�
    	sb.append(chars[n]);
    }
    
    // ��������
    for (int i = 0; i < LINES; i++) {
      
    	// 	���������ɫ
    	graphic.setColor(getRandomColor());
    		
    	//	 �������
    	graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
    }
    
    	//	����ͼƬ��֤���ֵ
    	strr = sb.toString();
   
    	//	������֤���ͼƬ
    	return new Object[]{strr, image};
	}
  
  	public Color getRandomColor() {
  		Random ran = new Random();
  		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
    	return color;
  	}
  
}
