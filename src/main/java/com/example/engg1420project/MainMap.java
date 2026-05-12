package com.example.engg1420project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MainMap extends Application {

    public static final int GRID_SIZE = 10;

    private static final int CELL_SIZE = 75;

    private static final int WINDOW_WIDTH = 1000;

    private static final int WINDOW_HEIGHT = 800;

    private static final int STATUS_HEIGHT = 220;

    private TreasureHouse[] treasureHouses = new TreasureHouse[8];

    private Player player1;
    private Player player2;

    private GameSession session;

    private Scoreboard scoreboard;

    private BorderPane root;

    public static int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }

    @Override
    public void start(Stage stage) {

        Image backgroundImage =
                new Image(
                        getClass()
                                .getResource("/images/map.png")
                                .toString()
                );

        ImageView backgroundView =
                new ImageView(backgroundImage);

        root = new BorderPane();

        StackPane mapPane = new StackPane();

        mapPane.getChildren().add(backgroundView);

backgroundView.setFitWidth(GRID_SIZE * CELL_SIZE);

backgroundView.setFitHeight(GRID_SIZE * CELL_SIZE);

backgroundView.setPreserveRatio(false);

        mapPane.setOnMouseClicked(
                event -> mapPane.requestFocus()
        );

        GridPane grid = createGrid();

        // Proper map sizing
mapPane.setMinSize(
        GRID_SIZE * CELL_SIZE,
        GRID_SIZE * CELL_SIZE
);

mapPane.setMaxSize(
        GRID_SIZE * CELL_SIZE,
        GRID_SIZE * CELL_SIZE
);

        mapPane.getChildren().add(grid);

        BorderPane.setAlignment(mapPane, Pos.CENTER);

        root.setCenter(mapPane);

        BorderPane.setAlignment(mapPane, Pos.CENTER);

mapPane.setTranslateY(10);

        scoreboard = new Scoreboard();

        treasureHouses[0] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Diamond Ring",
                        20
                );

        treasureHouses[1] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Jewel Encrusted Sword",
                        30
                );

        treasureHouses[2] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Golden Goblet",
                        15
                );

        treasureHouses[3] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Crystal Goblets",
                        12
                );

        treasureHouses[4] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Wooden Bow",
                        5
                );

        treasureHouses[5] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Paladin's Shield",
                        10
                );

        treasureHouses[6] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Golden Key",
                        17
                );

        treasureHouses[7] =
                TreasureHouse.spawnTreasure(
                        grid,
                        "Dragon Scroll",
                        25
                );

        Random random = new Random();

        for (int i = 0; i < random.nextInt(5); i++) {
            Walls.spawnWalls(grid);
        }

        // Landmark
        Image landmarkImage =
                new Image(
                        getClass()
                                .getResource("/images/landmark.png")
                                .toString()
                );

        ImageView landmarkImageView =
                new ImageView(landmarkImage);

        landmarkImageView.setFitWidth(CELL_SIZE);
        landmarkImageView.setFitHeight(CELL_SIZE);

        grid.add(landmarkImageView, 9, 9);

        // Players
        player1 = new Player(
                "/images/player.png",
                CELL_SIZE,
                GameRules.STARTING_MONEY,
                GameRules.STARTING_POWER
        );

        player2 = new Player(
                "/images/player.png",
                CELL_SIZE,
                GameRules.STARTING_MONEY,
                GameRules.STARTING_POWER
        );

        player1.setMapLabel("P1");
        player2.setMapLabel("P2");

        session =
                GameSession.createTwoPlayerMapGame();

        CityGraph.City player1StartCity =
                session.getCityGraph()
                        .getCity(
                                session.getPlayers()
                                        .get(0)
                                        .getCurrentCityName()
                        );

        CityGraph.City player2StartCity =
                session.getCityGraph()
                        .getCity(
                                session.getPlayers()
                                        .get(1)
                                        .getCurrentCityName()
                        );

        grid.add(
                player1.getMarker(),
                player1StartCity.getX(),
                player1StartCity.getY()
        );

        grid.add(
                player2.getMarker(),
                player2StartCity.getX(),
                player2StartCity.getY()
        );

        addCityLabels(grid);

        scoreboard = new Scoreboard();

        scoreboard.addPlayer("Player 1");
        scoreboard.addPlayer("Player 2");

        // Labels
        Label scoreboardLabel = new Label("Scoreboard");

        Label playerStatus1Label =
                new Label("Player 1 Status");

        Label playerStatus2Label =
                new Label("Player 2 Status");

        scoreboardLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        scoreboardLabel.setTextFill(Color.WHITE);

        playerStatus1Label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        playerStatus1Label.setTextFill(Color.WHITE);

        playerStatus2Label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        playerStatus2Label.setTextFill(Color.WHITE);

        Scene scene = new Scene(
                root,
                WINDOW_WIDTH,
                WINDOW_HEIGHT + STATUS_HEIGHT
        );

        stage.setTitle("Traveling Salesman Game");

        // RESIZABLE WINDOW
        stage.setResizable(true);

        // Minimum size limits
        stage.setMinWidth(900);
        stage.setMinHeight(700);

        stage.setScene(scene);

        stage.show();

        root.requestFocus();

        // Status labels
        Label turnLabel = new Label();

        Label inventoryLabel = new Label();

        Label marketLabel = new Label();

        Label eventLabel = new Label();
        Label destinationLabel = new Label();

        Button shopButton =
                new Button("Open Shop");

        VBox statusPanel =
                createStatusPanel(
                        turnLabel,
                        inventoryLabel,
                        marketLabel,
                        eventLabel,
                        destinationLabel,
                        shopButton
                );

        root.setTop(statusPanel);

        // Movement
