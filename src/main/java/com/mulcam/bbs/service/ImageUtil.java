package com.mulcam.bbs.service;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

public class ImageUtil {
	private static BufferedImage buffer;

	public static void main(String[] args) throws Exception {
		String fname = "런지.png";
		File file = new File("/Users/student/Pictures/" + fname);
		buffer = ImageIO.read(file);
		int width = buffer.getWidth();
		int height = buffer.getHeight();
		int size=width, x=0, y=0;
		if (width > height) {
			size = width;
			x = (width - size) / 2;
			y = 0;
		} else if (width < height) {
			size = height;
			x = 0;
			y = (height - size) / 2;
		} else {
			System.out.println("width = height = " + width);
		}
		String now = LocalDateTime.now().toString().substring(0,22).replaceAll("[-T:.]", "");
        int idx = fname.lastIndexOf('.');
        String format = fname.substring(idx+1);
        fname = now + fname.substring(idx);	// 유니크한 파일 이름으로 변경
        
        BufferedImage dest = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buffer, 0, 0, size, size, x, y, x + size, y + size, null);
        g.dispose();
        
        File dstFile = new File("/Users/student/Pictures/" + fname);
        OutputStream os = new FileOutputStream(dstFile);
        ImageIO.write(buffer, format, os);
        os.close();
	}

}
