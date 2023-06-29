package com.example.campuseats;

public class FoodItem {
    private String Name;
    private String Price;
    private String Rating;
    private String ImageURL;
    public FoodItem() {}
    public String getName()
    {
        return Name;
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
