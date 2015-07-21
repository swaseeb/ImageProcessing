import org.apache.flink.api.common.functions.RichMapFunction;

import java.awt.image.BufferedImage;

/**
 * Created by shakirullah on 5/31/15.
 */
public class ImageDataToBufferedImage extends RichMapFunction<ChunkImageStream,BufferedImage>{
    @Override
    public BufferedImage map(ChunkImageStream imageData) throws Exception {
        System.out.println("Mapping back to buffered image ...");

        BufferedImage outputImage = imageData.getImageData();
        return  outputImage;
    }

}
