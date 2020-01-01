import Constants.Kernels;
import Models.Pixel;
import Utils.TimeCounter;
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
        Pixel pixel = picture.getPixelWithoutException(0, 0);

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

        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();
        Filter filter = new Filter(picture, Kernels.PIRAMIDALNY);
        Picture picture1 = filter.modifyPicture();
        timeCounter.stop();

        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());
        System.out.println("Execution time: " + timeCounter.getTimeMilis() + " ms");
    }

    @Test
    public void timeTest() throws Exception {
        final double repetitions = 20.0;

        Picture picture1 = null;
        Picture picture = new Picture(Paths.get("src/main/resources/t1.jpg").toAbsolutePath().toString());
        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();
        for (int i = 0; i < repetitions; i++) {
            Filter filter = new Filter(picture, Kernels.PIRAMIDALNY);
            picture1 = filter.modifyPicture();
        }
        timeCounter.stop();

        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());
        System.out.println("Execution time: " + timeCounter.getTimeMilis() * 1.0 / repetitions + " ms");
    }
}
