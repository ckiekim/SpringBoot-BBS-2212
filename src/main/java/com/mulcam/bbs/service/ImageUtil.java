package com.mulcam.bbs.service;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageUtil {
	
	@Value("${spring.servlet.multipart.location}") private String uploadDir;

	public String squareImage(String fname) throws Exception {
		File file = new File(uploadDir + "/" + fname);
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
        
        File dstFile = new File(uploadDir + "/" + newFname);
        OutputStream os = new FileOutputStream(dstFile);
        ImageIO.write(dest, format, os);
        os.close();
        
		return newFname;
	}
	
	public void resizeImage(String fname) throws Exception {
		File file = new File(uploadDir + "/" + fname);
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
	
	public byte[] squareImage(byte[] image, String format) throws Exception {
		InputStream is = new ByteArrayInputStream(image);
		BufferedImage buffer = ImageIO.read(is);
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
        
        BufferedImage dest = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buffer, 0, 0, size, size, x, y, x + size, y + size, null);
        g.dispose();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(dest, format, baos);
        byte[] bytes = baos.toByteArray();
		
		return bytes;
	}
	
}
