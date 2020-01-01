import Models.Pixel;
import Utils.PictureUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    @Getter
    private Pixel[][] imageArray;

    @Getter
    private int width;
    @Getter
    private int height;

    public Picture(Picture picture) {
        this.imageArray = new Pixel[picture.imageArray.length][];

        for (int i = 0; i < picture.imageArray.length; i++) {
            Pixel[] pixels = new Pixel[picture.imageArray[i].length];

            for (int j = 0; j < picture.imageArray[i].length; j++) {
                pixels[j] = new Pixel(picture.imageArray[i][j]);
            }
            imageArray[i] = pixels;
        }
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

        imageArray = PictureUtils.convertTo2DUsingGetRGB(hugeImage);
    }

    public void savePicture(String filePath) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                bufferedImage.setRGB(j, i, imageArray[i][j].getPixelValue());
            }
        }

        File file = new File(filePath);
        if (!ImageIO.write(bufferedImage, "png", file)) {
            throw new Exception("Cannot save file");
        }
    }

    public void setAllPixelsToZero() {
        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                imageArray[i][j] = new Pixel(255, 0, 0, 0, 0);
            }
        }
    }

    public void setPixel(int heightN, int widthN, Pixel pixel) {
        imageArray[heightN][widthN] = pixel;
    }

    public Pixel getPixelWithoutException(int heightN, int widthN) {
        if (heightN >= imageArray.length)
            heightN = imageArray.length - 1;
        else if (heightN < 0)
            heightN = 0;

        if (widthN >= imageArray[heightN].length)
            widthN = imageArray[heightN].length - 1;
        else if (widthN < 0)
            widthN = 0;

        return imageArray[heightN][widthN];
    }
}