PlayerMovement playerMovement =
        new PlayerMovement(
                player1,
                player2,
                turnLabel,
                destinationLabel,
                inventoryLabel,
                marketLabel,
                eventLabel,
                session
        );

        shopButton.setOnAction(
                e -> openShopDialog(playerMovement)
        );

        shopButton.setFocusTraversable(false);

        scene.setOnKeyPressed(
                event -> playerMovement.handleKeyPress(event)
        );
    }

    private VBox createStatusPanel(
            Label turnLabel,
            Label inventoryLabel,
            Label marketLabel,
            Label eventLabel,
            Label destinationLabel,
            Button shopButton
    ) {

        VBox statusPanel = new VBox(8);

        statusPanel.setPrefHeight(STATUS_HEIGHT);

        statusPanel.setStyle(
                "-fx-background-color: #17202a; -fx-padding: 8;"
        );

        HBox statusColumns = new HBox(14);

        statusColumns.setAlignment(Pos.TOP_LEFT);

        VBox travelBox =
                createStatusBox(
                        "Travel",
                        turnLabel,
                        240
                );

                VBox destinationBox =
        createStatusBox(
                "Destinations",
                destinationLabel,
                320
        );

        VBox inventoryBox =
                createStatusBox(
                        "Inventory",
                        inventoryLabel,
                        350
                );

        inventoryBox.setMinHeight(220);

        VBox marketBox =
                createStatusBox(
                        "Market",
                        marketLabel,
                        250
                );

        shopButton.setMinHeight(40);

        shopButton.setStyle(
                "-fx-background-color: #1abc9c;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-weight: bold;"
        );

statusColumns.getChildren().addAll(
        travelBox,
        destinationBox,
        inventoryBox,
        marketBox,
        shopButton
);

        eventLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        12
                )
        );

        eventLabel.setTextFill(Color.WHITE);

        eventLabel.setWrapText(true);

        eventLabel.setMaxWidth(WINDOW_WIDTH - 24);

        eventLabel.setStyle(
                "-fx-background-color: #263645;"
                        + "-fx-padding: 8;"
                        + "-fx-border-color: #5d6d7e;"
        );

        statusPanel.getChildren().addAll(
                statusColumns,
                eventLabel
        );

        return statusPanel;
    }

    private VBox createStatusBox(
            String title,
            Label contentLabel,
            int width
    ) {

        Label titleLabel = new Label(title);

        titleLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        12
                )
        );

        titleLabel.setTextFill(Color.LIGHTGRAY);

        contentLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        12
                )
        );

        contentLabel.setTextFill(Color.WHITE);

        contentLabel.setWrapText(true);

        contentLabel.setMinHeight(Region.USE_PREF_SIZE);

        VBox box =
                new VBox(
                        4,
                        titleLabel,
                        contentLabel
                );

        box.setPrefWidth(width);

        box.setMinHeight(160);

        box.setStyle(
                "-fx-background-color: #263645;"
                        + "-fx-padding: 8;"
                        + "-fx-border-color: #5d6d7e;"
        );

        return box;
    }

    private void addCityLabels(GridPane grid) {

        for (CityGraph.City city :
                session.getCityGraph().getCities()) {

            Label cityLabel =
                    new Label(
                            abbreviateCityName(city.getName())
                    );

            cityLabel.setFont(
                    Font.font(
                            "Arial",
                            FontWeight.BOLD,
                            8
                    )
            );

            cityLabel.setTextFill(Color.WHITE);

            cityLabel.setWrapText(false);

            cityLabel.setMaxWidth(CELL_SIZE - 8);

            cityLabel.setStyle(
                    "-fx-background-color: rgba(0,0,0,0.65);"
                            + "-fx-padding: 2;"
            );

            GridPane.setHalignment(
                    cityLabel,
                    javafx.geometry.HPos.CENTER
            );

            GridPane.setValignment(
                    cityLabel,
                    javafx.geometry.VPos.TOP
            );

            grid.add(
                    cityLabel,
                    city.getX(),
                    city.getY()
            );
        }
    }

    // KEEP YOUR EXISTING SHOP METHODS HERE
