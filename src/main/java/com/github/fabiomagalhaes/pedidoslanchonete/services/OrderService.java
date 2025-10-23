package com.github.fabiomagalhaes.pedidoslanchonete.services;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.Food;
import com.github.fabiomagalhaes.pedidoslanchonete.util.Helper;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Optional;

public class OrderService {

    public OrderSummary calcularResumoPedido(List<Food> foods) {
        OrderSummary resumo = new OrderSummary();

        // Limpar itens gratis
        foods.removeIf(f -> f.getFoodPrice() == 0);

        // Checar se tem X-Salada e Batata frita
        if (foods.stream().anyMatch(f -> f.getFoodName().equals("X-Salada")) &&
                foods.stream().anyMatch(f -> f.getFoodName().equals("Batata Frita"))
        ){
            Optional<Food> refri = foods.stream().filter(f -> f.getFoodName().equals("Refrigerante")).findFirst();
            if (refri.isPresent()){
                if (foods.stream().noneMatch(f ->
                        f.getFoodName().equals("Refrigerante") &&
                        f.getFoodPrice() == 0)){
                    foods.add(new Food(1,"Refrigerante", 0, List.of("Coca-Cola"), Helper.FoodType.BEBIDA));
                }
            } else {
                foods.add(new Food(1,"Refrigerante", 0, List.of("Coca-Cola"), Helper.FoodType.BEBIDA));
            }
        }

        for (Food food : foods){
            resumo.itens += food.getAmount();
            resumo.totalLanches += food.getAmount() * food.getFoodPrice();
            resumo.adicionais += food.getAmount() * food.getAdditionalIngredientsPrice();
            resumo.total += food.getAmount() * (food.getFoodPrice() + food.getAdditionalIngredientsPrice());
            resumo.subTotal = resumo.total;
        }

        // 3% de desconto se o valor total do pedido for acima de R$50,00
        if (resumo.total > 50){
            double descVl = resumo.total - (resumo.total * ((double) 3 /100));
            resumo.desc = descVl - resumo.total;
            resumo.total = descVl;
        }
        return resumo;
    }

    public static class OrderSummary {
        public double total;
        public int itens;
        public double totalLanches;
        public double adicionais;
        public double desc;
        public double subTotal;
    }

    public void tagsIngredients(Food editingFood, FlowPane container) {
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
