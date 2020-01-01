import Constants.Kernels;
import Models.Pixel;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class PictureTest {

    @Test
    public void loadingPictureTest() throws IOException {
        final int width = 30;
        final int height = 22;

        Picture picture = new Picture(Paths.get("src/main/resources/p1.png").toAbsolutePath().toString());

        Assert.assertEquals(width, picture.getWidth());
        Assert.assertEquals(height, picture.getHeight());
        Pixel pixel  =  picture.getPixelWithoutException(0, 0);

        pixel.setRed(100);
        pixel.setGreen(100);
        pixel.setBlue(100);
        pixel.setAlpha(100);

        picture.setPixel(0, 0, pixel);
        Assert.assertEquals(100, pixel.getAlpha());
        Assert.assertEquals(100, pixel.getRed());
        Assert.assertEquals(100, pixel.getBlue());
        Assert.assertEquals(100, pixel.getGreen());
    }

    @Test
    public void modify() throws Exception {
        Picture picture = new Picture(Paths.get("src/main/resources/t1.jpg").toAbsolutePath().toString());

        Filter filter = new Filter(picture, Kernels.PIRAMIDALNY);
        Picture picture1 = filter.modifyPicture();

        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());

        Filter filter1 = new Filter(picture1, Kernels.DOWN_ONES_5x5);
        Picture picture2 = filter1.modifyPicture();

        picture2.savePicture(Paths.get("src/main/resources/p3_1.png").toAbsolutePath().toString());

    }
}
