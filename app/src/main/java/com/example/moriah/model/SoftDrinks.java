package com.example.moriah.model;

public class SoftDrinks {
    private String Name;
    private String Image;
    private String MenuId;
    private String Price;
    private String Description;

    public SoftDrinks() {
    }

    public SoftDrinks(String name, String image, String menuId, String price, String description) {
        Name = name;
        Image = image;
        MenuId = menuId;
        Price = price;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
