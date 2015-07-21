import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.awt.image.BufferedImage;

/**
 * Created by shakirullah on 6/10/15.
 */
public class FlinkHoughTransformer {
    public static void main(String[] args) throws Exception{
        Configuration config = new Configuration();
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();

        ArrayData ar=new ArrayData(320,240);
        //Read the image buffered stream from a source
        DataStream<ImageStream> imgStream= env.addSource(new ImageSourceFunction("Pentagon.png"));

        //
        DataStream<ImageStream> img=imgStream

                //Convert image into small images in the form of objects of class:ChunkImageStream
                // ChunkImageStream(String IMAGEID,int startX,int startY,BufferedImage img)
                .flatMap(new ImageFlatMap(30,30))

                        //Apply the convolution on small images
                .map(new HoughTransformMap(30, 30, 100))

                        //Group by the original image ID
                .groupBy(new KeySelector<ChunkImageStream, String>() {

                    public String getKey(ChunkImageStream id) {
                        return id.getIMAGEID();
                    }
                })

                        //Recombine the resulted small images into one.
                .reduce(new ImageReducer())

                        //Transform the image data back to buffer stream.
                .map(new ChunkImageStreamToImageStream())

                        //Store the image
                .addSink(new ImageSinkFunction("img/save/"));
        env.execute();
    }
}
