import Constants.Kernels;
import Models.Params;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Filter {
    private Picture picture;
    private Kernels kernel;
    private static boolean printed = false;
    @Getter
    private int maxThreadsAmount;
    int[][] tmpPicture;

    List<Params> paramsList = new ArrayList<>();

//    private int ctr;

    public Filter(Picture picture, Kernels kernel) {
        this.picture = picture;
        this.kernel = kernel;
    }

    public int[][] modifyPictureUsingThreads(int maxThreadsAmount) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        this.maxThreadsAmount = maxThreadsAmount;

        if (!printed) {
            System.out.println("Running " + maxThreadsAmount + " threads");
            printed = true;
        }

        int pieces = maxThreadsAmount * 4;

        int incrementWidthBy = picture.getWidth() / pieces;
        int incrementHeightBy = picture.getHeight() / pieces;

        for (int i = 0; i < picture.getWidth(); i+= incrementWidthBy) {
            for (int j = 0; j < picture.getHeight(); j += incrementHeightBy) {
                int startWidth = i;
                int endWidth = i + incrementWidthBy;
                int startHeight = j;
                int endHeight = j + incrementHeightBy;

                paramsList.add(new Params(startHeight, endHeight, startWidth, endWidth));

            }
        }
        tmpPicture = new int[picture.getHeight()][picture.getWidth()];

        for (int i = 0; i < maxThreadsAmount; i++) {
            Thread t = new Thread(() -> {
                while (!paramsList.isEmpty()) {
                    Params params;
                    try {
                        params = paramsList.get(0);
                        paramsList.remove(0);

                        for (int hei = params.getHeightStart(); hei < params.getHeightEnd() - 1; hei++) {
                            for (int wid = params.getWidthStart(); wid < params.getWidthEnd() - 1; wid++) {
                                if (hei > picture.getHeight() - 1 || wid > picture.getWidth() - 1) {
                                    continue;
                                }

                                Color c = useKernelOnPixel(hei, wid);
                                tmpPicture[hei][wid] = c.getRed();
                            }
                        }
                    }
                    catch (Exception e) {
//                        break;
                    }
                }

            });

            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

        return tmpPicture;
    }

    public Picture modifyPicture() {
        Picture newPicture = new Picture(picture);
        newPicture.setAllPixelsToZero();

        for (int widthN = 0; widthN < picture.getWidth(); widthN++) {
            for (int heightM = 0; heightM < picture.getHeight(); heightM++) {
                Color pixel = useKernelOnPixel(heightM, widthN);
                try {
                    newPicture.setPixel(heightM, widthN, pixel);
                }
                catch (Exception e){
                    System.out.println(pixel);
                }
            }
        }

        return newPicture;
    }

    private Color useKernelOnPixel(int heightM, int widthN) {
        int sum = 0;
        int ctr = 0;

        for (int kernelWidth = -1; kernelWidth < kernel.getFilter().length - 1; kernelWidth++) {
            for (int kernelHight = -1; kernelHight < kernel.getFilter()[kernelWidth + 1].length - 1; kernelHight++) {
                int kernelValue = kernel.getFilter()[kernelWidth + 1][kernelHight + 1];

                if (kernelValue == 1) {
//                    Color pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    int pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sum += pixel;
                    ctr += kernelValue;
                }
                else if (kernelValue != 0) {
                    int pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
//                    Color pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sum += pixel * kernelValue;
                    ctr += kernelValue;
                }
            }
        }

        sum = Math.abs(sum % 255);

        if (ctr != 0) {
            int val = sum / ctr;
            return new Color(val, val, val);
        }
        else {
            return new Color(sum, sum, sum);
        }
    }
}
