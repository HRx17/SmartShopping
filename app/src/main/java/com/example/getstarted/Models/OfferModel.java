package com.example.getstarted.Models;

public class OfferModel {
    String img_url;
    String name;
    int price;
    String documentId;

    public OfferModel(String img_url, String name, int price, String documentId) {
        this.img_url = img_url;
        this.name = name;
        this.price = price;
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
