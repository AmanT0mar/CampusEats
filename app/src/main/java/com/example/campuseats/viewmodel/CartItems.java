package com.example.campuseats.viewmodel;

public class CartItems {
    private String Name;
    private String Price;
    private String Quantity;
    private String Id;

    public CartItems() { }

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

    public String getQuantity() {return Quantity;}
    public void setQuantity(String Quantity) {this.Quantity = Quantity;}
}
