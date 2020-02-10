package Database;

public class Image {

    private String Image;
    private int ImgID;
    private int UserID;

    public Image(int UserID, String Image){
        this.Image = Image;
        this.UserID = UserID;
    }

    public String GetImage(){
        return Image;
    }
    public int GetImgID(){
        return ImgID;
    }
    public int GetUserID(){
        return UserID;
    }
    public void SetImage(String Image){
        this.Image = Image;
    }
    public void SetImgID(int ImgID){
        this.ImgID = ImgID;
    }
    public void SetUserID(int UserID){
        this.UserID = UserID;
    }


}
