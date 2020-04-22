package com.packagename.myapp.Services.Interfaces;

public interface IUserStorage {

    void addUser(String username, String password, String zipCode);

    void updateUser(String docId);

    void deleteUser(String docId);

    boolean validCredentials(String username, String password);

    boolean userExists(String username);

    String getUserZipCode(String username);
}
