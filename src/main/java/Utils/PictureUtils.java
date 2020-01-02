package Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PictureUtils {

    public static Color[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];

        for (int row = 0; row < height; row++) {
            System.out.println(row);
            for (int col = 0; col < width; col++) {
//                result[row][col] = new Pixel(image.getRGB(col, row));
                result[row][col] = new Color(image.getRGB(col, row));
            }
        }

        return result;
    }
}
