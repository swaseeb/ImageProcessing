import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.configuration.Configuration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by shakirullah on 5/31/15.
 */
public class ImageReducer extends RichReduceFunction<ChunkImageStream>{

    @Override
    public ChunkImageStream reduce(ChunkImageStream imageData1, ChunkImageStream imageData2) throws Exception {

        System.out.println("Combining the result of convolution");

        BufferedImage im1=imageData1.getImageData();
        BufferedImage im2=imageData2.getImageData();

        ChunkImageStream result=null;
        BufferedImage imgsResult = new BufferedImage(imageData1.orgImageWidth,imageData1.orgImageHeight, im1.getType());

        //draw the small image
        Graphics2D gr = imgsResult.createGraphics();
        //imageData1 is always the intermediate result of the previous combination
        gr.drawImage(im1, imageData1.startX(), imageData1.startY(), imageData1.startX()+im1.getWidth(), imageData1.startY()+im1.getHeight(), 0, 0, im1.getWidth(),im1.getHeight(), null);
        gr.drawImage(im2, imageData2.startX(), imageData2.startY(), imageData2.startX()+im2.getWidth(), imageData2.startY()+im2.getHeight(), 0, 0, im2.getWidth(), im2.getHeight(), null);
        result= new ChunkImageStream(imageData1.getIMAGEID(),imageData1.orgImageWidth,imageData1.orgImageHeight,0,0,imgsResult);
        gr.dispose();

        return result;
    }

}
