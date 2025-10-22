package com.github.fabiomagalhaes.pedidoslanchonete.controllers;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.Food;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.Ingredient;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;

import com.github.fabiomagalhaes.pedidoslanchonete.util.Helper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.getMainDB;
import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.getUser;
import static com.github.fabiomagalhaes.pedidoslanchonete.util.Helper.*;
import static com.github.fabiomagalhaes.pedidoslanchonete.util.Helper.formatPrice;
import static com.github.fabiomagalhaes.pedidoslanchonete.util.MainHelper.tagsIngredients;

public class MainController {
    public Label lblUsername;
    public ListView<Food> lstCardapio;
    public Pane pCardapio;
    public Pane pEditar;
    public ImageView imgFood;
    public Label lblFoodName;
    public Label lblValorLanche;
    public Label lblHeader;
    public ListView<Ingredient> lstIngredAdicional;
    public ListView<Ingredient> lstIngredientes;
    public Label lblErrorIngredients;
    public TextField inputQuantity;
    public Label lblAdditionals;
    public Pane pFinish;
    public Label lblIngredients;
    public Circle circleCart;
    public Label lblCartNum;
    public ListView<Food> lstFinish;
    private final LoggedUser loggedUser;
    public FlowPane flwIngredients;
    public ImageView iconCart;
    public ImageView bgImage;
    public AnchorPane rootPane;
    public Label lblTotalFinish;
    public Label lblItemsFinish;
    public Label lblAdicionaisFinish;

    private Food editingFood;

    public MainController(){
        loggedUser = getUser();
    }

