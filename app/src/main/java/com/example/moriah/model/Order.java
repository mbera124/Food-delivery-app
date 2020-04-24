package com.example.moriah.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String ProductId;
    private String ProductName;
    private String UnitPrice;
    private String Quantity;
    private String totalOrderPrice;
    private String key;

    public static List<Order> orderList = new ArrayList<>();

    public Order( ) {
    }

    public Order(String productName, String unitPrice, String quantity, String totalOrderPrice) {
       this.ProductName = productName;
        this.UnitPrice = unitPrice;
        this.Quantity = quantity;
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
       this.ProductId = productId;
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
        this.UnitPrice = unitPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        this.Quantity = quantity;
    }

    public String getTotalPrice() {
        return totalOrderPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalOrderPrice = totalPrice;
    }

    public static List<Order> getOrderList() {
        return orderList;
    }

    public static void setOrderList(List<Order> orderList) {
        Order.orderList = orderList;
    }

    public static void addOrder(Order order){
        orderList.add(order);

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
