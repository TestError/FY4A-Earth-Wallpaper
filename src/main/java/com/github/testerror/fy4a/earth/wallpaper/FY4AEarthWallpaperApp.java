package com.github.testerror.fy4a.earth.wallpaper;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;

public class FY4AEarthWallpaperApp {

	public static final String outPut = System.getProperty("user.dir") + "\\output.jpg";

	public static void main(String [] args) throws IOException, InterruptedException {


		while(true){
			Thumbnails.Builder fileBuilder = Thumbnails.fromURLs(Collections.singleton(new URL("http://img.nsmc.org.cn/CLOUDIMAGE/FY4A/MTCC/FY4A_DISK.JPG?random=" + System.currentTimeMillis())));
			BufferedImage bufferedImage = new BufferedImage(
					190,
					80,
					BufferedImage.TYPE_INT_RGB
			);

			BufferedImage lopImage = new BufferedImage(
					60,
					60,
					BufferedImage.TYPE_INT_RGB
			);
			fileBuilder.watermark(Positions.TOP_LEFT,bufferedImage,0.8f);

			fileBuilder.watermark(Positions.BOTTOM_RIGHT,lopImage,1f);


			fileBuilder.scale(0.4).toFile(outPut);
			SPI.INSTANCE.SystemParametersInfo(0x0014, 0,outPut,0);

			System.out.println("当前图片路径"+outPut);
			System.out.println("替换成功");

			Thread.sleep(1200000L);
		}
	}

	public interface SPI extends StdCallLibrary {

		//from MSDN article
		long SPI_SETDESKWALLPAPER = 20;
		long SPIF_UPDATEINIFILE = 0x01;
		long SPIF_SENDWININICHANGE = 0x02;

		SPI INSTANCE = (SPI) Native.load("user32", SPI.class, new HashMap<String, Object>() {
			{
				put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
				put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
			}
		});

		boolean SystemParametersInfo (int one, int two, String s ,int three);
	}

}
