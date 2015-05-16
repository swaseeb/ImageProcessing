import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
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
         double[][] spatialFilter={{-1,-1,1},{-1,8,-1},{-1,-1,-1}};
        try {
            imgOriginal=ImageIO.read(new File("img2.jpeg"));

            //Set convlution matrix
            ConvolutionMatrix cm=new ConvolutionMatrix(3);
            cm.applyConfig(spatialFilter);
            imgFiltered=ConvolutionMatrix.computeConvolution3x3(imgOriginal,cm);

           /*  int h=imgOriginal.getHeight();
            int w=imgOriginal.getWidth();
            int fm=spatialFilter.length-1;
            int fn=spatialFilter[0].length-1;
            imgFiltered=new BufferedImage(h,w,BufferedImage.TYPE_INT_ARGB);
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
                    imgFilteredRaster.setSample(i, j, 0, filterVlaue);
                }

            }*/
            ImageIO.write(imgFiltered, "png", new File("Cate3.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
