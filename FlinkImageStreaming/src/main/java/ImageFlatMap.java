import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by shakirullah on 5/31/15.
 */

public class ImageFlatMap extends RichFlatMapFunction<ImageStream, ChunkImageStream>{

    // determines the chunk width and height
    private int chunkWidth;
    private int chunkHeight;

    public ImageFlatMap(int chunkWidth,int chunkHeight){
        this.chunkWidth=chunkWidth;
        this.chunkHeight=chunkHeight;
    }
    @Override
    public void flatMap(ImageStream imageFile, Collector<ChunkImageStream> collector) throws Exception {

        System.out.println("Creating small chunks ...");

        BufferedImage img= imageFile.img;

        String imgName=imageFile.imgName;
        String imgType =imageFile.getImageExtension();

        //the values for rows and cols variables should be decided
        int cols =Math.round(img.getWidth() /chunkWidth );

        int rows =Math.round(img.getHeight()/chunkHeight);
        //
        int unfitChunkWidth=img.getWidth()%chunkWidth;

        int unfitChunkHeight=img.getHeight()%chunkHeight;

        System.out.println("No of rows:"+rows+" No of columns:"+cols+" UnfitRow:"+unfitChunkWidth+" UnfitCol:"+unfitChunkHeight);

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                getSmallImage(img,imgName,imgType,x,y,chunkWidth,chunkHeight,collector);
            }if(unfitChunkWidth!=0){
                System.out.println(cols*chunkWidth);
                getUnfitSmallImage(img,imgName,imgType,x,cols,chunkWidth,chunkHeight,unfitChunkWidth,chunkHeight,collector);
            }
        }if(unfitChunkHeight!=0){
            for (int y = 0; y < cols; y++) {
                getUnfitSmallImage(img, imgName, imgType, rows, y, chunkWidth, chunkHeight, chunkWidth, unfitChunkHeight, collector);
            }if(unfitChunkWidth!=0){
                getUnfitSmallImage(img,imgName,imgType,rows,cols,chunkWidth,chunkHeight,unfitChunkWidth,unfitChunkHeight,collector);
            }
        }
        System.out.println("Creating small chunks for image "+ imageFile.imgName+" is finished ...");
    }
    public static void getSmallImage(BufferedImage imgSource,String imgName,String imgType,int startX,int startY,int chunkWidth,int chunkHeight,Collector<ChunkImageStream> collector) throws IOException {
        //Initialize the image array with image chunks
        BufferedImage imgsSmall = new BufferedImage(chunkWidth, chunkHeight, imgSource.getType());

        // draws the image chunk
        Graphics2D gr =  imgsSmall.createGraphics();
        gr.drawImage(imgSource, 0, 0, chunkWidth, chunkHeight, chunkWidth * startY, chunkHeight * startX, chunkWidth * startY + chunkWidth, chunkHeight * startX + chunkHeight, null);
        gr.dispose();
        System.out.println("Image row "+startX+" col "+startY+ " is created...");
        ImageIO.write(imgsSmall, "PNG", new File("img/save/flatImages/" +  chunkWidth * startY+"_"+chunkHeight * startX + ".png"));
        collector.collect(new ChunkImageStream(imgName,imgSource.getWidth(),imgSource.getHeight(),chunkWidth * startY,chunkHeight * startX,imgsSmall));
    }
    public static void getUnfitSmallImage(BufferedImage imgSource,String imgName,String imgType,int startX,int startY,int chunkWidth,int chunkHeight,int unfitChunkWidth,int unfitChunkHeight,Collector<ChunkImageStream> collector){
        //Initialize the image array with image chunks
        BufferedImage imgsSmall = new BufferedImage(chunkWidth, chunkHeight, imgSource.getType());

        // draws the image chunk
        Graphics2D gr =  imgsSmall.createGraphics();
        gr.drawImage(imgSource, 0, 0, chunkWidth, chunkHeight, chunkWidth * startY, chunkHeight * startX, chunkWidth * startY + unfitChunkWidth, chunkHeight * startX + unfitChunkHeight, null);
        gr.dispose();
        System.out.println("Image row "+startX+" col "+startY+ " is created...");
        collector.collect(new ChunkImageStream(imgName,imgSource.getWidth(),imgSource.getHeight(),chunkWidth * startY,chunkHeight * startX,imgsSmall));
    }
}
