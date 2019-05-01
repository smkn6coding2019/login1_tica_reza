package com.example.gitasoft.login1;

public class DataProduct {
    String id;
    String title;
    String shortdesc;
    String rating;
    String price;
    String image;

    public DataProduct(){

    }

    public DataProduct(String ID, String Title, String Shortdesc, String Rating, String Price, String Image){
        this.id = ID;
        this.title = Title;
        this.shortdesc = Shortdesc;
        this.rating = Rating;
        this.price = Price;
        this.image = Image;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
