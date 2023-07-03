package com.example.campuseats;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private String Name;
    private String Price;
    private String Rating;
    private String ImageURL;
    private String Id;
    public FoodItem() {}
    public String getName()
    {
        return Name;
    }
    public String getId()
    {
        return Id;
    }
    public void setId(String Id)
    {
        this.Id = Id;
    }
    public void setName(String Name)
    {
        this.Name = Name;
    }
    public String getPrice()
    {
        return Price;
    }
    public void setPrice(String Price)
    {
        this.Price = Price;
    }
    public String getRating()
    {
        return Rating;
    }
    public void setRating(String Rating)
    {
        this.Rating = Rating;
    }

    public String getImageURL()
    {
        return ImageURL;
    }
    public void setImageURL(String ImageURL)
    {
        this.ImageURL = ImageURL;
    }
}
