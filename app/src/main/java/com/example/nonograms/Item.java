package com.example.nonograms;

public class Item {
    public String id, name, picture;
    public Item() {}
    public Item(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPicture(){
        return picture;
    }
}
