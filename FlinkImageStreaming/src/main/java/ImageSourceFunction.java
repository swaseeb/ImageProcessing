import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.function.source.SourceFunction;
import org.apache.flink.util.Collector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shakirullah on 5/15/15.
 */
public  class ImageSourceFunction implements SourceFunction<ImageStream>{

    //images directory or file
    static File file;

    // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
            "gif", "png", "bmp","jpg","jpeg" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    public ImageSourceFunction(String path) {
        this.file= new File(path);
    }

    public void invoke(Collector<ImageStream> collector) throws Exception {


        if (file.isDirectory()) { // make sure it's a directory
            for (final File f : file.listFiles(IMAGE_FILTER)) {
                System.out.println("Reading image: " + f.getName());
                BufferedImage img=ImageIO.read(f);
                collector.collect(new ImageStream(f.getName(),img));
            }
        }
        else {
            System.out.println("Reading image "+file.getName());
            BufferedImage img=ImageIO.read(file);
            collector.collect(new ImageStream(file.getName(),img));
        }
    }

}
