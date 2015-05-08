import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 * Created by shakirullah on 5/8/15.
 */
public class ImageConverter {
    protected static SampleModel sampleModel;
    public static void applySpatialMask(File file){
        try
        {
            int[][] matrix={{0,128},{192,64}};
            int [][] D2={{0,128,32,160},{192,64,224,96},{48,176,16,144},{240,112,208,80}};
            BufferedImage img= ImageIO.read(file);
            Raster raster=img.getData();
            int n=D2.length;
            WritableRaster wr=img.getRaster();
            int val=0;
            int wid=wr.getWidth(),high=wr.getHeight();
            for (int x=0;x<wid;x++) {
                for (int y = 0; y < high; y++) {
                    if(wr.getSample(x,y,0)>D2[y%n][x%n])
                        wr.setSample(x,y,0,1);
                //val=wr.getSample(x,y,0)/2;
                  //  wr.setSample(x,y,0,val);
                }
            }
           //BufferedImage image=new BufferedImage(wid,high,BufferedImage.TYPE_BYTE_GRAY);
            //image.setData(wr);
            ImageIO.write(img, "png", new File("Cate.png"));
           /* sampleModel = raster.getSampleModel();
            int w=raster.getWidth(),h=raster.getHeight();
            for (int x=0;x<w;x++) {
                for (int y = 0; y < h; y++) {

                }
            }
            WritableRaster rasterFiltered= Raster.createWritableRaster(sampleModel, new Point(0, 0));
            System.out.println("Size of the Image: "+w+" x "+h);
            int pixels[][]=new int[w][h];
            for (int x=0;x<w;x++)
            {
                for(int y=0;y<h;y++)
                {
                    pixels[x][y]= raster.getSample(x,y,0);
                    raster.getPixel(x,y,pixels);

                    rasterFiltered.setSample(x, y, 0, raster.getSample(x,y,0));
                    // System.out.println("Pixels["+x+"]["+y+"] : "+pixels[x][y]);
                }
            }*/

           // BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
            //image.setData(rasterFiltered);
            //ImageIO.write(image, "png", new File("Cate.png"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static int[][] compute(File file)
    {
        try
        {
            BufferedImage img= ImageIO.read(file);
            Raster raster=img.getData();
            sampleModel = raster.getSampleModel();
            int w=raster.getWidth(),h=raster.getHeight();
            System.out.println("Size of the Image: "+w+" x "+h);
            int pixels[][]=new int[w][h];
            for (int x=0;x<w;x++)
            {
                for(int y=0;y<h;y++)
                {
                    pixels[x][y]= raster.getSample(x,y,0);
                   // System.out.println("Pixels["+x+"]["+y+"] : "+pixels[x][y]);
                }
            }

            return pixels;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static BufferedImage getImage(int pixels[][])
    {
        int w=pixels.length;
        int h=pixels[0].length;
        System.out.println("Size of the Int Array: "+w+" x "+h);
        //BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster= Raster.createWritableRaster(sampleModel, new Point(0,0));
        for(int i=0;i<w;i++)
        {
            for(int j=0;j<h;j++)
            {
                raster.setSample(i, j, 0, pixels[i][j]);
               // System.out.println("Pixels[" + i + "][" + j + "] : " + pixels[i][j]);
            }
        }

        BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
        image.setData(raster);
        return image;
    }
}
