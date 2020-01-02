import Constants.Kernels;
import Utils.TimeCounter;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
//        Picture picture = new Picture("/Users/krzysztofczarnecki/Desktop/p1.png");
//
//        picture.savePicture("/Users/krzysztofczarnecki/Desktop/p2.png");

        final double repetitions = 1;
        Picture picture1 = null;

        System.out.println("XD");
        Picture picture = new Picture(Paths.get("src/main/resources/b1.png").toAbsolutePath().toString());
        System.out.println("XD2");

        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();

        for (int i = 0; i < repetitions; i++) {
            System.out.println(i);
            Filter filter = new Filter(picture, Kernels.EDGE_DETECTION_2);
            picture1 = filter.modifyPicture();
        }


        timeCounter.stop();

        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());
        System.out.println("Execution time single: " + timeCounter.getTimeMilis() * 1.0 / repetitions + " ms");
    }
}
