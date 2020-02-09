package com.packagename.myapp.Database;

import Database.DatabaseFunctions;
import Database.User;
import armdb.SQLQueryException;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;

public class DatabaseTests {


    @Test
    public void CanConnect() {
        try{
            DatabaseFunctions.Connect();
        }catch(SQLQueryException e){
            Assert.assertTrue(false);
        }

    }

    @Test
    public void ReturnSelectedUsername() {
        CanConnect();
        String username = DatabaseFunctions.DBAccounts.SelectUsername(1);
        Assert.assertTrue(username.equals("Sirsteele97"));
    }

    @Test
    public void ReturnSelectedPassword() {
        CanConnect();
        String username = DatabaseFunctions.DBAccounts.SelectPassword(1);
        Assert.assertTrue(username.equals("Steele97"));
    }

    @Test
    public void TestConfirmCredentials() {
        CanConnect();
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials("Sirsteele97", "Steele97"));
    }

    @Test
    public void ReturnSelectedUserID() {
        CanConnect();
        int userID = DatabaseFunctions.DBAccounts.SelectUserID("Sirsteele97", "Steele97");
        Assert.assertTrue(userID == 1);
    }

    @Test
    public void ReturnSelectedUser() {
        CanConnect();
        User user = DatabaseFunctions.DBAccounts.SelectUser(1);
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials(user.GetUsername(), user.GetPassword()));
    }

    @Test
    public void TestAddAndDeleteUser() {
        CanConnect();
        int id = DatabaseFunctions.DBAccounts.CreateUser("Test15", "Test1234");
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials("Test15", "Test1234"));
        DatabaseFunctions.DBAccounts.DeleteUser(id);
        Assert.assertFalse(DatabaseFunctions.DBAccounts.ConfirmCredentials("Test15", "Test1234"));

    }

    @Test
    public void TestUpdateUsername() {
        CanConnect();
        DatabaseFunctions.DBAccounts.UpdateUsername(1, "MaceMan97");
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials("MaceMan97", "Steele97"));
        DatabaseFunctions.DBAccounts.UpdateUsername(1, "Sirsteele97");
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials("Sirsteele97", "Steele97"));
    }

    @Test
    public void TestUpdatePassword() {
        CanConnect();
        DatabaseFunctions.DBAccounts.UpdatePassword(1, "Steele1997");
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials("Sirsteele97", "Steele1997"));
        DatabaseFunctions.DBAccounts.UpdatePassword(1, "Steele97");
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials("Sirsteele97", "Steele97"));
    }

}
