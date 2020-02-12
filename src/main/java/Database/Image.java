package Database;

import com.helger.commons.base64.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Image {

    private String Image;
    private int ImgID;
    private int UserID;

    public Image(int UserID){
        this.Image = "";
        this.UserID = UserID;
        this.ImgID = -1;
    }

    public Image(int UserID, String img){
        this.Image = img;
        this.UserID = UserID;
        this.ImgID = -1;
    }

    public String GetImage(){
        return Image;
    }
    public int GetImgID(){
        return ImgID;
    }
    public BufferedImage GetBufferedImage(){

        BufferedImage image = null;
        try {
            byte[] imageByte;
            imageByte = Base64.decode(this.Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }
    public int GetUserID(){
        return UserID;
    }
    public void SetImage(BufferedImage Image, String format)   {
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(Image, format, bos );
            String encodedImage = Base64.encodeBytes(bos.toByteArray());
            this.Image = encodedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void SetImgID(int ImgID){
        this.ImgID = ImgID;
    }
    public void SetUserID(int UserID){
        this.UserID = UserID;
    }


}
