
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.record.io.FileInputFormat;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.function.source.SourceFunction;
import org.apache.flink.util.Collector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by shakirullah on 5/15/15.
 */
public class FlinkSpatialFilter {
    public static void main(String arg[]) throws Exception {
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();

        ExecutionEnvironment env1=ExecutionEnvironment.getExecutionEnvironment();
        DataStream<BufferedImage> dataStream= env.addSource(new ImageSourceFunction("img2.jpeg"));
        DataStream<BufferedImage> img= dataStream.map(new SpatialFilterMap()).addSink(new imageSinkFun());
        //env.saveImageFile(img);
        //str.print();
        //img.print();
        env.execute();
    }
}


