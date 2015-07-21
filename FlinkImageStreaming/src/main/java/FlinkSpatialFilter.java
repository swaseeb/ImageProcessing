

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by shakirullah on 5/15/15.
 */

public class FlinkSpatialFilter {

    public static void main(String arg[]) throws Exception {

        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        int factor = 4,chunkwidth = 160,chunkheight = 60;
        int[][] spatialFilter={{-1,-1,-1},{-1,9,-1},{-1,-1,-1}};

        //Reads buffered images from source as objects of class:ImageStream
        DataStream<ImageStream> imgStream= env.addSource(new ImageSourceFunction("img"));

        DataStream img=imgStream
                //Convert image into chunks, objects of class:ChunkImageStream
                .flatMap(new ImageFlatMap(chunkwidth,chunkheight))
                        //Apply the convolution on chunks
                .map(new ConvolutionMap(spatialFilter, factor))
                        //Group chunks by the original image ID
                .groupBy(new KeySelector<ChunkImageStream, String>() {
                    public String getKey(ChunkImageStream id) {
                        return id.getIMAGEID();
                   }
                })
                        //Recombine the resulted chunks into one.
                .reduce(new ImageReducer())
                        //Transform the ChunkImageStream to ImageStream.
                .map(new ChunkImageStreamToImageStream())
                        //Store the ImageStream back as an image
                .addSink(new ImageSinkFunction("img/save/"));

        env.execute();
    }
}


