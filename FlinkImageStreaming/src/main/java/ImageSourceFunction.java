import org.apache.flink.streaming.api.function.source.SourceFunction;
import org.apache.flink.util.Collector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shakirullah on 5/15/15.
 */
public class ImageSourceFunction implements SourceFunction<byte []>{

    private final  String path;

    public ImageSourceFunction(String path) {
        this.path = path;
    }

    public void invoke(Collector<byte[]> collector) throws Exception {
        FileInputStream fis = new FileInputStream(new File(path));
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
            while (fis.read(buf)!= -1) {
                //Writes to this byte array output stream
                collector.collect(buf);
            }

    }
}
