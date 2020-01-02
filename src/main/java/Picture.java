import Models.Pixel;
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
    private BufferedImage image;

    @Getter
    private int width;
    @Getter
    private int height;

    public Picture(Picture picture) {
        this.image = deepCopy(picture.image);

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

        image = hugeImage;
//        PictureUtils.convertTo2DUsingGetRGB(hugeImage);
//        imageArray = PictureUtils.convertTo2DUsingGetRGB(hugeImage);
    }

    public void savePicture(String filePath) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                bufferedImage.setRGB(j, i, image.getRGB(j, i));
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
//                imageArray[i][j] = new Pixel(0, 0, 0, 0);
                image.setRGB(i, j, new Color(0, 0, 0).getRGB());
            }
        }
    }

    public void setPixel(int heightN, int widthN, Color pixel) {
        image.setRGB(widthN, heightN, new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()).getRGB());
//        imageArray[heightN][widthN] = pixel;
    }

    public Color getPixelWithoutException(int heightN, int widthN) {
        if (heightN >= height)
            heightN = height - 1;
        else if (heightN < 0)
            heightN = 0;

        if (widthN >= width)
            widthN = width - 1;
        else if (widthN < 0)
            widthN = 0;

//        return imageArray[heightN][widthN];
        return new Color(image.getRGB(widthN, heightN));
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
