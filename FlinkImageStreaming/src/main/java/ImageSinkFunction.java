import org.apache.flink.streaming.api.function.sink.SinkFunction;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

/**
 * Created by shakirullah on 6/1/15.
 */
public class ImageSinkFunction implements SinkFunction<ImageStream>{

    private String sinkPath;
    public ImageSinkFunction(String imgOutputDirectory){
        File f=new File(imgOutputDirectory);
        if(f.exists() && f.isDirectory()) {
            sinkPath=imgOutputDirectory;
        }
        else {
            System.out.println("the path doesn't exists or is not a directory");
            System.exit(0);
        }
    }
    public void invoke(ImageStream value) {
        String imageName = FilenameUtils.removeExtension(value.imgName);
        System.out.println("Saving image :"+ imageName);
        try {
            ImageIO.write(value.img,"PNG",new File(sinkPath +"/" + imageName+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
