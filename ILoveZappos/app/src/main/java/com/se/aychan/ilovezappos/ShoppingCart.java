package com.se.aychan.ilovezappos;

import java.util.ArrayList;

/**
 * Created by aychan on 2/9/17.
 * Holds most current list of Products within shopping cart
 */
public class ShoppingCart {
    private ArrayList<Product> products = new ArrayList<>();

    private static ShoppingCart ourInstance = new ShoppingCart();

    static ShoppingCart getInstance() {
        return ourInstance;
    }

    private ShoppingCart() {
    }

    void addToCart(Product product){
        if(products != null && !products.contains(product)){
            products.add(product);
        }
    }

    void removeFromCart(Product product){
        if(products != null && products.contains(product)){
            products.remove(product);
        }
    }

    void removeAllFromCart(){
        products.clear();
    }

    Product[] getAllFromCart(){
        return (Product[]) products.toArray();
    }

    int getCartCount(){
        if(products == null){
            return 0;
        }
        return products.size();
    }
}
