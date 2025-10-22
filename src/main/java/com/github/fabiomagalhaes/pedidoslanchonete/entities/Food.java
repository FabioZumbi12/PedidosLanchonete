package com.github.fabiomagalhaes.pedidoslanchonete.entities;

import com.github.fabiomagalhaes.pedidoslanchonete.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class Food implements Cloneable {

    private final long id;
    private final String foodName;
    private final Helper.FoodType foodType;

    private double foodPrice;
    private final List<String> foodIngredients;
    private List<Ingredient> additionalIngredients;
    private int amount;

    public Food(long id, String foodName, double foodPrice, List<String> foodIngredients, Helper.FoodType foodType) {
        this.id = id;
        this.foodPrice = foodPrice;
        this.foodIngredients = foodIngredients;
        this.foodName = foodName;
        this.foodType = foodType;
        this.additionalIngredients = new ArrayList<>();
        this.amount = 1;
    }

    public long getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double price){
        this.foodPrice = price;
    }

    public List<String> getFoodIngredients() {
        return foodIngredients;
    }

    public Helper.FoodType getFoodType() {
        return foodType;
    }

    public List<Ingredient> getAdditionalIngredients() {
        return additionalIngredients;
    }

    public void setAdditionalIngredients(List<Ingredient> additionalIngredients) {
        this.additionalIngredients = additionalIngredients;
    }

    public double getAdditionalIngredientsPrice() {
        double price = 0;
        for (Ingredient ingredient : this.additionalIngredients) {
            price += ingredient.getPrice();
        }
        return price;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    @Override
    public Food clone() {
        try {
            return (Food) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