private void openShopDialog(PlayerMovement playerMovement) {

    if (session.hasWon() || session.hasLost()) {

        showInformation(
                "Shop Closed",
                "The game is over and the shop is no longer available."
        );

        root.requestFocus();
        return;
    }

    ChoiceDialog<String> shopChoice =
            new ChoiceDialog<>(
                    "Buy items",
                    "Buy items",
                    "Sell items",
                    "Leave shop"
            );

    shopChoice.setTitle("City Shop");

    shopChoice.setHeaderText(
            "Shop in "
                    + session.getCurrentPlayer().getCurrentCityName()
    );

    shopChoice.setContentText("Choose shop action:");

    Optional<String> shopResult =
            shopChoice.showAndWait();

    if (shopResult.isEmpty()
            || shopResult.get().equals("Leave shop")) {

        root.requestFocus();
        return;
    }

    switch (shopResult.get()) {

        case "Buy items" ->
                showBuyDialog(playerMovement);

        case "Sell items" ->
                showSellDialog(playerMovement);

        default -> {
        }
    }

    root.requestFocus();
}

private String abbreviateCityName(String cityName) {

    return switch (cityName) {

        case "Riverbend" -> "River";
        case "Pinewatch" -> "Pine";
        case "Meadowrun" -> "Meadow";
        case "Northpass" -> "North";
        case "Goldfield" -> "Gold";
        case "Eastcliff" -> "East";
        case "Marketon" -> "Market";
        case "Stonewick" -> "Stone";
        case "Midtown" -> "Mid";
        case "Southgate" -> "South";
        case "Bridgeford" -> "Bridge";
        case "Harborview" -> "Harbor";
        case "Westhaven" -> "West";
        case "Sunfield" -> "Sun";
        case "Moonwell" -> "Moon";
        case "Castleford" -> "Castle";

        default -> cityName;
    };
}

