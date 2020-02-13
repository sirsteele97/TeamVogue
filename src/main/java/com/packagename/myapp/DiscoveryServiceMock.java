package com.packagename.myapp;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DiscoveryServiceMock {

    public List<String> imageUrls(String clothesParam, String colorParam){
        List<String> images = new LinkedList<String>();

        if(clothesParam.equalsIgnoreCase("") && colorParam.equalsIgnoreCase("")){
            images.add("https://cdn.shopify.com/s/files/1/0210/9734/products/7w8hFiH.jpg?v=1571265085");
            images.add("https://image.uniqlo.com/UQ/ST3/WesternCommon/imagesgoods/407044/item/goods_09_407044.jpg?width=2000");
        }
        else if(clothesParam.equalsIgnoreCase("long sleeve shirt") && colorParam.equalsIgnoreCase("blue")){
            images.add("https://cdn.shopify.com/s/files/1/0210/9734/products/7w8hFiH.jpg?v=1571265085");
        }
        else if(clothesParam.equalsIgnoreCase("short sleeve shirt") && colorParam.equalsIgnoreCase("black")){
            images.add("https://image.uniqlo.com/UQ/ST3/WesternCommon/imagesgoods/407044/item/goods_09_407044.jpg?width=2000");
        }

        return images;
    }

}
