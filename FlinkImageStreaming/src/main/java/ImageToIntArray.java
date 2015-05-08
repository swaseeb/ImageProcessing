import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by shakirullah on 5/7/15.
 */
public class ImageToIntArray {
    /**
     * This returns a true bitmap where each element in the grid is either a 0
     * or a 1. A 1 means the pixel is white and a 0 means the pixel is black.
     *
     * If the incoming image doesn't have any pixels in it then this method
     * returns null;
     *
     * @param image
     * @return
     */
    public static int[][] ImageToIntArray(BufferedImage image)
    {

        if (image == null || image.getWidth() == 0 || image.getHeight() == 0)
            return null;

        // This returns bytes of data starting from the top left of the bitmap
        // image and goes down.
        // Top to bottom. Left to right.
        final byte[] pixels = ((DataBufferByte) image.getRaster()
                .getDataBuffer()).getData();

        final int width = image.getWidth();
        final int height = image.getHeight();

        int[][] result = new int[height][width];

        boolean done = false;
        boolean alreadyWentToNextByte = false;
        int byteIndex = 0;
        int row = 0;
        int col = 0;
        int numBits = 0;
        byte currentByte = pixels[byteIndex];
        while (!done)
        {
            alreadyWentToNextByte = false;

            result[row][col] = (currentByte & 0x80) >> 7;
            currentByte = (byte) (((int) currentByte) << 1);
            numBits++;

            if ((row == height - 1) && (col == width - 1))
            {
                done = true;
            }
            else
            {
                col++;

                if (numBits == 8)
                {
                    currentByte = pixels[++byteIndex];
                    numBits = 0;
                    alreadyWentToNextByte = true;
                }

                if (col == width)
                {
                    row++;
                    col = 0;

                    if (!alreadyWentToNextByte)
                    {
                        currentByte = pixels[++byteIndex];
                        numBits = 0;
                    }
                }
            }
        }

        return result;
    }
}
