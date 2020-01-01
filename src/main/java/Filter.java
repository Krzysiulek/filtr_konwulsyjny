import Constants.Kernels;
import Models.Pixel;
import lombok.AllArgsConstructor;

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
            int height = (picture.getImageArray().length / maxThreadsAmount);
            int width = (picture.getImageArray()[0].length / maxThreadsAmount);

            int finalThr = thr;
            Thread thread = new Thread(() -> {
                for (int widthN = finalThr * width; (widthN < (finalThr + 1) * width); widthN++) {
                    for (int heightM = 0; heightM < picture.getHeight(); heightM++) {
                        Pixel pixel = useKernelOnPixel(heightM, widthN);
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
                Pixel pixel = useKernelOnPixel(heightM, widthN);
                newPicture.setPixel(heightM, widthN, pixel);
            }
        }

        return newPicture;
    }

    private Pixel useKernelOnPixel(int heightM, int widthN) {
        int ctr = 0;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;

        for (int kernelWidth = -1; kernelWidth < kernel.getFilter().length - 1; kernelWidth++) {
            for (int kernelHight = -1; kernelHight < kernel.getFilter()[kernelWidth + 1].length - 1; kernelHight++) {
                int kernelValue = kernel.getFilter()[kernelWidth + 1][kernelHight + 1];

                if (kernelValue == 1) {
                    Pixel pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sumRed += pixel.getRed();
                    sumBlue += pixel.getBlue();
                    sumGreen += pixel.getGreen();

                    ctr += kernelValue;
                }
                else if (kernelValue != 0) {
                    Pixel pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sumRed += pixel.getRed();
                    sumBlue += pixel.getBlue();
                    sumGreen += pixel.getGreen();

                    ctr += kernelValue;
                }
            }
        }

        if (ctr != 0)
            return new Pixel(255, sumRed / ctr, sumGreen / ctr, sumBlue / ctr, 0);
        else
            return new Pixel(255, sumRed, sumGreen, sumBlue, 0);
    }
}