    @FXML
    public void initialize() {
        /*
        Setup inicial
         */
        lblUsername.setText("Bem vindo(a), " + loggedUser.name + " " + loggedUser.lastName);
        Tooltip tooltip = new Tooltip("Revisar pedido");
        Tooltip.install(iconCart, tooltip);

        bgImage.fitWidthProperty().bind(rootPane.widthProperty());
        bgImage.fitHeightProperty().bind(rootPane.heightProperty());

        pCardapio.setVisible(true);
        pEditar.setVisible(false);
        pFinish.setVisible(false);

        /* Restante dos objetos */
        lstCardapio.setItems(FXCollections.observableArrayList(getMainDB().getAllFoods()));
        lstCardapio.addEventFilter(ScrollEvent.SCROLL, e -> {
            ScrollBar bar = (ScrollBar) lstCardapio.lookup(".scroll-bar:vertical");
            if (bar != null) {
                double delta = -e.getDeltaY() / 5000.0;
                bar.setValue(Math.min(Math.max(bar.getValue() + delta, 0), 1));
                e.consume();
            }
        });
        lstCardapio.setCellFactory(lv -> new ListCell<Food>() {
            private final HBox hbox = new HBox(10);
            private final ImageView icon = new ImageView();
            private final VBox vbox = new VBox(2);

            final Label nameLabel = new Label();
            final Label priceLabel = new Label();
            final Label ingredientsLabel = new Label();

            private final Button chooseButton = new Button("Escolher");

            {
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                vbox.getChildren().addAll(nameLabel, priceLabel, ingredientsLabel/*, typeLabel*/);
                hbox.getChildren().addAll(icon, vbox, spacer, chooseButton);

                chooseButton.setVisible(false);
                chooseButton.setOnAction(e -> {
                    Food selectedFood = getItem();
                    if (selectedFood != null) {
                        SetupEditFood(selectedFood);
                    }
                });
                chooseButton.setStyle(
                        "-fx-background-color: #F57627;" +
                                "-fx-font-size: 26px;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 8;" +
                                "-fx-padding: 5 15 5 15;"
                );

                selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    chooseButton.setVisible(isNowSelected);
                });
            }


            @Override
            protected void updateItem(Food food, boolean empty) {
                super.updateItem(food, empty);
                if (empty || food == null) {
                    setGraphic(null);
                } else {
                    String imagePath = GetImageType(food.getFoodType());
                    Image img = new Image(getClass().getResourceAsStream(imagePath));
                    icon.setImage(img);
                    icon.setFitWidth(110);
                    icon.setFitHeight(110);

                    nameLabel.setText(food.getFoodName());
                    nameLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

                    priceLabel.setText("Preço: R$" + String.format("%.2f", food.getFoodPrice()));
                    priceLabel.setStyle("-fx-font-size: 28px;");

                    ingredientsLabel.setText("Ingredientes: " + String.join(", ", food.getFoodIngredients()));
                    ingredientsLabel.setStyle("-fx-font-size: 20px;");

                    setGraphic(hbox);
                }
            }
        });

        // Setup lista finish (lista observável)
        lstFinish.setItems(loggedUser.selectedFoods);
        lstFinish.setCellFactory(lv -> new ListCell<Food>() {
            private final HBox hbox = new HBox(5);
            private final ImageView icon = new ImageView();
            private final VBox vbox = new VBox(-5);

            final Label nameLabel = new Label();
            final Label amountLabel = new Label();
            final Label priceLabel = new Label();
            final Label ingredientsLabel = new Label();

            private final Button editButton = new Button("Editar");
            private final Button deleteButton = new Button("X");

            {
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                vbox.getChildren().addAll(nameLabel, priceLabel, ingredientsLabel);
                hbox.getChildren().addAll(amountLabel, icon, vbox, spacer, editButton, deleteButton);

                HBox.setMargin(editButton, new Insets(5, 0, 0, 0)); // top, right, bottom, left
                HBox.setMargin(deleteButton, new Insets(5, 0, 0, 0));
                HBox.setMargin(amountLabel, new Insets(15, 0, 0, 0));


                editButton.setOnAction(e -> {
                    Food selectedFood = getItem();
                    if (selectedFood != null) {
                        loggedUser.selectedFoods.remove(selectedFood);
                        SetupEditFood(selectedFood);
                    }
                });
                deleteButton.setOnAction(e -> {
                    Food selectedFood = getItem();
                    if (selectedFood != null) {
                        loggedUser.selectedFoods.remove(selectedFood);
                    }
                    checkCart();
                });
                editButton.setStyle("-fx-background-color: #F57627;" +
                        "-fx-font-size: 22px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-min-width: 80px;" +
                        "-fx-padding: 2px;");
                deleteButton.setStyle("-fx-background-color: red;" +
                        "-fx-font-size: 22px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-min-width: 30px;" +
                        "-fx-padding: 2px;");
            }

            @Override
            protected void updateItem(Food food, boolean empty) {
                super.updateItem(food, empty);
                if (empty || food == null) {
                    setGraphic(null);
                } else {
                    String imagePath = GetImageType(food.getFoodType());
                    Image img = new Image(getClass().getResourceAsStream(imagePath));
                    icon.setImage(img);
                    icon.setFitWidth(50);
                    icon.setFitHeight(50);

                    amountLabel.setText(food.getAmount() + "x");
                    amountLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

                    nameLabel.setText(food.getFoodName());
                    nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                    priceLabel.setText("Preço: " + formatPrice(food.getFoodPrice()) + " - Adic.: " + formatPrice(food.getAdditionalIngredientsPrice()));
                    priceLabel.setStyle("-fx-font-size: 14px;");

                    if (!food.getAdditionalIngredients().isEmpty()) {
                        ingredientsLabel.setText("Adic.: " + String.join(", ", food.getAdditionalIngredients().stream().map(Ingredient::getName).toArray(String[]::new)));
                        ingredientsLabel.setStyle("-fx-font-size: 12px;");
                        ingredientsLabel.setVisible(true);
                    } else {
                        ingredientsLabel.setVisible(false);
                    }

                    setGraphic(hbox);
                }
            }
        });
    }

    private void SetupEditFood(Food food) {
        editingFood = food.clone();

        pCardapio.setVisible(false);
        pEditar.setVisible(true);
        pFinish.setVisible(false);

        lblHeader.setText("Personalizar Pedido");
        lblErrorIngredients.setVisible(false);
        lblAdditionals.setVisible(false);

        Image img = new Image(getClass().getResourceAsStream(GetImageType(editingFood.getFoodType())));
        imgFood.setImage(img);

        tagsIngredients(editingFood, flwIngredients);

        lblFoodName.setText(editingFood.getFoodName());
        lblValorLanche.setText("Preço: " + formatPrice(editingFood.getFoodPrice()));
        inputQuantity.setText(String.valueOf(editingFood.getAmount()));

        lstIngredientes.getItems().clear();

        if (editingFood.getFoodType().equals(Helper.FoodType.LANCHE)) {
            lstIngredientes.setDisable(false);
            lstIngredAdicional.setDisable(false);

            lstIngredientes.setItems(FXCollections.observableArrayList(editingFood.getAdditionalIngredients()));
            lstIngredientes.setCellFactory(lv -> new ListCell<>() {
                private final Label label = new Label();
                private final Button deleteButton = new Button("Excluir");
                private final HBox container = new HBox();

                {
                    container.setSpacing(10);
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    container.getChildren().addAll(label, spacer, deleteButton);

                    label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
                    deleteButton.setCursor(Cursor.HAND);
                    deleteButton.setStyle("-fx-background-color: red; " +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;" +
                            "-fx-font-size: 16px;"
                    );

                    deleteButton.setOnAction(event -> {
                        Ingredient item = getItem();
                        lstIngredientes.getItems().remove(item);
                        lblErrorIngredients.setVisible(false);
                        checkAdditional();
                    });
                    container.setAlignment(Pos.CENTER_LEFT);
                }

                @Override
                protected void updateItem(Ingredient item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        label.setText(item.getName() + " (" + formatPrice(item.getPrice()) + ")");
                        setGraphic(container);
                    }
                }
            });

            lstIngredAdicional.setItems(FXCollections.observableArrayList(getMainDB().getIngredients()));
            lstIngredAdicional.setCellFactory(lv -> new ListCell<>() {
                private final Label label = new Label();
                private final Button addButton = new Button("Adicionar");
                private final HBox container = new HBox();

                {
                    container.setSpacing(10);
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    container.getChildren().addAll(label, spacer, addButton);

                    label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
                    addButton.setCursor(Cursor.HAND);
                    addButton.setStyle("-fx-background-color: #F57627; " +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;" +
                            "-fx-font-size: 16px;"
                    );

                    addButton.setOnAction(event -> {
                        Ingredient item = getItem();
                        if (lstIngredientes.getItems().size() > 9) {
                            lblErrorIngredients.setText("Não é possivel adicionar mais de 10 itens");
                            lblErrorIngredients.setVisible(true);
                        } else {
                            lblErrorIngredients.setVisible(false);
                            lstIngredientes.getItems().add(item);
                        }
                        checkAdditional();
                    });
                    container.setAlignment(Pos.CENTER_LEFT);
                }

                @Override
                protected void updateItem(Ingredient item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        label.setText(item.getName() + " (" + formatPrice(item.getPrice()) + ")");
                        setGraphic(container);
                    }
                }
            });
        } else {
            lstIngredientes.setDisable(true);
            lstIngredAdicional.setDisable(true);
        }
        checkCart();
    }

    private void checkAdditional() {
        editingFood.setAdditionalIngredients(lstIngredientes.getItems().stream().toList());
        if (!editingFood.getAdditionalIngredients().isEmpty()) {
            lblAdditionals.setVisible(true);
            lblAdditionals.setText("+ " + formatPrice(editingFood.getAdditionalIngredientsPrice()));
        } else {
            lblAdditionals.setVisible(false);
        }
    }

    public void btnAddAction(ActionEvent actionEvent) {
        int amount = Integer.parseInt(inputQuantity.getText()) + 1;
        inputQuantity.setText(String.valueOf(amount));
        editingFood.setAmount(amount);
    }

    public void btnRemoveAction(ActionEvent actionEvent) {
        if (Integer.parseInt(inputQuantity.getText()) > 1){
            int amount = Integer.parseInt(inputQuantity.getText()) - 1;
            inputQuantity.setText(String.valueOf(amount));
            editingFood.setAmount(amount);
        }
    }

    public void btnNextAction(ActionEvent actionEvent) {
        checkCart();

        loggedUser.selectedFoods.add(editingFood);
        editingFood = null;

        checkCart();
        setupFinish();
    }

    public void btnAddMoreAction(ActionEvent actionEvent) {
        loggedUser.selectedFoods.add(editingFood);
        editingFood = null;
        pCardapio.setVisible(true);
        pEditar.setVisible(false);
        lblHeader.setText("Nosso Cardápio");
        checkCart();
    }

    private void checkCart() {
        if (!loggedUser.selectedFoods.isEmpty()) {
            lblCartNum.setText(String.valueOf(loggedUser.selectedFoods.size()));
            lblCartNum.setVisible(true);
            circleCart.setVisible(true);
        } else {
            lblCartNum.setVisible(false);
            circleCart.setVisible(false);
        }
    }

    public void btnCancelAction(ActionEvent actionEvent) {
        cancelCart(true);
    }

    private void cancelCart(boolean clear) {
        if (clear){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja continuar?");
            alert.setContentText("Esta ação vai limpar seus pedidos escolhidos anteriormente e voltar para o cardápio!\n\nClique em Continuar para prosseguir ou Cancelar para abortar.");

            ButtonType botaoContinuar = new ButtonType("Continuar");
            ButtonType botaoCancelar = new ButtonType("Cancelar");

            alert.getButtonTypes().setAll(botaoContinuar, botaoCancelar);
            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.isPresent() && resultado.get() == botaoContinuar) {
                loggedUser.selectedFoods.clear();
                editingFood = null;
            }
        }
        pCardapio.setVisible(true);
        pEditar.setVisible(false);
        pFinish.setVisible(false);
        lblHeader.setText("Nosso Cardápio");
        checkCart();
    }

    private void setupFinish() {
        lblHeader.setText("Revisar Pedido");

        double total = 0;
        int itens = 0;
        double adicionais = 0;
        for (Food food : loggedUser.selectedFoods){
            itens += food.getAmount();
            adicionais += food.getAdditionalIngredientsPrice();
            total += (food.getAmount() * food.getFoodPrice()) + food.getAdditionalIngredientsPrice();
        }

        lblTotalFinish.setText("Total: "+formatPrice(total));
        lblItemsFinish.setText("Itens: x" + itens);
        lblAdicionaisFinish.setText("Adicionais: "+formatPrice(adicionais));

        pCardapio.setVisible(false);
        pEditar.setVisible(false);
        pFinish.setVisible(true);
    }

    public void btnAddMaisFinishAction(ActionEvent actionEvent) {
        pCardapio.setVisible(true);
        pEditar.setVisible(false);
        pFinish.setVisible(false);
        lblHeader.setText("Nosso Cardápio");
    }

    public void btnCancelFinishAction(ActionEvent actionEvent) {
        cancelCart(true);
    }

    public void btnFinishAction(ActionEvent actionEvent) {
    }

    public void onMouseClickCart(MouseEvent mouseEvent) {
        if (!loggedUser.selectedFoods.isEmpty())
            setupFinish();
    }
}
