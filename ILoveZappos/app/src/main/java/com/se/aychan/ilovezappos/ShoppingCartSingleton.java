package com.se.aychan.ilovezappos;

import java.util.ArrayList;

/**
 * Created by aychan on 2/9/17.
 * Holds most current list of Products within shopping cart
 */
public class ShoppingCartSingleton {
    private ArrayList<Product> products = new ArrayList<>();

    private static ShoppingCartSingleton ourInstance = new ShoppingCartSingleton();

    static ShoppingCartSingleton getInstance() {
        return ourInstance;
    }

    private ShoppingCartSingleton() {
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

    double getTotalCost(){
        double total = 00.00;
        for(Product p : products){
            String priceStr = null;
            if (p.hasDiscount()){
                priceStr = p.getPrice().substring(1);
            }else{
                priceStr = p.getOriginalPrice().substring(1);
            }
            Double price = Double.parseDouble(priceStr);
            total+=price;
        }
        return total;
    }
}
