import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by shakirullah on 5/31/15.
 */
public class ChunkImageStream implements Serializable{
    private String IMAGEID;//ID of the original large image
    private BufferedImage img;//small image
    public int orgImageWidth;
    public int orgImageHeight;
    private int startX;//start row position at original image.
    private int startY;//start column position at original image

    public ChunkImageStream(String oImgID, int orgImageWidth, int orgImageHeight, int startX, int startY, BufferedImage img){
        this.IMAGEID=oImgID;
        this.orgImageWidth=orgImageWidth;
        this.orgImageHeight=orgImageHeight;
        this.startX=startX;
        this.startY=startY;
        this.img=img;
    }
    public ChunkImageStream(String oImgID, int startX, int startY, BufferedImage img){
        this.IMAGEID=oImgID;
        this.startX=startX;
        this.startY=startY;
        this.img=img;
    }
    public void setImageData( BufferedImage img){
        this.img=img;
    }
    public BufferedImage getImageData(){
        return img;
    }
    public int startX(){
        return startX;
    }
    public int startY(){
        return startY;
    }
    // getter / setter for private field (IMAGEID)
    public String getIMAGEID() {
        return IMAGEID;
    }
    public void setIMAGEID(String c) {
        this.IMAGEID = c;
    }

    public String getImageExtension(){
        int extIndx=IMAGEID.lastIndexOf(".");
        return IMAGEID.substring(extIndx + 1);
    }
}
