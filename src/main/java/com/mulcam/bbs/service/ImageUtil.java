package com.mulcam.bbs.service;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageUtil {

	public String squareImage(String fname, String imgDir) throws Exception {
		File file = new File(imgDir + "/" + fname);
        BufferedImage buffer = ImageIO.read(file);
        int width = buffer.getWidth();
        int height = buffer.getHeight();
        int size = width, x = 0, y = 0;
        if (width > height) {
            size = height;
            x = (width - size) / 2;
        } else if (width < height) {
            size = width;
            y = (height - size) / 2;
        }
        
        String now = LocalDateTime.now().toString().substring(0,22).replaceAll("[-T:.]", "");
        int idx = fname.lastIndexOf('.');
        String format = fname.substring(idx+1);
        String newFname = now + fname.substring(idx);
        
        BufferedImage dest = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buffer, 0, 0, size, size, x, y, x + size, y + size, null);
        g.dispose();
        
        File dstFile = new File(imgDir + "/" + newFname);
        OutputStream os = new FileOutputStream(dstFile);
        ImageIO.write(dest, format, os);
        os.close();
        
		return newFname;
	}
	
	public void resizeImage(String fname, String imgDir) throws Exception {
		File file = new File(imgDir + "/" + fname);
        BufferedImage buffer = ImageIO.read(file);
        int width = buffer.getWidth();
        int height = buffer.getHeight();
        if (width <= 800)
        	return;
        
        int idx = fname.lastIndexOf('.');
        String format = fname.substring(idx+1);
        int targetWidth = 800;
        int targetHeight = height * targetWidth / width;
        
        BufferedImage dest = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buffer, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        OutputStream os = new FileOutputStream(file);
        ImageIO.write(dest, format, os);
        os.close();
	}
	
}
