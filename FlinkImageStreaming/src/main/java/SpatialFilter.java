import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by shakirullah on 5/7/15.
 */
public class SpatialFilter {

    public static void main(String arg[]){
         BufferedImage imgOriginal=null;
         BufferedImage imgFiltered=null;
         int[][] spatialFilter={{-1,-1,-1},{-1,9,-1},{-1,-1,-1}};
        try {

            imgOriginal= ImageIO.read(new File("Cat.jpg"));
            int[][] imgOriginalArray= ImageToIntArray.ImageToIntArray(imgOriginal);
            int[][] imgFilterArray= imgOriginalArray.clone();
            for(int i=0; i<imgOriginalArray.length;i++){
                for(int j=0; j<imgOriginalArray[0].length; j++){
                    int filterVlaue=0;
                    for(int m=0; m < spatialFilter.length;m++)
                    {
                        for(int n=0;n<spatialFilter[m].length; n++){
                            int  ms=spatialFilter[m][n];
                            int idx=i-m;
                            int idy=j-n;
                            int im=1;
                            if(idx>=0 && idy>=0)
                                im = imgOriginalArray[idx][idy];
                            filterVlaue+=ms*im;
                        }
                    }
                    if(filterVlaue>255) filterVlaue=255;
                    if(filterVlaue<0)filterVlaue=0;
                    imgFilterArray[i][j]=filterVlaue;
                }


            }
            imgFiltered=ImageIntArrayToBufferedImage.intArrayToBufferImage(imgFilterArray);
            // Store the image using the PNG format.
            ImageIO.write(imgFiltered, "PNG", new File("checkboard.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
