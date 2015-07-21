import org.apache.flink.api.common.functions.RichMapFunction;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by shakirullah on 6/11/15.
 */
public class ChunkImageStreamToImageStream extends RichMapFunction<ChunkImageStream,ImageStream> {
    @Override
    public ImageStream map(ChunkImageStream imageData) throws Exception {
        System.out.println("Mapping back ChunkImageStream to ImageStream ...");
        ImageStream is=new ImageStream(imageData.getIMAGEID(),imageData.getImageData());
        return  is;
    }
}
