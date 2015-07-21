import java.io.Serializable;

/**
 * Created by shakirullah on 5/31/15.
 */
//This class is part of the ImageConvolution taken from:
// http://rosettacode.org/wiki/Image_convolution#Java : May 15, 20015

public class ArrayData implements Serializable{
    public final int[] dataArray;
    public final int width;
    public final int height;
    public ArrayData(int width, int height)
    {
        this(new int[width * height], width, height);
    }
    public ArrayData(int[] dataArray, int width, int height)
    {
        this.dataArray = dataArray;
        this.width = width;
        this.height = height;
    }
    public int get(int x, int y)
    {  return dataArray[y * width + x];  }
    public void set(int x, int y, int value)
    {  dataArray[y * width + x] = value;  }

    public void accumulate(int x, int y, int delta)
    {  set(x, y, get(x, y) + delta);  }

    public boolean contrast(int x, int y, int minContrast)
    {
        int centerValue = get(x, y);
        for (int i = 8; i >= 0; i--)
        {
            if (i == 4)
                continue;
            int newx = x + (i % 3) - 1;
            int newy = y + (i / 3) - 1;
            if ((newx < 0) || (newx >= width) || (newy < 0) || (newy >= height))
                continue;
            if (Math.abs(get(newx, newy) - centerValue) >= minContrast)
                return true;
        }
        return false;
    }

    public int getMax()
    {
        int max = dataArray[0];
        for (int i = width * height - 1; i > 0; i--)
            if (dataArray[i] > max)
                max = dataArray[i];
        return max;
    }
}
