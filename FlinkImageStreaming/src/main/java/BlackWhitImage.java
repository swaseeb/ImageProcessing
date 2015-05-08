import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

/**
 * Created by shakirullah on 5/7/15.
 */
public class BlackWhitImage {
    public static void main(String args[]){
        int imgHeight=300;
        int imgWidth=300;
        BufferedImage bfImg=new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster wr=bfImg.getRaster();
        int[] whiteRegion=new int[10*10];
        Arrays.fill(whiteRegion,0);
    }
}
