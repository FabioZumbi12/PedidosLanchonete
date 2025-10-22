module com.github.fabiomagalhaes.pedidoslanchonete {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;
    requires org.json;

    opens com.github.fabiomagalhaes.pedidoslanchonete to javafx.fxml;
    exports com.github.fabiomagalhaes.pedidoslanchonete;
    exports com.github.fabiomagalhaes.pedidoslanchonete.controllers;
    opens com.github.fabiomagalhaes.pedidoslanchonete.controllers to javafx.fxml;
    exports com.github.fabiomagalhaes.pedidoslanchonete.views;
    opens com.github.fabiomagalhaes.pedidoslanchonete.views to javafx.fxml;
    exports com.github.fabiomagalhaes.pedidoslanchonete.entities;
    opens com.github.fabiomagalhaes.pedidoslanchonete.entities to javafx.fxml;
    exports com.github.fabiomagalhaes.pedidoslanchonete.util;
    opens com.github.fabiomagalhaes.pedidoslanchonete.util to javafx.fxml;
    exports com.github.fabiomagalhaes.pedidoslanchonete.database;
    opens com.github.fabiomagalhaes.pedidoslanchonete.database to javafx.fxml;
}