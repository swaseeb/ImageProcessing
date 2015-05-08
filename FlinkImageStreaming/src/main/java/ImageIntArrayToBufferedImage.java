import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 * Created by shakirullah on 5/7/15.
 */
public class ImageIntArrayToBufferedImage {
    public static BufferedImage intArrayToBufferImage(int[][] imgIntArray){
        int imgLength = imgIntArray.length;
        // Let's create a BufferedImage for a binary image.
        BufferedImage im = new BufferedImage(imgIntArray[imgLength-1].length,imgLength,BufferedImage.TYPE_BYTE_BINARY);
        // We need its raster to set the pixels' values.
        WritableRaster raster = im.getRaster();
        // Put the pixels on the raster. Note that only values 0 and 1 are used for the pixels.
        // You could even use other values: in this type of image, even values are black and odd
        // values are white.
        for(int i=0;i<imgLength;i++)
            for(int j=0;j<imgIntArray[i].length;j++)
                raster.setSample(i,j,0,imgIntArray[i][j]); // checkerboard pattern.

        return  im;
    }
}
