import Constants.Kernels;
import Models.Pixel;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Filter {
    private Picture picture;
    private Kernels kernel;
    private static boolean printed = false;

    public Picture modifyPictureUsingThreads(int maxThreadsAmount) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        while (picture.getWidth() % maxThreadsAmount != 0) {
            maxThreadsAmount--;
        }

        if (!printed) {
            System.out.println("Running " + maxThreadsAmount + " threads");
            printed = true;
        }

        Picture newPicture = new Picture(picture);
        newPicture.setAllPixelsToZero();

        for (int thr = 0; thr < maxThreadsAmount; thr++) {
            int height = (picture.getHeight() / maxThreadsAmount);
            int width = (picture.getWidth() / maxThreadsAmount);

            int finalThr = thr;
            Thread thread = new Thread(() -> {
                for (int widthN = finalThr * width; (widthN < (finalThr + 1) * width); widthN++) {
                    for (int heightM = 0; heightM < picture.getHeight(); heightM++) {
                        Color pixel = useKernelOnPixel(heightM, widthN);
                        newPicture.setPixel(heightM, widthN, pixel);
                    }
                }
            });

            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return newPicture;
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
        int ctr = 0;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;

        for (int kernelWidth = -1; kernelWidth < kernel.getFilter().length - 1; kernelWidth++) {
            for (int kernelHight = -1; kernelHight < kernel.getFilter()[kernelWidth + 1].length - 1; kernelHight++) {
                int kernelValue = kernel.getFilter()[kernelWidth + 1][kernelHight + 1];

                if (kernelValue == 1) {
                    Color pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sumRed += pixel.getRed();
                    ctr += kernelValue;
                }
                else if (kernelValue != 0) {
                    Color pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sumRed += pixel.getRed();
                    ctr += kernelValue;
                }
            }
        }

            sumRed = sumRed % 255;
            sumBlue = sumRed;
            sumGreen = sumRed;

        if (ctr != 0)
            return new Color(sumRed / ctr, sumGreen / ctr, sumBlue / ctr);
        else {
            return new Color(sumRed, sumGreen, sumBlue);
        }

    }
}
