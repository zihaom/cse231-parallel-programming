/*******************************************************************************
 * Copyright (C) 2016-2020 Dennis Cosgrove
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package floodfill.images;

import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class FloodFillUtils {
	private static void clampToBlackAndWhite(WritableImage image) {
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = image.getPixelWriter();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color color = pixelReader.getColor(x, y);
				double opacity = color.getOpacity();
				pixelWriter.setColor(x, y, opacity < 0.5 ? Color.WHITE : Color.BLACK);
			}
		}
	}

	public static WritableImage loadClampedToBlackAndWhiteWritableImage() {
		// source:
		// "https://sites.wustl.edu/publicaffairs/files/2015/07/Washington_University_Monogram_Open1c200-01-17zuofc.png";
		URL url = FloodFillUtils.class.getResource("WashULogo.png");
		Image image = new Image(url.toExternalForm());
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		WritableImage writableImage = new WritableImage(image.getPixelReader(), width, height);
		clampToBlackAndWhite(writableImage);
		return writableImage;
	}
}
