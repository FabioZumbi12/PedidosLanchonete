package com.github.fabiomagalhaes.pedidoslanchonete.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoggedUser {
    public LoggedUser(int _id, String _name, String _sName, boolean adm){
        id = _id;
        name = _name;
        lastName = _sName;
        isAdm = adm;
        selectedFoods = FXCollections.observableArrayList();
    }

    public int id;
    public String name;
    public String lastName;
    public boolean isAdm;

    public ObservableList<Food> selectedFoods;

}
