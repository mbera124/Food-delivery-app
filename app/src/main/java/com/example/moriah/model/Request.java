package com.example.moriah.model;

import java.util.List;

public class Request {
    private  String name;
    private String contact;
    private String total;
    private String status;
    private String ProductId;
    private String ProductName;
    private String UnitPrice;
    private String Quantity;
    private String totalOrderPrice;
    private String txtLocationResult;
    private String key;
    private String userIdkey;
    private List<Order> foods;

    public Request() {
    }
    public Request(String name, String contact,String total,String productName, String unitPrice,
                   String quantity, String totalOrderPrice,String txtLocationResult,
                   String key, String userIdkey, String status) {

        this.name = name;
        this.contact = contact;
        this.total = total;
        this.ProductName = productName;
        this.UnitPrice = unitPrice;
        this.Quantity = quantity;
        this.totalOrderPrice = totalOrderPrice;
        this.txtLocationResult=txtLocationResult;
        this.key=key;
        this.status = status;
        this.userIdkey = userIdkey;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String address) {
        this.contact = address;
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

    public String getTxtLocationResult() {
        return txtLocationResult;
    }

    public void setTxtLocationResult(String txtLocationResult) {
        this.txtLocationResult = txtLocationResult;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserIdkey() {
        return userIdkey;
    }

    public void setUserIdkey(String userIdkey) {
        this.userIdkey = userIdkey;
    }
}
