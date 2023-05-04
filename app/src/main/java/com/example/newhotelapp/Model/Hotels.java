package com.example.newhotelapp.Model;

public class Hotels {

    String name,id,description,category,image;

    Integer price;

    Boolean available;

    public Hotels(){}

    public String getName(){ return name;}
    public void setName(String name){ this.name = name; }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}

    public String getImage(){return image;}
    public void setImage(String image){this.image = image;}

    public Integer getPrice(){return price;}
    public void setPrice(Integer price){this.price = price;}

    public Boolean getAvailable(){return available;}
    public void setAvailable(Boolean available){this.available = available;}

}
