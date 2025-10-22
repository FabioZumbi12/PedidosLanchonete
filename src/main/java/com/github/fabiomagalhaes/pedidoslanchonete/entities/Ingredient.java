package com.github.fabiomagalhaes.pedidoslanchonete.entities;

public class Ingredient {
    private final long  id;
    private final String name;
    private double value;

    public Ingredient(long  id, String name, double price) {
        this.id = id;
        this.name = name;
        this.value = price;
    }

    public long  getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return value;
    }

    public void setPrice(double price){
        this.value = price;
    }
}
