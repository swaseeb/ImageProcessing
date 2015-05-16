import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by shakirullah on 5/16/15.
 */


public class ConvolutionMatrix
{
    public static final int SIZE = 3;

    public double[][] Matrix;
    public double Factor = 1;
    public double Offset = 1;

    public ConvolutionMatrix(int size) {
        Matrix = new double[size][size];
    }

    public void setAll(double value) {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = value;
            }
        }
    }

    public void applyConfig(double[][] config) {
        for(int x = 0; x < SIZE; ++x) {
            for(int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = config[x][y];
            }
        }
    }

    public static BufferedImage computeConvolution3x3(BufferedImage src, ConvolutionMatrix matrix) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage result = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);

        int a, r, g, b;
        int sumR, sumG, sumB;
        int[][] pixels = new int[SIZE][SIZE];

        for(int y = 0; y < height - 2; ++y)
            for (int x = 0; x < width - 2; ++x) {

                // get pixel matrix
                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        pixels[i][j] = src.getRGB(x + i, y + j);
                    }
                }

                // get alpha of center pixel
                Color c = new Color(pixels[1][1]);
                a = c.getAlpha();
                // init color sum
                sumR = sumG = sumB = 0;

                // get sum of RGB on matrix
                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        sumR += (c.getRed() * matrix.Matrix[i][j]);
                        sumG += (c.getGreen() * matrix.Matrix[i][j]);
                        sumB += (c.getBlue() * matrix.Matrix[i][j]);
                    }
                }

                // get final Red
                r = (int) (sumR / matrix.Factor + matrix.Offset);
                if (r < 0) {
                    r = 0;
                } else if (r > 255) {
                    r = 255;
                }

                // get final Green
                g = (int) (sumG / matrix.Factor + matrix.Offset);
                if (g < 0) {
                    g = 0;
                } else if (g > 255) {
                    g = 255;
                }

                // get final Blue
                b = (int) (sumB / matrix.Factor + matrix.Offset);
                if (b < 0) {
                    b = 0;
                } else if (b > 255) {
                    b = 255;
                }

                // apply new pixel
                Color clr=new Color(r, g, b, a);
                result.setRGB(x + 1, y + 1,clr.getRGB() );
            }

        // final image
        return result;
    }
}
