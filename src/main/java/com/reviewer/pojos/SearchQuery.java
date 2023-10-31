package com.reviewer.pojos;

import java.io.Serializable;

public class SearchQuery implements Serializable {

    private static final long serialVersionUID = 1234132421041204214l;

    private String searchPhrase;
    private String price;
    private String rating;
    private String sorting;
    private String venueType;

    public String getVenueType() {
        return venueType;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(final String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(final String sorting) {
        this.sorting = sorting;
    }

}
