package com.packagename.myapp;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3Service {

    private AmazonS3 s3;

    public S3Service(){
        AWSCredentials credentials = new BasicAWSCredentials("AKIAJQUOZIQEYALRXFEA","byuWhE/rwU0RMxSfG+uoRc0M7vfhyO1OgqZCVWhR");
        s3 = new AmazonS3Client(credentials);
    }

    public String uploadImage(InputStream inputStream,String fileName){
        PutObjectRequest request = new PutObjectRequest("team-vogue-images",fileName,inputStream,null)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = s3.putObject(request);
        return fileName;
    }

    public String getImageUrl(String fileName){
        return "https://team-vogue-images.s3.us-east-2.amazonaws.com/"+fileName;
    }

}
