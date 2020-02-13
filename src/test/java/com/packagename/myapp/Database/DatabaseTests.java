package com.packagename.myapp.Database;

import Database.DatabaseFunctions;
import Database.Image;
import Database.User;
import armdb.SQLQueryException;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DatabaseTests {


    @Test
    public void CanConnect() {
        DatabaseFunctions.Connect();

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
        User user = new User("Sirsteele97", "Steele97");
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials(user));
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
        Assert.assertTrue(DatabaseFunctions.DBAccounts.ConfirmCredentials(user));
    }

    @Test
    public void TestAddAndDeleteUser() {
        CanConnect();
        User user = new User("Test15", "Test1234");
        int id = DatabaseFunctions.DBAccounts.CreateUser(user);
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

    @Test
    public void ReturnSelectedImage() {
        CanConnect();
        Image img = DatabaseFunctions.DBImages.SelectSingleImage(1);
        Assert.assertTrue(img.GetImage().equals("12345"));
    }

    @Test
    public void ReturnSelectedImageID() {
        CanConnect();
        Image image = new Image(1, "12345");
        int id = DatabaseFunctions.DBImages.SelectSingleImage(image);
        Assert.assertTrue(id == 1);
    }
    @Test
    public void ReturnSelectedImages() {
        CanConnect();
        List<Image> images = DatabaseFunctions.DBImages.SelectImages(1);
        Assert.assertTrue(images.size() > 1);
    }

    @Test
    public void TestConfirmImage() {
        CanConnect();
        Image image = new Image(1, "12345");
        Assert.assertTrue(DatabaseFunctions.DBImages.ConfirmImage(image));
    }


    @Test
    public void TestAddAndDeleteImage() {
        CanConnect();
        Image image = new Image(1, "4567");
        int id = DatabaseFunctions.DBImages.CreateImage(image);
        Assert.assertTrue(DatabaseFunctions.DBImages.ConfirmImage(image));
        DatabaseFunctions.DBImages.DeleteImage(id);
        Assert.assertFalse(DatabaseFunctions.DBImages.ConfirmImage(image));

    }

    @Test
    public void TestUpdateImage() {
        CanConnect();
        Image image = new Image(1, "12345");
        Image image2 = new Image(1, "890765");
        DatabaseFunctions.DBImages.UpdateImage(image, "890765");
        Assert.assertTrue(DatabaseFunctions.DBImages.ConfirmImage(image2));
        DatabaseFunctions.DBImages.UpdateImage(image2,"12345" );
        Assert.assertTrue(DatabaseFunctions.DBImages.ConfirmImage(image));
    }
    }

