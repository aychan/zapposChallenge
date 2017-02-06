package com.se.aychan.ilovezappos;

/**
 * Created by aychan on 2/5/17.
 * Search Query Object based on ReST Structure
 */

public class SearchQuery {
    private String originalTerm;
    private int currentResultCount;
    private int totalResultCount;
    private String term;
    private Product[] results;
    private int statusCode;

    public SearchQuery(String originalTerm, int currentResultCount, int totalResultCount, String term, Product[] results, int statusCode) {
        this.originalTerm = originalTerm;
        this.currentResultCount = currentResultCount;
        this.totalResultCount = totalResultCount;
        this.term = term;
        this.results = results;
        this.statusCode = statusCode;
    }

    //add product to ArrayList


    // GETTERS //
    public String getOriginalTerm() {
        return originalTerm;
    }

    public int getCurrentResultCount() {
        return currentResultCount;
    }

    public int getTotalResultCount() {
        return totalResultCount;
    }

    public String getTerm() {
        return term;
    }

    public Product[] getResults() {
        return results;
    }

    public int getStatusCode() {
        return statusCode;
    }
    ////////////

    // SETTER //
    public void setOriginalTerm(String originalTerm) {
        this.originalTerm = originalTerm;
    }

    public void setCurrentResultCount(int currentResultCount) {
        this.currentResultCount = currentResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setResults(Product[] results) {
        this.results = results;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    ////////////
}
