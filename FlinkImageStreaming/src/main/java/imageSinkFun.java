import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by shakirullah on 5/16/15.
 */
public class imageSinkFun implements org.apache.flink.streaming.api.function.sink.SinkFunction<java.awt.image.BufferedImage> {


    public void invoke(BufferedImage value) {
        try {
            ImageIO.write(value, "png", new File("Cate3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
