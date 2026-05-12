# Traveling Salesman Game

A turn-based strategy and trading game built in Java using JavaFX.

Players travel between connected cities, manage resources, collect treasures, interact with city markets, and race to reach the final destination before running out of power or days.

---

# Features

## Connected City Travel System

* Players can only travel between connected cities
* Graph-based map traversal system
* Dynamic destination selection panel
* Turn-based movement using dice rolls and travel points

## AI Opponent

* Player 2 is controlled by an AI opponent
* AI automatically travels between connected cities
* AI uses travel points similarly to a real player
* Animated turn-by-turn movement for better gameplay visualization

## Resource Management

* Power/stamina system
* Travel consumes power
* Players can purchase power potions using in-game currency
* Strategic balance between movement, treasure collection, and survival

## Market Economy System

* City-based markets with dynamic buy/sell prices
* Weapon purchasing and selling
* Inventory tracking system
* Trading mechanics inspired by traveling merchant gameplay

## Treasure Collection

* Treasure houses randomly placed around the map
* Collect treasures to progress toward victory
* Multiple treasure types with varying values

## Turn-Based Gameplay

* Dice-based travel movement
* Day counter and game progression
* Win/loss conditions
* Event tracking and gameplay feedback system

## User Interface

* Built entirely using JavaFX
* Interactive map-based UI
* Separate panels for:

  * Travel information
  * Destinations
  * Inventory
  * Market data
  * Event logs
* Resizable game window

---

# Technologies Used

* Java
* JavaFX
* Maven
* Object-Oriented Programming (OOP)
* Graph Data Structures
* Event-Driven Programming

---

# Project Architecture

The project uses a modular object-oriented structure.

## Core Classes

| Class            | Purpose                                                   |
| ---------------- | --------------------------------------------------------- |
| `MainMap`        | Main JavaFX application and UI setup                      |
| `GameSession`    | Handles game state, turns, rules, and win/loss conditions |
| `PlayerMovement` | Handles player controls and AI movement                   |
| `GamePlayer`     | Stores player inventory, money, power, and treasures      |
| `CityGraph`      | Graph structure representing connected cities             |
| `MarketEconomy`  | Handles city market prices and trading                    |
| `TreasureHouse`  | Spawns and manages treasures on the map                   |
| `Scoreboard`     | Tracks player progression                                 |

---

# Gameplay Mechanics

## Objective

Collect enough treasures and reach the final goal city before running out of power or days.

---

## Movement

* Each turn begins with a dice roll
* Dice roll determines available travel points
* Players may only travel to connected cities
* Each move consumes power

---

## Economy System

Players earn money by:

* collecting treasures
* trading weapons
* exploring cities

Money can be used to:

* purchase weapons
* buy power potions
* survive longer during travel

---

# Controls

| Key        | Action                  |
| ---------- | ----------------------- |
| Arrow Keys | Select connected city   |
| Enter      | Travel to selected city |
| P          | Buy power potion        |
| E          | End turn                |

---

# Installation & Running

## Prerequisites

* Java 21+
* Maven
* JavaFX support

---

## Clone Repository

```bash
git clone <repository-url>
```

---

## Navigate Into Project

```bash
cd TravellingSalesmanGame-master
```

---

## Run the Game

```bash
mvn clean javafx:run
```

---

# Screenshots

*Add gameplay screenshots here.*

Suggested screenshots:

* Main gameplay screen
* AI movement in action
* Shop system
* Destination selection panel
* Game over / win screen

---

# Future Improvements

Potential future features:

* Save/load system
* Random world events
* Smarter AI pathfinding
* Animated player movement
* Sound effects and music
* Main menu system
* Multiplayer networking
* Additional cities and market types

---

# Learning Outcomes

This project helped develop skills in:

* Java GUI development
* Object-oriented software design
* Event-driven programming
* Game state management
* Graph traversal systems
* UI/UX iteration
* Debugging and architecture refinement

---

# Author

Amnah Irfan

Computer Engineering Student — University of Guelph
