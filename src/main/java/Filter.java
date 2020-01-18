import Constants.Kernels;

import java.util.HashSet;

public class Filter {
    private Picture picture;
    private int[][] kernelArray;
    Kernels kernel;


    public int maxThreadsAmount;
    int[][] tmpPicture;
    private HashSet<Thread> threads = new HashSet<>();

    public Filter(Picture picture, Kernels kernel) {
        this.picture = picture;
        this.kernel = kernel;
        this.kernelArray = kernel.getFilter();
        tmpPicture = new int[picture.height][picture.width];
    }

    public int[][] modifyPictureUsingThreads(int maxThreadsAmount) throws InterruptedException {
        this.maxThreadsAmount = maxThreadsAmount;

        int incrementWidthBy = picture.width / maxThreadsAmount;
        int incrementHeightBy = picture.height / maxThreadsAmount;

        /**
         * TODO:
         *      - rodzielic
         */
        for (int i = 0; i < picture.width; i+= incrementWidthBy) {
            for (int j = 0; j < picture.height; j += incrementHeightBy) {
                int endWidth = i + incrementWidthBy;
                int endHeight = j + incrementHeightBy;
                int finalJ = j;
                int finalI = i;


                Thread t = new Thread(() -> {
                    for (int hei = finalJ; hei < endHeight; hei++) {
                        for (int wid = finalI; wid < endWidth; wid++) {
                            if (hei > picture.height - 1 || wid > picture.width - 1) {
                                continue;
                            }

                            tmpPicture[hei][wid] = useKernelOnPixel(hei, wid);
                        }
                    }
                });

                t.start();
                threads.add(t);
            }
        }

        for (Thread t : threads) {
            t.join();
        }

        return tmpPicture;
    }

    private int useKernelOnPixel(int heightM, int widthN) {
        int sum = 0;
        int ctr = 0;

        for (int kernelWidth = -1; kernelWidth < kernelArray.length - 1; kernelWidth++) {
            for (int kernelHight = -1; kernelHight < kernelArray[kernelWidth + 1].length - 1; kernelHight++) {
                int kernelValue = kernelArray[kernelWidth + 1][kernelHight + 1];

                if (kernelValue == 1) {
                    int pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sum += pixel;
                    ctr += kernelValue;
                }
                else if (kernelValue != 0) {
                    int pixel = picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth);
                    sum += pixel * kernelValue;
                    ctr += kernelValue;
                }
            }
        }

        sum = Math.abs(sum % 255);

        if (ctr != 0) {
            return sum / ctr;
        }
        else {
            return sum;
        }
    }
}
