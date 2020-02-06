package Database;


import java.sql.*;
import java.util.List;

public class DatabaseFunctions {

    private static String ConnectionName = "\"jdbc:mysql://198.187.29.149:3306/TeamVogue\",\"root\",\"TeamVogue1\"";

    public static class DBImages{

        private enum columns{USER_ID, IMG_ID, IMG}

        //Selects list of images
        public static List SelectImages(int UserID){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection(ConnectionName);
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery("select image from Images where ImageID = " + UserID);

            }catch(Exception e){ System.out.println(e);}
            return null;
        }

        //Selects list of ALL Images
        public static List SelectImages(){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection(ConnectionName);
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery("select image from Images");

            }catch(Exception e){ System.out.println(e);}
            return null;
        }

        //Selects single image based off ImageID
        public static List SelectSingleImage(int ImageID){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection(ConnectionName);
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery("select image from Images where ImageID="+ ImageID);

            }catch(Exception e){ System.out.println(e);}
            return null;
        }

        //Creates a new Image Entry
        public static int CreateImage(int UserID){

            return 0;
        }

        //Deletes Image Entry
        public static void DeleteImage(int ImgageID){

        }
    }

    public static class DBAccounts{
        private enum columns{USER_ID, USERNAME, PASSWORD}

        //Selects Username
        public static String SelectUsername(int UserID){

            return null;
        }

        //Selects Password
        public static String SelectPassword(int UserID){

            return null;
        }

        public static Boolean ConfirmCredentials(String Username, String Password){

            return null;
        }

        public static List SelectUserID(String Username, String Password){

            return null;
        }

        public static int CreateUser(String Username, String Password){

            return 0;
        }

        public static int UpdateUsername(int UserID, String Username){

            return 0;
        }
        public static int UpdatePassword(int UserID, String Password){

            return 0;
        }
    }
}