private void showBuyDialog(PlayerMovement playerMovement) {

    List<MarketEconomy.MarketListing> listings =
            session.getCurrentMarketListings();

    if (listings.isEmpty()) {

        showInformation(
                "Shop",
                "No items are available in this market."
        );

        root.requestFocus();
        return;
    }

    List<String> options = new ArrayList<>();

    List<String> weaponNames = new ArrayList<>();

    for (MarketEconomy.MarketListing listing : listings) {

        options.add(
                listing.getWeaponName()
                        + " | Buy $"
                        + listing.getBuyPrice()
                        + " | Sell $"
                        + listing.getSellPrice()
        );

        weaponNames.add(
                listing.getWeaponName()
        );
    }

    options.add("Leave shop");

    ChoiceDialog<String> buyDialog =
            new ChoiceDialog<>(
                    options.get(0),
                    options
            );

    buyDialog.setTitle("Buy Items");

    buyDialog.setHeaderText(
            "Buy from "
                    + session.getCurrentPlayer().getCurrentCityName()
    );

    buyDialog.setContentText(
            "Choose an item to buy:"
    );

    Optional<String> buyResult =
            buyDialog.showAndWait();

    if (buyResult.isEmpty()
            || buyResult.get().equals("Leave shop")) {

        root.requestFocus();
        return;
    }

    int index =
            options.indexOf(
                    buyResult.get()
            );

    String weaponName =
            weaponNames.get(index);

    if (!session.buyCurrentPlayerWeapon(weaponName)) {

        showInformation(
                "Purchase Failed",
                session.getLastEventMessage()
        );

    } else {

        showInformation(
                "Purchase Complete",
                session.getLastEventMessage()
        );
    }

    playerMovement.refreshStatus();

    root.requestFocus();
}

private void showSellDialog(PlayerMovement playerMovement) {

    GamePlayer player =
            session.getCurrentPlayer();

    if (player.getInventory().isEmpty()) {

        showInformation(
                "Sell Items",
                "Inventory is empty. You have nothing to sell."
        );

        root.requestFocus();
        return;
    }

    List<Weapon> inventoryWeapons =
            new ArrayList<>(
                    player.getInventory().keySet()
            );

    List<String> options =
            new ArrayList<>();

    for (Weapon weapon : inventoryWeapons) {

        int quantity =
                player.getInventory().get(weapon);

        options.add(
                weapon.getName()
                        + " x"
                        + quantity
                        + " | Sell $"
                        + getSellPriceForCurrentCity(
                                weapon.getName()
                        )
        );
    }

    options.add("Leave shop");

    ChoiceDialog<String> sellDialog =
            new ChoiceDialog<>(
                    options.get(0),
                    options
            );

    sellDialog.setTitle("Sell Items");

    sellDialog.setHeaderText(
            "Sell from your inventory"
    );

    sellDialog.setContentText(
            "Choose an item to sell:"
    );

    Optional<String> sellResult =
            sellDialog.showAndWait();

    if (sellResult.isEmpty()
            || sellResult.get().equals("Leave shop")) {

        root.requestFocus();
        return;
    }

    int index =
            options.indexOf(
                    sellResult.get()
            );

    String weaponName =
            inventoryWeapons
                    .get(index)
                    .getName();

    if (!session.sellCurrentPlayerWeapon(weaponName)) {

        showInformation(
                "Sell Failed",
                session.getLastEventMessage()
        );

    } else {

        showInformation(
                "Sell Complete",
                session.getLastEventMessage()
        );
    }

    playerMovement.refreshStatus();

    root.requestFocus();
}

private void showInformation(
        String title,
        String message
) {

    Alert info =
            new Alert(
                    Alert.AlertType.INFORMATION
            );

    info.setTitle(title);

    info.setHeaderText(null);

    info.setContentText(message);

    info.showAndWait();
}

private int getSellPriceForCurrentCity(String weaponName) {

    for (MarketEconomy.MarketListing listing :
            session.getCurrentMarketListings()) {

        if (listing.getWeaponName().equals(weaponName)) {

            return listing.getSellPrice();
        }
    }

    return 0;
}

    // (No changes needed below)

    private GridPane createGrid() {

        GridPane grid = new GridPane();

        grid.setHgap(1);

        grid.setVgap(1);

        grid.setStyle(
                "-fx-grid-lines-visible: true;"
                        + "-fx-grid-line-color: black;"
        );

        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < GRID_SIZE; j++) {

                Rectangle cell =
                        new Rectangle(
                                CELL_SIZE,
                                CELL_SIZE
                        );

                cell.setFill(Color.TRANSPARENT);

                grid.add(cell, i, j);

                if (i == 4 && j == 4) {
                    cell.setFill(Color.YELLOW);
                }
            }
        }

        return grid;
    }

    public static void main(String[] args) {
        launch();
    }
}