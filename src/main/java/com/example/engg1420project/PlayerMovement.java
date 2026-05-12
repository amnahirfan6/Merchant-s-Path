package com.example.engg1420project;

import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class PlayerMovement {

    private final Player player1;
    private final Player player2;

    private final Label turnLabel;
    private final Label inventoryLabel;
    private final Label marketLabel;
    private final Label eventLabel;

    private final GameSession session;

    private final Label destinationLabel;

    private int selectedDestinationIndex = 0;

public PlayerMovement(Player player1,
                      Player player2,
                      Label turnLabel,
                      Label destinationLabel,
                      Label inventoryLabel,
                      Label marketLabel,
                      Label eventLabel,
                      GameSession session) {

        this.player1 = player1;
        this.player2 = player2;

        this.turnLabel = turnLabel;
        this.inventoryLabel = inventoryLabel;
        this.marketLabel = marketLabel;
        this.eventLabel = eventLabel;

        this.session = session;

        this.destinationLabel = destinationLabel;

        player1.moveToCity(
                session.getCityGraph().getCity(
                        session.getPlayers().get(0).getCurrentCityName()
                )
        );

        player2.moveToCity(
                session.getCityGraph().getCity(
                        session.getPlayers().get(1).getCurrentCityName()
                )
        );

        refreshStatus();
    }

    public void handleKeyPress(KeyEvent event) {

        if (session.hasWon() || session.hasLost()) {
            refreshStatus();
            return;
        }

        List<CityGraph.City> validDestinations =
                session.getConnectedCitiesForCurrentPlayer();

        switch (event.getCode()) {

            case UP, LEFT:
                selectPreviousDestination(validDestinations.size());
                refreshStatus();
                break;

            case DOWN, RIGHT:
                selectNextDestination(validDestinations.size());
                refreshStatus();
                break;

            case ENTER:
                travelToSelectedDestination(validDestinations);
                break;

            case E:
                session.endCurrentTurn();
                selectedDestinationIndex = 0;
                refreshStatus();
                break;

    case P:
    buyPowerPotion();
    refreshStatus();
    break;

            default:
                // Ignore other keys
        }
    }

    private Player getCurrentPlayerSprite() {
        return session.getCurrentPlayerIndex() == 0
                ? player1
                : player2;
    }

    private void selectPreviousDestination(int destinationCount) {

        if (destinationCount == 0) {
            selectedDestinationIndex = 0;
            return;
        }

        selectedDestinationIndex =
                (selectedDestinationIndex - 1 + destinationCount)
                        % destinationCount;
    }

    private void selectNextDestination(int destinationCount) {

        if (destinationCount == 0) {
            selectedDestinationIndex = 0;
            return;
        }

        selectedDestinationIndex =
                (selectedDestinationIndex + 1)
                        % destinationCount;
    }

    private void travelToSelectedDestination(
            List<CityGraph.City> validDestinations
    ) {

        if (validDestinations.isEmpty()) {

            turnLabel.setText(
                    buildTravelMessage(
                            "No connected cities are available from here."
                    )
            );
            destinationLabel.setText(
        buildDestinationMessage()
);

            updateInventoryAndEventLabels();
            return;
        }

        CityGraph.City destination =
                validDestinations.get(selectedDestinationIndex);

        GamePlayer movingPlayer =
                session.getCurrentPlayer();

        Player movingSprite =
                getCurrentPlayerSprite();

        session.travelCurrentPlayerTo(destination);

        if (movingPlayer.getCurrentCityName()
                .equals(destination.getName())) {

            movingSprite.moveToCity(destination);
        }

        // =========================
        // AI PLAYER MOVEMENT
        // =========================

        moveAIPlayer();

        selectedDestinationIndex = 0;

        refreshStatus();
    }

public void refreshStatus() {

    turnLabel.setText(
            buildTravelMessage(null)
    );

    destinationLabel.setText(
            buildDestinationMessage()
    );

    // NORMAL STYLE
    turnLabel.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
    );

    // GAME OVER STYLE
    if (session.hasWon()) {

        turnLabel.setStyle(
                "-fx-text-fill: lime;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );
    }

    else if (session.hasLost()) {

        turnLabel.setStyle(
                "-fx-text-fill: red;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );
    }

    updateInventoryAndEventLabels();
}

    private void updateInventoryAndEventLabels() {

        GamePlayer currentPlayer =
                session.getCurrentPlayer();

        inventoryLabel.setText(
                currentPlayer.getName()
                        + "\nMoney: $" + currentPlayer.getMoney()
                        + "\nPower: " + currentPlayer.getPower()
                        + "\nTravel points: "
                        + session.getRemainingTravelPoints()
                        + "/" + session.getCurrentRoll()
                        + "\nTreasures: "
                        + currentPlayer.getTreasuresCollected()
                        + "/" + GameRules.TREASURES_TO_WIN
                        + "\nItems: "
                        + currentPlayer.getInventorySummary()
        );

        marketLabel.setText(
                buildMarketMessage()
        );

        eventLabel.setText(
                "Last event: "
                        + session.getLastEventMessage()
        );
    }

    private String buildMarketMessage() {

        List<MarketEconomy.MarketListing> listings =
                session.getCurrentMarketListings();

        if (listings.isEmpty()) {
            return "No market available";
        }

        StringBuilder sb =
                new StringBuilder(
                        "Market in "
                                + session.getCurrentPlayer().getCurrentCityName()
                                + ":\n"
                );

        for (MarketEconomy.MarketListing listing : listings) {

            sb.append(listing.getWeaponName())
                    .append(" - Buy: $")
                    .append(listing.getBuyPrice())
                    .append(" Sell: $")
                    .append(listing.getSellPrice())
                    .append("\n");
        }

        return sb.toString().trim();
    }

    private String getSelectedDestinationName() {

        if (session.hasWon() || session.hasLost()) {
            return "None";
        }

        List<CityGraph.City> validDestinations =
                session.getConnectedCitiesForCurrentPlayer();

        if (validDestinations.isEmpty()) {
            return "None";
        }

        return validDestinations
                .get(selectedDestinationIndex)
                .getName();
    }

    private String buildTravelMessage(String warning) {

        GamePlayer currentPlayer =
                session.getCurrentPlayer();

        StringBuilder message =
                new StringBuilder();

        message.append(currentPlayer.getName())
                .append("'s Turn");

        message.append("\nDay: ")
                .append(session.getDay())
                .append("/")
                .append(GameRules.MAX_DAYS);

        message.append("\nDie roll: ")
                .append(session.getCurrentRoll())
                .append(" | Travel points left: ")
                .append(session.getRemainingTravelPoints());

        message.append("\nCurrent city: ")
                .append(currentPlayer.getCurrentCityName());


        if (session.hasWon()) {

            message.append(
                    "\nGame over: a player reached "
                            + GameRules.GOAL_CITY
                            + " with enough treasure."
            );

            return message.toString();
        }

        if (session.hasLost()) {

            message.append(
                    "\n⚠ GAME OVER ⚠\n" + //
                                                "Out of power or days."
            );

            return message.toString();
        }

message.append(
    "\nArrows choose | Enter travels | "
    + "P buy potion ($"
    + GameRules.POTION_COST
    + " → +"
    + GameRules.POTION_POWER_GAIN
    + " power) | "
    + "E end turn."
);

        if (warning != null) {
            message.append("\n").append(warning);
        }

        return message.toString();
    }

  private void buyPowerPotion() {

    GamePlayer player = session.getCurrentPlayer();

    int potionCost = GameRules.POTION_COST;

    int powerGain = GameRules.POTION_POWER_GAIN;

    if (player.getMoney() < potionCost) {

        eventLabel.setText(
                "Not enough money to buy a power potion!"
        );

        return;
    }

    player.spendMoney(potionCost);

    player.recoverPower(powerGain);

    eventLabel.setText(
            player.getName()
                    + " bought a power potion for $"
                    + potionCost
                    + " and gained "
                    + powerGain
                    + " power!"
    );
}


private String buildDestinationMessage() {

    if (session.hasWon() || session.hasLost()) {
        return "Game Over";
    }

    StringBuilder sb = new StringBuilder();

    sb.append("Selected: ")
      .append(getSelectedDestinationName())
      .append("\n\n");

    sb.append("Available Cities:\n");

    List<CityGraph.City> validDestinations =
            session.getConnectedCitiesForCurrentPlayer();

    for (int i = 0; i < validDestinations.size(); i++) {

        CityGraph.City city = validDestinations.get(i);

        if (i == selectedDestinationIndex) {
            sb.append("> ");
        }

        sb.append(city.getName())
          .append("\n");
    }

    return sb.toString();
}

    // =========================================
    // AI PLAYER MOVEMENT
    // =========================================

private void moveAIPlayer() {

    aiTimeline.getKeyFrames().add(

            new KeyFrame(
                    Duration.seconds(0.7),

                    event -> {

                        // Stop if turn changed
                        if (session.getCurrentPlayerIndex() != 1) {
                            aiTimeline.stop();
                            refreshStatus();
                            return;
                        }

                        // Stop if no travel points
                        if (session.getRemainingTravelPoints() <= 0) {
                            aiTimeline.stop();
                            refreshStatus();
                            return;
                        }

                        List<CityGraph.City> connectedCities =
                                session.getConnectedCitiesForCurrentPlayer();

                        if (connectedCities.isEmpty()) {
                            aiTimeline.stop();
                            refreshStatus();
                            return;
                        }

                        CityGraph.City nextCity =
                                connectedCities.get(
                                        random.nextInt(
                                                connectedCities.size()
                                        )
                                );

                        // Move AI
                        session.travelCurrentPlayerTo(nextCity);

                        player2.moveToCity(nextCity);

                        // Update UI after EACH move
                        refreshStatus();
                    }
            )
    );

    aiTimeline.play();
}

    private final Timeline aiTimeline = new Timeline();

    private final Random random = new Random();
}