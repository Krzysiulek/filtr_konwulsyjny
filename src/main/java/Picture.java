import Models.Pixel;
import Utils.PictureUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    @Getter
//    private BufferedImage image;
    private int[][] image;

    @Getter
    private int width;
    @Getter
    private int height;

    public Picture(Picture picture) {
//        this.image = deepCopy(picture.image);

        this.width = picture.width;
        this.height = picture.height;
    }

    public Picture(String filePath) throws IOException {
        loadPicture(filePath);
    }

    public void loadPicture(String filePath) throws IOException {
        BufferedImage hugeImage = ImageIO.read(new File(filePath));
        width = hugeImage.getWidth();
        height = hugeImage.getHeight();

//        image = hugeImage;

//        PictureUtils.convertTo2DUsingGetRGB(hugeImage);
        image = PictureUtils.convertTo2DToints(hugeImage);
    }

    public void savePicture(String filePath) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                bufferedImage.setRGB(j, i, image.getRGB(j, i));
                bufferedImage.setRGB(j, i, image[i][j]);
            }
        }

        File file = new File(filePath);
        if (!ImageIO.write(bufferedImage, "png", file)) {
            throw new Exception("Cannot save file");
        }
    }

    public void setAllPixelsToZero() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
//                image.setRGB(i, j, new Color(0, 0, 0).getRGB());
                image[j][i] = 0;
            }
        }
    }

    public void setPixel(int heightN, int widthN, Color pixel) {
//        image.setRGB(widthN, heightN, new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()).getRGB());
        image[heightN][widthN] = pixel.getRed();
//        imageArray[heightN][widthN] = pixel;
    }

    public void setPixel(int heightN, int widthN, int pixel) {
//        image.setRGB(widthN, heightN, 1, 1, new int[]{pixel}, 0, 0);
//        imageArray[heightN][widthN] = pixel;
    }

    public int getPixelWithoutException(int heightN, int widthN) {
        if (heightN >= height)
            heightN = height - 1;
        else if (heightN < 0)
            heightN = 0;

        if (widthN >= width)
            widthN = width - 1;
        else if (widthN < 0)
            widthN = 0;

//        return new Color(image[heightN][widthN]);
        return image[heightN][widthN];
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static void savePicture(int[][] pic, String filePath) throws Exception {
        int width = pic[0].length;
        int height = pic.length;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = new Color(pic[i][j]).getRGB();
                bufferedImage.setRGB(j, i, rgb);
            }
        }

        File file = new File(filePath);
        if (!ImageIO.write(bufferedImage, "png", file)) {
            throw new Exception("Cannot save file");
        }
    }
}
