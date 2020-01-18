package Models;


import java.awt.*;


public class Pixel {
    public int red;
    public int green;
    public int blue;
    public int pixelValue;

    public Pixel(int pixelValue) {
        Color c = new Color(pixelValue);
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();

        this.pixelValue = pixelValue;
    }
}
