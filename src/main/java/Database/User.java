package Database;

public class User {

    private String Username;
    private String Password;
    private int UserID;

    public User(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public String GetUsername(){
        return Username;
    }

    public String GetPassword(){
        return Password;
    }

    public int GetUserID(){
        return UserID;
    }

    public void SetUsername(String Username){
        this.Username = Username;
    }

    public void SetPassword(String Password){
        this.Password = Password;
    }

    public void SetUserID(int UserID){
        this.UserID = UserID;
    }
}
