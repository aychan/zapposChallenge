package com.se.aychan.ilovezappos;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by aychan on 2/9/17.
 */
public class SearchSingleton {

    private ArrayList<Product> products = new ArrayList<>();
    private static SearchSingleton ourInstance = new SearchSingleton();
    private Product blank = new Product("No Products Match your query","",0,"",0,0,"","","","");
    static SearchSingleton getInstance() {
        return ourInstance;
    }

    private SearchSingleton() {
    }

    public void addToSearchQuery(Product product){
        if(products != null && products.contains(product)){
            products.add(product);
        }
    }
    void addAll(Product[] products){
        Collections.addAll(this.products, products);
    }
    void clearQuery(){
        if(products!=null && products.size()!=0){
            products.clear();
        }
    }
    Product[] getAll(){
        if (products != null){
            return (Product[]) products.toArray();
        }else{
            return new Product[]{blank};
        }
    }
}
