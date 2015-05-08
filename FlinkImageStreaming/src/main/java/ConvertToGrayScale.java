/**
 * Created by shakirullah on 5/8/15.
 */

import java.awt.*;
        import java.awt.image.BufferedImage;

        import java.io.*;

        import javax.imageio.ImageIO;

public class ConvertToGrayScale {

    BufferedImage image;
    int width;
    int height;

    public ConvertToGrayScale() {

        try {
            File input = new File("abc.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
        System.out.println(image.getType());
            for(int i=0; i<height; i++){

                for(int j=0; j<width; j++){

                    Color c = new Color(image.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() *0.114);
                    Color newColor = new Color(red+green+blue,

                            red+green+blue,red+green+blue);

                    image.setRGB(j,i,newColor.getRGB());
                }
            }

            File ouptut = new File("grayscale.jpg");
            ImageIO.write(image, "jpg", ouptut);

        } catch (Exception e) {}
    }

    static public void main(String args[]) throws Exception
    {
        ConvertToGrayScale obj = new ConvertToGrayScale();
    }
}