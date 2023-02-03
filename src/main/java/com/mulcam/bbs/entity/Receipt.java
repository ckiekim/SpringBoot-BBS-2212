package com.mulcam.bbs.entity;

import java.time.LocalDate;

public class Receipt {
	String shop;
    LocalDate buyDate;
    int price;
    
    public Receipt() { }
    public Receipt(String shop, LocalDate buyDate, int price) {
        this.shop = shop;
        this.buyDate = buyDate;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Receipt [shop=" + shop + ", buyDate=" + buyDate + ", price=" + price + "]";
    }
    
    public String getShop() {
        return shop;
    }
    public void setShop(String shop) {
        this.shop = shop;
    }
    public LocalDate getBuyDate() {
        return buyDate;
    }
    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
