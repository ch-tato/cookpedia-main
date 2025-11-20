package com.cookpedia.model;

public class DetailIngredient {
    private String name;
    private String unit;
    private String quantity;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getUnit(){
        return unit;
    }
    public void setUnit(String unit){
        this.unit=unit;
    }
    public String getQuantity(){
        return quantity;
    }
    public void setQuantity(String quantity){
        this.quantity=quantity;
    }
}
