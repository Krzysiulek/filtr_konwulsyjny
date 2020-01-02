package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int pixelValue;

    public Pixel(int pixelValue) {
        Color c = new Color(pixelValue);
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();

        this.pixelValue = pixelValue;
    }
}
