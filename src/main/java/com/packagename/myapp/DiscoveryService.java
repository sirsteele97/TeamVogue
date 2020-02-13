package com.packagename.myapp;

import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class DiscoveryService {

    String key = "quleCLx3QpXA48No0xDwqlXEasla_kk2sLwRMt60Bitj";
    String url = "https://api.au-syd.discovery.watson.cloud.ibm.com/instances/f5002da5-7966-47cc-a3e9-c87dd9d590ff";

    String clothesCollection = "f2ee7a33-3dcd-42bc-81d5-431738ff9173";
    String clothesEnvironment = "100a78cb-2b22-4b88-922b-97b7a63b5a1d";

    public Map<String,Map<String,String>> getImages(String clothesParam, String colorParam) {
        Map<String,Map<String,String>> imageUrls = new HashMap<String,Map<String,String>>();

        IamAuthenticator authenticator = new IamAuthenticator(key);
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl(url);

        LinkedList<String> filters = new LinkedList<String>();
        if(!clothesParam.equalsIgnoreCase("")){
            filters.add("ClothModel::"+clothesParam);
        }
        if(!colorParam.equalsIgnoreCase("")){
            filters.add("ColorModel::"+colorParam);
        }

        String filter = "";
        for(int i=0;i<filters.size();i++){
            filter += filters.get(i);
            if(i != filters.size()-1)
                filter +=", ";
        }

        QueryOptions queryOptions = new QueryOptions.Builder(clothesEnvironment,clothesCollection)
                .query(filter).build();
        QueryResponse response = discovery.query(queryOptions).execute().getResult();

        List<QueryResult> results = response.getResults();
        for(QueryResult doc : results){
            Map<String,String> imageMap = new HashMap<String,String>();
            imageMap.put("FileName",doc.get("FileName").toString());
            imageMap.put("ClothModel",doc.get("ClothModel").toString());
            imageMap.put("ColorModel",doc.get("ColorModel").toString());
            imageUrls.put(doc.getId(),imageMap);
        }

        return imageUrls;
    }

    public void addClothing(String json) {
        IamAuthenticator authenticator = new IamAuthenticator(key);
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl(url);

        InputStream documentStream = new ByteArrayInputStream(json.getBytes());
        AddDocumentOptions addDocumentOptions = new AddDocumentOptions.Builder(clothesEnvironment,clothesCollection)
                .file(documentStream)
                .filename(System.nanoTime()+"")
                .fileContentType(HttpMediaType.APPLICATION_JSON)
                .build();
        DocumentAccepted accepted = discovery.addDocument(addDocumentOptions).execute().getResult();
    }

    public void deleteClothing(String docId){
        IamAuthenticator authenticator = new IamAuthenticator(key);
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl(url);

        DeleteDocumentOptions deleteDocumentOptions = new DeleteDocumentOptions.Builder(clothesEnvironment,clothesCollection,docId).build();
        discovery.deleteDocument(deleteDocumentOptions).execute().getResult();
    }

}
