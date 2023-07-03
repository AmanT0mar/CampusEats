package com.example.campuseats.viewmodel;

public class CartItem {
    private String Name;
    private String Price;
    private String Quantity;
    private String Id;

    public CartItem() { }

    public CartItem(String id,String name,String price,String quantity){
        Id = id;
        Name = name;
        Price = price;
        Quantity = quantity;
    }

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
        Id = Id;
    }
    public void setName(String Name)
    {
        Name = Name;
    }

    public String getPrice()
    {
        return Price;
    }
    public void setPrice(String Price)
    {
        Price = Price;
    }

    public String getQuantity() {return Quantity;}
    public void setQuantity() {Quantity = Quantity;}

}
