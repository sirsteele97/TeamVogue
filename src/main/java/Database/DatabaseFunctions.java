package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import armdb.*;

public class DatabaseFunctions {

    static ConnectHost con = null;

    static int CurrentUserID = 0;

    public static void Connect(){
        try{
            String fileURL="https://masonmordue.com/handleSQL.php";	//url of 'habdleSQL.php', remember that the 'habdleSQL.php' must be in the same server in which interested database is located
            String host="localhost";					//server host name
            String user="masohtdc_sirsteele97";						//username
            String pass="Steele1997";						//password
            String DBName="masohtdc_TeamVogue";						//database name
            con=new ConnectHost(fileURL, host, user, pass, DBName);	//make connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class DBImages {

        private enum columns {USER_ID, IMG_ID, IMG}

        //Selects list of images
        public static List SelectImages(int UserID) {
            List images = new ArrayList<Image>();
            QueryResult qr = null;
            SQLQuery query = new SQLQuery(con);
            String username = "";
            try {
                qr = query.statement("select * from Images WHERE User_ID = " + UserID + ";");        //execution of query statement
                while (qr.nextFlag()) {                               //setting flag to next row till next row exists
                    //print column_1 & column_2 value of row where flag is set

                    if (Integer.parseInt(qr.getValue("User_ID")) == (UserID)) {
                        Image image = new Image(Integer.valueOf(qr.getValue("User_ID")), qr.getValue("Image"));
                        image.SetImgID(Integer.valueOf(qr.getValue("Img_ID")));
                        images.add(image);
                    }
                }
            } catch (SQLQueryException e) {                        //catch exception if occurred
                System.out.println(e.getMessage());                    //print exception message
            }
            return images;
        }


        //Selects list of ALL Images
        public static List SelectImages() {

            List images = new ArrayList<Image>();
            QueryResult qr = null;
            SQLQuery query = new SQLQuery(con);
            String username = "";
            try {
                qr = query.statement("select * from Images;");        //execution of query statement
                while (qr.nextFlag()) {                               //setting flag to next row till next row exists
                    //print column_1 & column_2 value of row where flag is set
                    Image image = new Image(Integer.valueOf(qr.getValue("User_ID")), qr.getValue("Image"));
                    image.SetImgID(Integer.valueOf(qr.getValue("Img_ID")));
                    images.add(image);

                }
            } catch (SQLQueryException e) {                        //catch exception if occurred
                System.out.println(e.getMessage());                    //print exception message
            }
            return images;
        }


        //Selects single image based off ImageID
        public static Image SelectSingleImage(int ImageID) {
            QueryResult qr = null;
            Image image = null;
            SQLQuery query = new SQLQuery(con);
            String username = "";
            try {
                qr = query.statement("select * from Images;");        //execution of query statement
                while (qr.nextFlag()) {                               //setting flag to next row till next row exists
                    if (Integer.parseInt(qr.getValue("Img_ID")) == ImageID) {
                        image = new Image(Integer.valueOf(qr.getValue("User_ID")), qr.getValue("Image"));
                        image.SetImgID(Integer.valueOf(qr.getValue("Img_ID")));
                    }

                }
            } catch (SQLQueryException e) {                        //catch exception if occurred
                System.out.println(e.getMessage());                    //print exception message
            }
            return image;
        }

        public static int SelectSingleImage(Image image) {
            QueryResult qr = null;
            SQLQuery query = new SQLQuery(con);

            try {
                qr = query.statement("select * from Images;");        //execution of query statement
                while (qr.nextFlag()) {
                    String img1 = qr.getValue("image");
                    String img2 = image.GetImage();
                    boolean b = false;
                    if(img1.length() > 99){
                        b = img1.substring(1,100).equals(img2.substring(1,100));
                    }
                    if (b) {
                        image.SetImgID(Integer.valueOf(qr.getValue("Img_ID")));
                    }

                }
            } catch (SQLQueryException e) {                        //catch exception if occurred
                System.out.println(e.getMessage());                    //print exception message
            }
            return image.GetImgID();
        }

        //Creates a new Image Entry
        public static int CreateImage(Image image) {
            int isCreated = -1;

            Boolean AlreadyImage = true;
            QueryResult qr = null;
            SQLUpdate query = new SQLUpdate(con);
            try {
                AlreadyImage = ConfirmImage(image);
                if (!AlreadyImage) {
                    query.statement("INSERT INTO Images (User_ID, Image) VALUES ('" + image.GetUserID() + "','" + image.GetImage() + "');");
                    isCreated = SelectSingleImage(image);
                }
            } catch (SQLUpdateException e) {
                System.out.println(e.getMessage());
            }
            return isCreated;
        }

        //Deletes Image Entry
        public static Boolean DeleteImage(int ImageID) {
            Boolean userExists = false;
            Boolean AlreadyUser = true;
            QueryResult qr = null;
            SQLUpdate query = new SQLUpdate(con);
            try {
                Image image = SelectSingleImage(ImageID);
                if (!image.equals(null)) {
                    userExists = true;
                    query.statement("DELETE FROM Images WHERE Img_ID = '" + ImageID + "';");
                }

            } catch (SQLUpdateException e) {
                System.out.println(e.getMessage());
            }
            return userExists;
        }

        public static Boolean ConfirmImage(Image image) {
            Boolean confirmed = false;
            QueryResult qr = null;
            SQLQuery query = new SQLQuery(con);
            try {
                qr = query.statement("select * from Images");

                String img1 = qr.getValue("image");
                String img2 = image.GetImage();
                boolean b = false;
                if(img1.length() > 99){
                    b = img1.substring(1,100).equals(img2.substring(1,100));
                }

                while (qr.nextFlag()) {                               //setting flag to next row till next row exists
                    //print column_1 & column_2 value of row where flag is set

                    if (Integer.parseInt(qr.getValue("Img_ID")) == image.GetImgID() || b) {
                        confirmed = true;
                    }
                }
            } catch (SQLQueryException e) {
                System.out.println(e.getMessage());
            }
            return confirmed;
        }

        public static void UpdateImage(Image image, String NewImage){

            QueryResult qr = null;
            SQLUpdate query=new SQLUpdate(con);
            try{
                int img_id = SelectSingleImage(image);
                if(img_id > 0){
                    query.statement("UPDATE Images SET Image = '" + NewImage + "' WHERE Img_ID = '"+img_id+"';");
                }

            }catch(SQLUpdateException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static class DBAccounts{
        private enum columns{USER_ID, USERNAME, PASSWORD}

        //Selects Username
        public static String SelectUsername(int UserID){
            QueryResult qr = null;
            SQLQuery query=new SQLQuery(con);
            String username = "";
            try{
                qr=query.statement("select * from Users");		//execution of query statement
                int rows = qr.numRows();
                username = qr.getValueAt(UserID-1, 1);

            }catch(SQLQueryException e){						//catch exception if occurred
                System.out.println(e.getMessage());					//print exception message
            }
            return username;
        }

        //Selects Password
        public static String SelectPassword(int UserID) {

            QueryResult qr = null;
            SQLQuery query=new SQLQuery(con);
            String password = "";
            try{
                qr=query.statement("select * from Users");		//execution of query statement
                int rows = qr.numRows();
                password = qr.getValueAt(UserID-1, 2);

            }catch(SQLQueryException e){						//catch exception if occurred
                System.out.println(e.getMessage());					//print exception message
            }
            return password;
        }
            //returns true if Credentials exist in the database
            public static Boolean ConfirmCredentials(String Username, String Password){
            Boolean confirmed = false;
            QueryResult qr = null;
            SQLQuery query=new SQLQuery(con);
            try {
                qr = query.statement("select * from Users");
                while (qr.nextFlag()) {                               //setting flag to next row till next row exists
                    //print column_1 & column_2 value of row where flag is set

                    if(qr.getValue("Username").equals(Username) && qr.getValue("Password").equals(Password)){
                        confirmed = true;
                    }
                }
            }catch(SQLQueryException e){
                System.out.println(e.getMessage());
            }
            return confirmed;
        }

        public static Boolean ConfirmCredentials(User user){
            return ConfirmCredentials(user.GetUsername(), user.GetPassword());
        }
        //Returns -1 if ID is not found
        public static int SelectUserID(String Username, String Password){
            String id = "-1";
            QueryResult qr = null;
            SQLQuery query=new SQLQuery(con);
            try {
                qr = query.statement("select * from Users");
                while (qr.nextFlag()) {                               //setting flag to next row till next row exists
                    //print column_1 & column_2 value of row where flag is set

                    if(qr.getValue("Username").equals(Username) && qr.getValue("Password").equals(Password)){
                        id = qr.getValue(0);
                    }
                }
            }catch(SQLQueryException e){
                System.out.println(e.getMessage());
            }
            return Integer.parseInt(id);
        }

        public static int SelectUserID(User user){
            return SelectUserID(user.GetUsername(), user.GetUsername());
        }

        //Returns a user
        public static User SelectUser(int ID){
            User user = null;
            QueryResult qr = null;
            SQLQuery query=new SQLQuery(con);
            try {
                qr = query.statement("select * from Users");
                while (qr.nextFlag()) {                               //setting flag to next row till next row exists
                    //print column_1 & column_2 value of row where flag is set

                    if(Integer.valueOf(qr.getValue(0)) == ID){
                        user = new User(qr.getValue(1), qr.getValue(2));
                        user.SetUserID(ID);
                    }
                }
            }catch(SQLQueryException e){
                System.out.println(e.getMessage());
            }
            return user;
        }

        //returns User_ID, if -1 then user already exists
        private static int CreateUser(String Username, String Password){
            int id = -1;
            Boolean AlreadyUser = true;
            QueryResult qr = null;
            SQLUpdate query=new SQLUpdate(con);
            try{
                AlreadyUser = ConfirmCredentials(Username, Password);
                if(!AlreadyUser){
                    query.statement("INSERT INTO Users (Username, Password) VALUES ('" + Username + "','" + Password + "');");
                    id = Integer.valueOf(SelectUserID(Username, Password));
                }
            }catch(SQLUpdateException e){
                System.out.println(e.getMessage());
            }
            return id;
        }

        public static int CreateUser(User user){
            return CreateUser(user.GetUsername(), user.GetPassword());
        }

        public static void UpdateUsername(int UserID, String Username){
            QueryResult qr = null;
            SQLUpdate query=new SQLUpdate(con);
            try{
                User user = SelectUser(UserID);
                if(!user.equals(null)){
                    query.statement("UPDATE Users SET Username = '" + Username + "' WHERE User_ID = '"+UserID+"';");
                }

            }catch(SQLUpdateException e){
                System.out.println(e.getMessage());
            }
        }
        public static void UpdatePassword(int UserID, String Password){

            QueryResult qr = null;
            SQLUpdate query=new SQLUpdate(con);
            try{
                User user = SelectUser(UserID);
                if(!user.equals(null)){
                    query.statement("UPDATE Users SET Password = '" + Password + "' WHERE User_ID = '"+UserID+"';");
                }

            }catch(SQLUpdateException e){
                System.out.println(e.getMessage());
            }
        }

        public static Boolean DeleteUser(int User_ID){
            Boolean userExists = false;
            Boolean AlreadyUser = true;
            QueryResult qr = null;
            SQLUpdate query=new SQLUpdate(con);
            try{
                User user = SelectUser(User_ID);
                if(!user.equals(null)){
                    userExists = true;
                    query.statement("DELETE FROM Users WHERE User_ID = '" + User_ID + "';");
                }

            }catch(SQLUpdateException e){
                System.out.println(e.getMessage());
            }
            return userExists;
        }
    }
}



