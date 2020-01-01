package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pixel {
    private int alpha;
    private int red;
    private int green;
    private int blue;
    private int pixelValue;

    public Pixel(Pixel p) {
        this.alpha = p.alpha;
        this.red = p.red;
        this.green = p.green;
        this.blue = p.blue;
        this.pixelValue = p.pixelValue;
    }

    public Pixel(int pixelValue) {
        alpha = (pixelValue>>24) & 0xff;
        red = (pixelValue>>16) & 0xff;
        green = (pixelValue>>8) & 0xff;
        blue = pixelValue & 0xff;
        this.pixelValue = pixelValue;
    }

    public int getPixelValue() {
        int p = (alpha<<24) | (red<<16) | (green<<8) | blue;
        return p;
    }
}
