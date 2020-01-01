import Constants.Kernels;
import Models.Pixel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Filter {
    private Picture picture;
    private Kernels kernel;

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

                sumRed += picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth).getRed() * kernelValue;
                sumBlue += picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth).getBlue() * kernelValue;
                sumGreen += picture.getPixelWithoutException(heightM + kernelHight, widthN + kernelWidth).getGreen() * kernelValue;

                ctr += kernelValue;
            }
        }

        if (ctr != 0)
            return new Pixel(255, sumRed / ctr, sumGreen / ctr, sumBlue / ctr, 0);
        else
            return new Pixel(255, sumRed, sumGreen, sumBlue, 0);

    }
}
