package Utils;

import Models.Pixel;

import java.awt.image.BufferedImage;

public class PictureUtils {

    public static Pixel[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Pixel[][] result = new Pixel[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = new Pixel(image.getRGB(col, row));
            }
        }

        return result;
    }
}
