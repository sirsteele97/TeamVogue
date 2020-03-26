package com.packagename.myapp.Services;

import com.google.gson.Gson;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;
import com.packagename.myapp.Services.Interfaces.IUserStorage;
import com.packagename.myapp.Utils.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscoveryUserStorage implements IUserStorage {

    private final String key = KeyHolder.getKey("ibm.discovery.apiKey");
    private final String url = "https://api.au-syd.discovery.watson.cloud.ibm.com/instances/f5002da5-7966-47cc-a3e9-c87dd9d590ff";

    private final String userCollection = "fc14d61b-e50a-4c8f-a4c0-e8fb4d3fd8a3";
    private final String userEnvironment = "100a78cb-2b22-4b88-922b-97b7a63b5a1d";

    @Override
    public void addUser(String username, String password, String zipCode) {
        if(userExists(username) || username.isEmpty() || password.isEmpty() || zipCode.isEmpty()){
            return;
        }

        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt());

        Map<String,String> userData = new HashMap<String,String>();
        userData.put("Username",username);
        userData.put("Password",hashedPassword);
        userData.put("ZipCode",zipCode);

        Gson gson = new Gson();
        String json = gson.toJson(userData);

        IamAuthenticator authenticator = new IamAuthenticator(key);
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl(url);

        InputStream documentStream = new ByteArrayInputStream(json.getBytes());
        AddDocumentOptions addDocumentOptions = new AddDocumentOptions.Builder(userEnvironment, userCollection)
                .file(documentStream)
                .filename(System.nanoTime()+"")
                .fileContentType(HttpMediaType.APPLICATION_JSON)
                .build();
        DocumentAccepted accepted = discovery.addDocument(addDocumentOptions).execute().getResult();
    }

    @Override
    public void updateUser(String docId) {

    }

    @Override
    public void deleteUser(String docId) {
        IamAuthenticator authenticator = new IamAuthenticator(key);
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl(url);

        DeleteDocumentOptions deleteDocumentOptions = new DeleteDocumentOptions.Builder(userEnvironment,userCollection,docId).build();
        discovery.deleteDocument(deleteDocumentOptions).execute().getResult();
    }

    @Override
    public boolean validCredentials(String username, String password) {
        IamAuthenticator authenticator = new IamAuthenticator(key);
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl(url);

        if(username.isEmpty()){
            return false;
        }

        String filter = "Username::"+username;
        QueryOptions queryOptions = new QueryOptions.Builder(userEnvironment, userCollection)
                .filter(filter).build();
        QueryResponse response = discovery.query(queryOptions).execute().getResult();

        List<QueryResult> results = response.getResults();
        for(QueryResult doc : results){
            System.out.println(doc.getId());
            if(BCrypt.checkpw(password,doc.get("Password").toString())){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean userExists(String username) {
        if(username.isEmpty()){
            return false;
        }

        IamAuthenticator authenticator = new IamAuthenticator(key);
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl(url);

        String filter = "Username::"+username;
        QueryOptions queryOptions = new QueryOptions.Builder(userEnvironment, userCollection)
                .filter(filter).build();
        QueryResponse response = discovery.query(queryOptions).execute().getResult();

        List<QueryResult> results = response.getResults();
        if(results.size() > 0) {
            return true;
        }

        return false;
    }
}
