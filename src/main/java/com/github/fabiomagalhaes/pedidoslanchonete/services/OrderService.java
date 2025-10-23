package com.github.fabiomagalhaes.pedidoslanchonete.services;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.Food;
import com.github.fabiomagalhaes.pedidoslanchonete.util.Helper;

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

}
