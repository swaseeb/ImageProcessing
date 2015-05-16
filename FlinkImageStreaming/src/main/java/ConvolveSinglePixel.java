import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 * Created by shakirullah on 5/15/15.
 */
public class ConvolveSinglePixel {
    public static void main(String arg[]) {
        BufferedImage imgOriginal=null;
        BufferedImage imgFiltered=null;
        double[][] spatialFilter={{-1,-1,1},{-1,8,-1},{-1,-1,-1}};
        int km=spatialFilter[0].length;
        int m2 = Math.abs( km/ 2);
        int n2 = Math.abs(spatialFilter.length / 2);
        System.out.println(m2+" "+n2);
        try {
            imgOriginal=ImageIO.read(new File("img2.jpeg"));
            int h=imgOriginal.getHeight();
            int w=imgOriginal.getWidth();
            imgFiltered=new BufferedImage(h,w,BufferedImage.TYPE_BYTE_BINARY);
            Raster imgOrginalRaster = imgOriginal.getRaster();
            WritableRaster imgFilteredRaster = imgFiltered.getRaster();
            ConvolutionMatrix cm=new ConvolutionMatrix(3);
            cm.setAll(5);
            cm.applyConfig(spatialFilter);
            imgFiltered= cm.computeConvolution3x3(imgOriginal,cm);
            //Spatial mask start from here
            /*for(int x=0; x<h;x++){
                for(int y=0; y<w; y++){
                    int filterVlaue=0;
                    double sum = 0;
                    for (int k = -n2; k <= n2; k++) {
                        for (int j = -m2; j <= m2; j++) {
                            sum += spatialFilter[j+m2][k+n2] * imgOrginalRaster.getSample(x-j,y-k,0);
                        }
                    }
                    System.out.println(sum);
                    imgFilteredRaster.setSample(x,y,0,sum);
                    //if(filterVlaue>255) filterVlaue=255;
                    //if(filterVlaue<0)filterVlaue=0;
                }

            }*/
            ImageIO.write(imgFiltered, "png", new File("Cate.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
