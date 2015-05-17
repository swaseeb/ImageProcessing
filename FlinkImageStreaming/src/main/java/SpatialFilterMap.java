import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.util.Collector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;

/**
 * Created by shakirullah on 5/16/15.
 */
public class SpatialFilterMap implements MapFunction<BufferedImage,BufferedImage> {

    public BufferedImage map(BufferedImage image) throws Exception {
        // ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        //Iterator<?> readers = ImageIO.getImageReadersByFormatName("Cate3.jpg");

        //ImageIO is a class containing static methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding.
        //ImageReader reader = (ImageReader) readers.next();
        //Object source = bis;
        //ImageInputStream iis = ImageIO.createImageInputStream(source);
        //reader.setInput(iis, true);
        //ImageReadParam param = reader.getDefaultReadParam();

        //Image image = reader.read(0, param);
        //got an image file

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //bufferedImage is the RenderedImage to be written

        //Graphics2D g2 = bufferedImage.createGraphics();
        //g2.drawImage(image, null, null);
        BufferedImage imgOriginal=null;
        BufferedImage imgFiltered=null;
        int[][] spatialFilter={{-1,-1,-1},{-1,4,-1},{-1,-1,-1}};
        imgOriginal = image;
        int h = imgOriginal.getHeight();
        int w = imgOriginal.getWidth();
        int fm = spatialFilter.length - 1;
        int fn = spatialFilter[0].length - 1;
        imgFiltered = new BufferedImage(h, w, BufferedImage.TYPE_BYTE_GRAY);
        Raster imgOrginalRaster = imgOriginal.getRaster();
        WritableRaster imgFilteredRaster = imgFiltered.getRaster();
        //Spatial mask start from here
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int filterVlaue = 0;
                for (int m = 0; m < fm; m++) {
                    for (int n = 0; n < fn; n++) {
                        int ms = spatialFilter[m][n];
                        int idx = i - m;
                        int idy = j - n;
                        int im = 1;
                        if (idx >= 0 && idy >= 0)
                            im = imgOrginalRaster.getSample(i, j, 0);

                        filterVlaue += ms * im;
                    }
                }
                if (filterVlaue > 255) filterVlaue = 255;
                if (filterVlaue < 0) filterVlaue = 0;
                imgFilteredRaster.setSample(i, j, 0, filterVlaue);
            }

        }
        return imgFiltered;

    }
}
