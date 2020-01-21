import Constants.Kernels;
import Utils.TimeCounter;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<Integer, Long> times = new HashMap<>();
    private static Picture picture;

    public static void main(String[] args) throws Exception {
        String filePath = "src/main/resources/fhd.jpg";
        picture = new Picture(Paths.get(filePath).toAbsolutePath().toString());

        for (int i = 1; i < 10; i++) {
            System.out.print("#" + i + " : ");
            runWithThreads(i, 500);
        }

        times
                .forEach((k,v) -> System.out.println("#" + k + ":" + v + " ms. Przyspieszenie: " + times.get(1) * 1.0 / v * 1.0));

    }

    private static void runWithThreads(int threads, int repetitions) throws Exception {
        int[][] picture1 = new int[0][];
        TimeCounter timeCounter = new TimeCounter();
        Filter filter = new Filter(picture, Kernels.WYOSTRZAJACY_1);

        timeCounter.start();

        for (int i = 0; i < repetitions; i++) {
            picture1 = filter.modifyPictureUsingThreads(threads);
        }

        timeCounter.stop();
        System.out.println(timeCounter.getTimeMilis() / repetitions);

//        Picture.savePicture(picture1, "src/main/resources/p4_2.png");
        times.put(threads, timeCounter.getTimeMilis());
    }
}
