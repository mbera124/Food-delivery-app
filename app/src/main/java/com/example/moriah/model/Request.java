package com.example.moriah.model;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Request {
    private  String name;
    private String address;
    private String total;
    private String status;
    private String ProductId;
    private String ProductName;
    private String UnitPrice;
    private String Quantity;
    private String totalOrderPrice;
    private List<Order> foods;

    public Request() {
    }

    //    public Request(String s, String phone, String key, List<Order> orderList) {
//    }

    public Request(String name, String address,String total,String productName, String unitPrice, String quantity, String totalOrderPrice) {

        this.name = name;
        this.address = address;
        this.total = total;
        this.ProductName = productName;
        this.UnitPrice = unitPrice;
        this.Quantity = quantity;
        this.totalOrderPrice = totalOrderPrice;
        this.status = "0";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

}
