import Constants.Kernels;
import Utils.TimeCounter;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        final double repetitions = 1;
        Picture picture1 = null;

        Picture picture = new Picture(Paths.get("src/main/resources/k1.jpg").toAbsolutePath().toString());
        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();

        for (int i = 0; i < repetitions; i++) {
            Filter filter = new Filter(picture, Kernels.WYOSTRZAJACY_1);
            picture1 = filter.modifyPicture();
        }


        timeCounter.stop();

        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());
        System.out.println("Execution time multiple: " + timeCounter.getTimeMilis() * 1.0 / repetitions + " ms");
    }
}
