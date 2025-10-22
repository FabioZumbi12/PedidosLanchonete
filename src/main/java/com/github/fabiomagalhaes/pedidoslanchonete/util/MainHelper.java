package com.github.fabiomagalhaes.pedidoslanchonete.util;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.Food;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class MainHelper {

    public static void tagsIngredients(Food editingFood, FlowPane container) {
        container.getChildren().clear();

        for (String ingredient : editingFood.getFoodIngredients()) {
            Label lbl = new Label(ingredient);
            lbl.setStyle("-fx-padding: 2 7 2 7; -fx-background-color: #F57627; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-size: 24px; -fx-text-fill: white;");

            Button btnRemove = new Button("x");
            Tooltip tooltip = new Tooltip("Remover ingrediente");
            Tooltip.install(btnRemove, tooltip);
            btnRemove.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand; -fx-font-size: 26px; -fx-padding: 0 6 0 0;");
            btnRemove.setOnAction(e -> {
                editingFood.getFoodIngredients().remove(ingredient);
                tagsIngredients(editingFood, container);
            });

            HBox tag;
            if (ingredient.equals("Pão") || ingredient.equals("Massa de Pastel") || !editingFood.getFoodType().equals(Helper.FoodType.LANCHE)){
                tag = new HBox(lbl);
            } else {
                tag = new HBox(lbl, btnRemove);
            }

            tag.setAlignment(Pos.CENTER);
            tag.setSpacing(5);
            tag.setStyle("-fx-background-color: #F0F0F0; -fx-border-radius: 5; -fx-background-radius: 5;");

            container.getChildren().add(tag);
        }
    }
}
