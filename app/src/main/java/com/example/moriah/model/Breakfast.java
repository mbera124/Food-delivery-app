package com.example.moriah.model;

public class Breakfast {
   private String Name;
   private String Image;
    private String MenuId;
   private String Price;
   private String Description;

    public Breakfast() {
    }

    public Breakfast(String name, String image, String price, String description, String menuId ) {
        Name = name;
        Image = image;
        Price=price;
        Description=description;
        MenuId=menuId;
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

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
