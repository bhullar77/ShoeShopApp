package com.example.shoeshopapp;

import java.util.List;

public class ShoeAppUtils {

    public static Product findProduct(int pid){
        for(Product pp:getProducts()){
            if(pp.getId()==pid){
                return pp;
            }
        }
        return null;
    }

    public static List<Product> getProducts() {
        List<Product> list = SplashScreen.plist;
        return list;
    }

    public static List<User> getUsers(){
        return null;
    }

}
