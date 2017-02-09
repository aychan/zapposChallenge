package com.se.aychan.ilovezappos;

import java.util.ArrayList;

/**
 * Created by aychan on 2/9/17.
 * Holds most current list of Products within shopping cart
 */
public class ShoppingCart {
    private ArrayList<Product> products;

    private static ShoppingCart ourInstance = new ShoppingCart();

    static ShoppingCart getInstance() {
        return ourInstance;
    }

    private ShoppingCart() {
    }

    void addToCart(Product product){
        if(!products.contains(product)){
            products.add(product);
        }
    }

    void removeFromCart(Product product){
        if(products.contains(product)){
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
        return products.size();
    }
}
