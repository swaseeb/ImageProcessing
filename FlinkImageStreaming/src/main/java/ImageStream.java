import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by shakirullah on 6/12/15.
 */
public class ImageStream implements Serializable{
    public String imgName;
    public BufferedImage img;
    public ImageStream(String imgName,BufferedImage img){
        this.imgName=imgName;
        this.img=img;
    }
    public String getImageExtension(){
        int extIndx=imgName.lastIndexOf(".");
        return imgName.substring(extIndx + 1);
    }
}
