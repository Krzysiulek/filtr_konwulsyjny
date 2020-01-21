import Constants.Kernels;
import Utils.TimeCounter;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<Integer, Long> times = new HashMap<>();
    static Picture picture;

    public static void main(String[] args) throws Exception {
        String filePath = "src/main/resources/fhd.jpg";
        picture = new Picture(Paths.get(filePath).toAbsolutePath().toString());

        for (int i = 1; i < 9; i+= 2) {
//        for (int i = 1; i < picture.getWidth(); i++) {
            System.out.print("#" + i + " : ");
            runWithThreads(i, 500);

//            while (picture.getWidth() % i != 0) {
//                i++;
//            }
        }

        times
                .forEach((k,v) -> System.out.println("#" + k + ":" + v + " ms. Przyspieszenie: " + times.get(1) * 1.0 / v * 1.0));
    }

    private static void runWithThreads(int threads, int repetitions) throws Exception {
        int[][] picture1 = new int[0][];
        int threadsTmp = 0;
        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();

        for (int i = 0; i < repetitions; i++) {
            Filter filter = new Filter(picture, Kernels.EDGE_DETECTION_4);
            picture1 = filter.modifyPictureUsingThreads(threads);
            threadsTmp = filter.getMaxThreadsAmount();
        }


        timeCounter.stop();
        System.out.println(timeCounter.getTimeMilis() / repetitions);

//        Picture.savePicture(picture1, "src/main/resources/p4_2.png");
        times.put(threadsTmp, timeCounter.getTimeMilis());
    }

    private static void runWithoutThreads() throws Exception {
        final int repetitions = 50;
        Picture picture1 = null;

        System.out.println("XD");
        Picture picture = new Picture(Paths.get("src/main/resources/cat.jpeg").toAbsolutePath().toString());
        System.out.println("XD2");

        TimeCounter timeCounter = new TimeCounter();

        timeCounter.start();

        for (int i = 0; i < repetitions; i++) {
            Filter filter = new Filter(picture, Kernels.EDGE_DETECTION_4);
            picture1 = filter.modifyPicture();
        }


        timeCounter.stop();

        picture1.savePicture(Paths.get("src/main/resources/p3.png").toAbsolutePath().toString());
        System.out.println("Time: " + (timeCounter.getTimeMilis() * 1.0 / repetitions) + " ms without threads");
    }
}
