package com.github.fabiomagalhaes.pedidoslanchonete.services;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.Food;
import com.github.fabiomagalhaes.pedidoslanchonete.util.Helper;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    OrderService orderService = new OrderService();

    @Test
    void orderDiscountApply() {
        Food xSalada = new Food(1, "X-Salada", 30.0, List.of("Pão", "Carne"), Helper.FoodType.LANCHE);
        Food batata = new Food(1, "Batata Frita", 25.0, List.of("Batata"), Helper.FoodType.PORCAO);

        var resumo = orderService.calcularResumoPedido(FXCollections.observableArrayList(xSalada, batata));

        assertEquals(55.0, resumo.subTotal, 0.01);
        assertEquals(-1.6499999999999986, resumo.desc, 0.01); // 3% de 55 = 1.65
        assertEquals(53.35, resumo.total, 0.01);
    }
}