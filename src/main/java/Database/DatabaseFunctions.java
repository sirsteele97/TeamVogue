package Database;

import java.sql.*;
import java.util.List;

import armdb.*;
import com.jcraft.jsch.*;
import com.mysql.jdbc.Driver;


public class DatabaseFunctions {

    static String url = "jdbc:mysql://198.187.29.149:22/masohtdc_TeamVogue";
    static String username = "masohtdc_sirsteele97";
    static String password = "FD80E683B904F2D41508DF7A9ED2C7DF2B181DD7";
    static String husername = "masohtdc";
    static String hpassword = "Steele97*";
    static private ConnectHost con = null;


    public static void Connect() throws SQLQueryException {

        String fileURL="https://masonmordue.com/handleSQL.php";	//url of 'habdleSQL.php', remember that the 'habdleSQL.php' must be in the same server in which interested database is located
        String host="localhost";					//server host name
        String user="masohtdc_sirsteele97";						//username
        String pass="Steele1997";						//password
        String DBName="masohtdc_TeamVogue";						//database name

        con=new ConnectHost(fileURL, host, user, pass, DBName);	//make connection
    }

    public static class DBImages{

        private enum columns{USER_ID, IMG_ID, IMG}

        //Selects list of images
        public static List SelectImages(int UserID){

            return null;
        }

        //Selects list of ALL Images
        public static List SelectImages(){

            return null;
        }

        //Selects single image based off ImageID
        public static List SelectSingleImage(int ImageID){

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
            return Integer.valueOf(id);
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
        public static int CreateUser(String Username, String Password){
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


