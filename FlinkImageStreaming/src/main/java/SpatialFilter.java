import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shakirullah on 5/7/15.
 */
public class SpatialFilter {

    public static void main(String arg[]){
         BufferedImage imgOriginal=null;
         BufferedImage imgFiltered=null;
         int[][] spatialFilter={{1,-1,1},{-1,9,-1},{-1,1,-1}};
        try {
            //Check this with Cat.jpg and img2.jpeg and see the Cat.png for the result
            imgOriginal=ImageIO.read(new File("img2.jpeg"));
            int h=imgOriginal.getHeight();
            int w=imgOriginal.getWidth();
            int fm=spatialFilter.length-1;
            int fn=spatialFilter[0].length-1;
            imgFiltered=new BufferedImage(h,w,BufferedImage.TYPE_BYTE_BINARY);
            Raster imgOrginalRaster=imgOriginal.getRaster();
            WritableRaster imgFilteredRaster=imgFiltered.getRaster();
            //Spatial mask start from here
            for(int i=0; i<h;i++){
                for(int j=0; j<w; j++){
                    int filterVlaue=0;
                    for(int m=0; m < fm;m++)
                    {
                        for(int n=0;n < fn; n++){
                            int  ms=spatialFilter[m][n];
                            int idx=i-m;
                            int idy=j-n;
                            int im=1;
                            if(idx >= 0 && idy >= 0)
                                im = imgOrginalRaster.getSample(i,j,0);

                            filterVlaue+=ms*im;
                        }
                    }
                    if(filterVlaue>255) filterVlaue=255;
                    if(filterVlaue<0)filterVlaue=0;
                    imgFilteredRaster.setSample(i,j,0,filterVlaue);
                }


            }
            ImageIO.write(imgFiltered, "png", new File("Cate.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
