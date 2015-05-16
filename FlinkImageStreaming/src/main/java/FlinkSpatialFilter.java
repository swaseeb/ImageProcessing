
import org.apache.flink.streaming.api.datastream.DataStream;

import java.awt.image.BufferedImage;

/**
 * Created by shakirullah on 5/15/15.
 */
public class FlinkSpatialFilter {
    public static void main(String arg[]) throws Exception {
        ImageStreamExecutionEnvironment env=ImageStreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<byte[]> dataStream=env.readImageFile("Cat.jpg");
       DataStream<BufferedImage> img= dataStream.map(new SpatialFilterMap()).addSink(new imageSinkFun());
        //env.saveImageFile(img);
        DataStream<String> str=env.fromElements("werere lasjdflkj");
        str.print();
        //img.print();
        env.execute();
    }
}

