package model;

import model.tasks.Task;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class Game {

    private Board board;
    private Player pilot;
    private Player copilot;


    public Game(Plan plan) throws IOException {
        this.board = new Board(plan);
        this.pilot = new Player(Role.PILOT);
        this.copilot = new Player(Role.COPILOT);
    }


    public void playRound(){
        Player playerOne;
        Player playerTwo;
        if (board.getAltitude().getAltitude()%2==0){
            playerOne = getPilot();
            playerTwo = getCopilot();
        }else {
            playerOne = getCopilot();
            playerTwo = getPilot();
        }
        playerOne.rollDice(4);
        playerTwo.rollDice(4);
        while (!playerOne.getDice().getRolledValues().isEmpty() || !playerTwo.getDice().getRolledValues().isEmpty()) {
            if (!playerOne.getDice().getRolledValues().isEmpty()) {
                takeTurn(playerOne);
                GameState.evaluateAll(this);
            }
            if (!playerTwo.getDice().getRolledValues().isEmpty()) {
                takeTurn(playerTwo);
                GameState.evaluateAll(this);
            }
        }


        board.getAltitude().decreaseAltitude();

    }

    private void takeTurn(Player player) { //bis ich input von den spieler implementiere haben die Spieler keine freie Wahl was Reihenfolge von Dice angeht
        List<Integer> rolledValues = player.getDice().getRolledValues();
        boolean placedDie = false;

        for (int diceValue : rolledValues) {
            for (Task task : board.getAvailableTasks(new HashSet<>(List.of(player.getRole())))) {
                if (player.contributeToTask(diceValue, task)) {
                    placedDie = true;
                    break;
                }
            }
        }

        if (!placedDie) {
            System.out.println(player.getRole() + " has no valid moves and passes their turn");
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player getPilot() {
        return pilot;
    }

    public Player getCopilot() {
        return copilot;
    }


}
