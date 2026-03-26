# SkyTeam Board Game – Java / libGDX

## Overview

This project is a digital implementation of the cooperative board game **Sky Team**, developed in **Java** using the **libGDX** framework.
The goal of the project was to design and implement the game logic using a clean software architecture and to build a graphical user interface for the game.

The project was developed as part of a university software engineering project and focuses on object-oriented programming, MVC architecture, game logic implementation, GUI development, and unit testing.

Although the project was not fully completed, the core game logic, architecture, and GUI framework were successfully implemented.

---

## Technologies Used

* Java
* libGDX
* Gradle
* JUnit
* Mockito
* Jackson (JSON parsing)
* IntelliJ IDEA
* Git

---

## Project Architecture

The project is structured using the **Model–View–Controller (MVC)** architecture.

### Model

Contains the core game logic and game state:

* Game
* Board
* Player
* FlightPlan
* Altitude
* Dice
* Tasks

### View

Responsible for rendering the game and user interface:

* Main Menu Screen
* Game Screen
* UI rendering
* Input handling

### Controller

Handles game flow, user input, and communication between Model and View.

This architecture improves maintainability, scalability, and separation of concerns.

---

## Project Structure

```
assets/     -> textures, skins, JSON configs
core/       -> game logic (MVC structure)
lwjgl3/     -> desktop launcher
```

### assets

Contains all runtime resources such as:

* Textures
* UI skins
* JSON configuration files
* Flight plans

### core

Contains the main game logic structured into:

* model
* view
* controller
* tests

### lwjgl3

Contains the desktop launcher used to start the game.

---

## Features Implemented

* Core game logic
* Board and player system
* Dice mechanics
* Flight plan system
* Altitude system
* Task system
* Main menu screen
* Game screen
* Input handling
* Drag & drop mechanics
* JSON configuration loading
* Unit tests with JUnit and Mockito
* UML class diagram
* Activity diagram
* Developer documentation
* User documentation

---

## UML Diagram

The class diagram of the system architecture can be found here:

```
Klassendiagramm.png
```

## Documentation

The project includes full documentation:

* Developer Documentation: `EntwicklerDokumentation.pdf`
* User Documentation: `BenutzerDokumentation.pdf`
* Activity Diagram: `AktivitaetsDiagramm.png`

---

## How to Run the Project

### Windows

```
gradlew.bat lwjgl3:run
```

### Linux / Mac

```
./gradlew lwjgl3:run
```

The game will start in a desktop window.

---

## Learning Outcomes

This project helped me learn and practice:

* Object-oriented design
* MVC architecture
* Game development with libGDX
* Gradle build system
* Unit testing with JUnit and Mockito
* UML diagrams and software documentation
* Asset management in game development
* Software architecture design
* Working on a larger software project independently

---

## Author

**Emrah Zehic**
Computer Science Student
Hof University of Applied Sciences

---
