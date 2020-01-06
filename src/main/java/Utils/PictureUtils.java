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

    public static int[][] convertTo2DToints(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] tmp = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tmp[i][j] = image.getRGB(j, i);
            }
        }

        return tmp;
    }
}
