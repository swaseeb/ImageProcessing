/**
 * Created by shakirullah on 6/10/15.
 */

import org.apache.flink.api.common.functions.RichMapFunction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is taken from following link:
*http://rosettacode.org/wiki/Hough_transform#Java : May 15, 20015
*/
public class HoughTransformMap extends RichMapFunction<ChunkImageStream, ChunkImageStream>{
    int minContrast;
    int thetaAxisSize;
    int rAxisSize;

    public HoughTransformMap(int thetaAxisSize,int rAxisSize, int minContrast){
        this.minContrast=minContrast;
        this.thetaAxisSize=thetaAxisSize;
        this.rAxisSize=rAxisSize;
    }
    @Override
    public ChunkImageStream map(ChunkImageStream imageData) throws Exception {
        System.out.println("Applying Hough Transformation on small images ...");
        BufferedImage imgData=imageData.getImageData();
        ArrayData inputData=getArrayDataFromImage(imgData);
        ArrayData outPutData = houghTransform(inputData, thetaAxisSize, rAxisSize, minContrast);
        BufferedImage img=getImageFromArrayData(outPutData);
        imageData.setImageData(img);
        System.out.println("Applying convolution on small chunks ...");
        return imageData;
    }
    public static ArrayData houghTransform(ArrayData inputData, int thetaAxisSize, int rAxisSize, int minContrast)
    {
        int width = inputData.width;
        int height = inputData.height;
        int maxRadius = (int)Math.ceil(Math.hypot(width, height));
        int halfRAxisSize = rAxisSize >>> 1;
        ArrayData outputData = new ArrayData(thetaAxisSize, rAxisSize);
        // x output ranges from 0 to pi
        // y output ranges from -maxRadius to maxRadius
        double[] sinTable = new double[thetaAxisSize];
        double[] cosTable = new double[thetaAxisSize];
        for (int theta = thetaAxisSize - 1; theta >= 0; theta--)
        {
            double thetaRadians = theta * Math.PI / thetaAxisSize;
            sinTable[theta] = Math.sin(thetaRadians);
            cosTable[theta] = Math.cos(thetaRadians);
        }

        for (int y = height - 1; y >= 0; y--)
        {
            for (int x = width - 1; x >= 0; x--)
            {
                if (inputData.contrast(x, y, minContrast))
                {
                    for (int theta = thetaAxisSize - 1; theta >= 0; theta--)
                    {
                        double r = cosTable[theta] * x + sinTable[theta] * y;
                        int rScaled = (int)Math.round(r * halfRAxisSize / maxRadius) + halfRAxisSize;
                        outputData.accumulate(theta, rScaled, 1);
                    }
                }
            }
        }
        return outputData;
    }
    public static ArrayData getArrayDataFromImage(BufferedImage img) throws IOException
    {
        BufferedImage inputImage = img;
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[] rgbData = inputImage.getRGB(0, 0, width, height, null, 0, width);
        ArrayData arrayData = new ArrayData(width, height);
        // Flip y axis when reading image
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int rgbValue = rgbData[y * width + x];
                rgbValue = (int)(((rgbValue & 0xFF0000) >>> 16) * 0.30 + ((rgbValue & 0xFF00) >>> 8) * 0.59 + (rgbValue & 0xFF) * 0.11);
                arrayData.set(x, height - 1 - y, rgbValue);
            }
        }
        return arrayData;
    }
    public static BufferedImage getImageFromArrayData(ArrayData arrayData) throws IOException
    {
        int max = arrayData.getMax();
        BufferedImage outputImage = new BufferedImage(arrayData.width, arrayData.height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < arrayData.height; y++)
        {
            for (int x = 0; x < arrayData.width; x++)
            {
                int n = Math.min((int)Math.round(arrayData.get(x, y) * 255.0 / max), 255);
                outputImage.setRGB(x, arrayData.height - 1 - y, (n << 16) | (n << 8) | 0x90 | -0x01000000);
            }
        }
        return outputImage;
    }


}
