import Constants.Kernels;
import Models.Pixel;
import Utils.TimeCounter;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
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
        int pixel = picture.getPixelWithoutException(0, 0);

//        pixel.setRed(100);
//        pixel.setGreen(100);
//        pixel.setBlue(100);
//
//        picture.setPixel(0, 0, pixel);
//        Assert.assertEquals(100, pixel.getRed());
//        Assert.assertEquals(100, pixel.getBlue());
//        Assert.assertEquals(100, pixel.getGreen());
    }

    @Test
    public void modify() throws Exception {
        Picture picture = new Picture(Paths.get("src/main/resources/t2.jpg").toAbsolutePath().toString());

        Filter filter = new Filter(picture, Kernels.PIRAMIDALNY);
        Picture picture1 = filter.modifyPicture();

        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());

        Filter filter1 = new Filter(picture1, Kernels.DOWN_ONES_5x5);
        Picture picture2 = filter1.modifyPicture();

        picture2.savePicture(Paths.get("src/main/resources/p3_1.png").toAbsolutePath().toString());
    }

    @Test
    public void timeTestThreads() throws Exception {
        final double repetitions = 100;
        Picture picture1 = null;

        Picture picture = new Picture(Paths.get("src/main/resources/big_cat.jpg").toAbsolutePath().toString());
        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();

        for (int i = 0; i < repetitions; i++) {
            Filter filter = new Filter(picture, Kernels.SOBEL);
//            picture1 = filter.modifyPictureUsingThreads(3);
        }
        

        timeCounter.stop();

//        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());
        System.out.println("Execution time multiple: " + timeCounter.getTimeMilis() * 1.0 / repetitions + " ms");
    }

    @Test
    public void timeTest() throws Exception {
        final double repetitions = 10;
        Picture picture1 = null;

        System.out.println("XD");
        Picture picture = new Picture(Paths.get("src/main/resources/big_cat.jpg").toAbsolutePath().toString());
        System.out.println("XD2");

        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();

        for (int i = 0; i < repetitions; i++) {
            System.out.println(i);
            Filter filter = new Filter(picture, Kernels.SOBEL);
            picture1 = filter.modifyPicture();
        }


        timeCounter.stop();

//        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());
        System.out.println("Execution time single: " + timeCounter.getTimeMilis() * 1.0 / repetitions + " ms");
    }
}
