package com.github.fabiomagalhaes.pedidoslanchonete.database;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.Food;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.Ingredient;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;

import java.util.List;

public interface IDatabase {
    LoggedUser insertUser(String pNome, String sNome, String senha, boolean adm);
    boolean hasUsers();
    LoggedUser checkUserAndPass(String pName, String pass);
    LoggedUser getUser(String nameOrId);
    List<Ingredient> getIngredients();
    Ingredient getIngredient(long id);
    List<Food> getAllFoods();
}
