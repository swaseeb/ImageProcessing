import org.apache.flink.api.common.accumulators.Accumulator;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataOutputView;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by shakirullah on 7/10/15.
 */
public class HoughTransformAccumulator implements Accumulator{

    public final int[] dataArray;
    public final int width;
    public final int height;
    public HoughTransformAccumulator(int width, int height)
    {
        this(new int[width * height], width, height);
    }
    public HoughTransformAccumulator(int[] dataArray, int width, int height)
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
    public void add(Object o) {
        Tuple3<Integer,Integer,Integer> values=(Tuple3)o;
        this.accumulate(values.f0,values.f1,values.f2);
    }

    public Object getLocalValue() {
        return null;
    }

    public void resetLocal() {

    }

    public void merge(Accumulator accumulator) {

    }

    public void write(DataOutputView dataOutputView) throws IOException {

    }

    public void read(DataInputView dataInputView) throws IOException {

    }
}
