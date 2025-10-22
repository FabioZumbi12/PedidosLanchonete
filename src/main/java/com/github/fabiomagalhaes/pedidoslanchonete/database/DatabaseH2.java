package com.github.fabiomagalhaes.pedidoslanchonete.database;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.Food;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.Ingredient;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;
import com.github.fabiomagalhaes.pedidoslanchonete.util.Helper;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseH2 implements IDatabase {
    private final String dataUrl = "jdbc:h2:./data/database";
    private final String username = "";
    private final String password = "";

    public DatabaseH2() {

        // Create tables
        createUserTable();
        setupTable("FOOD", "setupFood");
        setupTable( "INGREDIENT", "setupIngr");
    }

    private void setupTable(String tableName, String dataFile) {
        try (Connection conn = DriverManager.getConnection(dataUrl, username, password);
             Statement stmt = conn.createStatement()) {

            if (tableNotExists(conn, tableName)){
                try (InputStream is = getClass().getResourceAsStream("/setup/" + dataFile + ".sql")) {
                    if (is == null) {
                        System.out.println("Arquivo SQL não encontrado: " + dataFile + ".sql");
                        return;
                    }
                    String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                    for (String command : sql.split(";")) {
                        if (command.trim().isEmpty()) continue;
                        stmt.execute(command);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean tableNotExists(Connection connection, String tableName) {
        boolean notExists = true;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet resultSet = metaData.getTables(null, null, tableName.toUpperCase(), null)) {
                if (resultSet.next()) {
                    notExists = false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return notExists;
    }

    private void createUserTable() {
        try (Connection connection = DriverManager.getConnection(dataUrl, username, password);
             Statement statement = connection.createStatement()) {
            if (tableNotExists(connection, "PUSERS")){
                statement.executeUpdate("CREATE TABLE PUSERS (ID INT AUTO_INCREMENT PRIMARY KEY, PNAME VARCHAR(100), SNAME VARCHAR(100), PASSWORD VARCHAR(100), ADM BOOLEAN)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LoggedUser insertUser(String pNome, String sNome, String senha, boolean adm) {
        try (Connection connection = DriverManager.getConnection(dataUrl, username, password);
             PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM PUSERS WHERE PNAME = ?");
             PreparedStatement pStatement = connection.prepareStatement("INSERT INTO PUSERS (PNAME, SNAME, PASSWORD, ADM) VALUES (?, ?, ?, ?)")) {

            checkStatement.setString(1, pNome);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getInt(1) > 0)
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Registro de Usuário");
                        alert.setContentText("Este nome de usuário ja existe!");
                        alert.showAndWait();
                        return null;
                    }
                }
            }

            pStatement.setString(1, pNome);
            pStatement.setString(2, sNome);
            pStatement.setString(3, senha);
            pStatement.setBoolean(4, adm);

            pStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro de Usuário");
            alert.setContentText("Usuário adicionado com sucesso!");
            alert.showAndWait();

            return getUser(pNome);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean hasUsers() {
        try (Connection connection = DriverManager.getConnection(dataUrl, username, password);
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM PUSERS")) {
                if (resultSet.next()) {
                    int rowCount = resultSet.getInt(1);
                    return rowCount > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public LoggedUser checkUserAndPass(String pName, String pass) {
        try (Connection connection = DriverManager.getConnection(dataUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT ID,PNAME,SNAME,ADM FROM PUSERS WHERE PNAME = ? AND PASSWORD = ?")) {
            statement.setString(1, pName);
            statement.setString(2, pass);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getInt(1) > 0){
                        return new LoggedUser(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LoggedUser getUser(String nameOrId) {
        try (Connection connection = DriverManager.getConnection(dataUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT ID,PNAME,SNAME,ADM FROM PUSERS WHERE PNAME = ? OR ID = ?")) {
            statement.setString(1, nameOrId);
            statement.setString(2, nameOrId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getInt(1) > 0){
                        return new LoggedUser(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ingredient> getIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dataUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID, NAME, PRICE FROM INGREDIENT")) {

            while (rs.next()) {
                ingredients.add(new Ingredient(
                        rs.getLong("ID"),
                        rs.getString("NAME"),
                        rs.getDouble("PRICE")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    public Ingredient getIngredient(long id){
        Ingredient ingredient = null;

        String sql = "SELECT NAME, PRICE FROM INGREDIENT WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(dataUrl, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ingredient = new Ingredient(
                            rs.getLong("ID"),
                            rs.getString("NAME"),
                            rs.getDouble("PRICE")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredient;
    }

    public List<Food> getAllFoods() {
        List<Food> foods = new ArrayList<>();

        String sql = "SELECT ID, NAME, PRICE, TYPE, INGREDIENTS FROM FOOD";

        try (Connection conn = DriverManager.getConnection(dataUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("NAME");
                double price = rs.getDouble("PRICE");
                Helper.FoodType type = Helper.FoodType.valueOf(rs.getString("TYPE"));

                // Converte a string de ingredientes em lista de strings
                String ingredientsStr = rs.getString("INGREDIENTS");
                List<String> ingredients = new ArrayList<>();
                if (ingredientsStr != null && !ingredientsStr.isEmpty()) {
                    ingredients = Arrays.asList(ingredientsStr.split(","));
                }

                Food food = new Food(id, name, price, FXCollections.observableArrayList(ingredients), type);
                foods.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foods;
    }
}
