/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import entities.Player;
import entities.Token;
import entities.spaces.CentralSpace;
import entities.spaces.ExteriorSpace;
import entities.spaces.Space;
import entities.spaces.SquareSpace;
import entities.spaces.TriangleSpace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import utilities.Console;

public class Game {

    private static Game instance;

    /**
     * Singleton pattern to keep a single instance of this class program running
     *
     * @return The instance of the program is returned, if there's none a new
     * one is created
     */
    public static Game getInstance() {
        if (instance == null) {
            Console.WriteLine("Game instance not found. Creating new instance");
            instance = new Game();
        }

        return instance;
    }

    private Game() {
    }

    private final ArrayList<Player> players = new ArrayList<>();

    private final Board board = Board.getInstance();

    private int bet;

    private int turn = 1;

    public boolean run() {
        if (!init()) {
            Console.WriteLine("Failed to initialize game");
            return false;
        }

        return true;
    }

    private boolean init() {
        if (getPlayerCount() < 2) {
            Console.WriteLine("Not enough players have joined the game (" + getPlayerCount() + "/4)");
            return false;
        }

        if (bet <= 0) {
            Console.WriteLine("A minimum bet is required to play");
            return false;
        }

        //shufflePlayers();
        board.init(3, 2);

        return true;
    }

    public void addPlayer(final Player player) {
        Console.WriteLine("Adding new player to game " + player.getIdString());
        players.add(player);
    }

    public void addPlayers(final ArrayList<Player> players) {
        Console.WriteLine("Adding new players to game " + players.size() + " players");
        this.players.addAll(players);
    }

    private void removePlayer(final Player player) {
        Console.WriteLine("Removing player from game " + player.getIdString());
        board.removeTokensFromPlayer(player);
        players.remove(player);
    }

    private void shufflePlayers() {
        Console.WriteLine("Shuffling order of players");

        Collections.shuffle(players);

        final StringBuilder sb = new StringBuilder();
        sb.append("New player order: ");

        for (int i = 0; i < getPlayerCount(); i++) {
            sb.append(getPlayer(i).getName());
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());

        Console.WriteLine(sb.toString());
    }

    private void insertToken() {
        final Player player = getCurrentPlayer();

        final Token token = player.createAndAssignToken(getCurrentPlayerStartPos());

        board.insertNewTokenAtPos(token, token.getInitialPos());

        Console.WriteLine("Inserted new token in board for player " + player.getName() + " at position " + token.getInitialPos());
        advanceTurn();
    }

    public Token selectToken(final int index) {
        final Player player = getCurrentPlayer();
        final Token token = player.getToken(index);

        Console.WriteLine("Selecting token " + index + " of player " + player.getName() + " in position " + token.getCurrentPos());

        return token;
    }

    public void playToken(final Token token) {
        final int successes = countCoins(5);
        Token selectedToken = token;

        if (successes == 0) {
            // p a y
            if (getCurrentPlayer().hasTokensInPlay()) {
                payEveryone();
            }
            return;
        }

        if (selectedToken == null) {
            if (playerHasFinished()) {
                removePlayerAndAdvance();
                return;
            }

            if (!getCurrentPlayer().hasTokensInPlay() && successes > 0) {
                insertToken();
                return;
            }

            if (!getCurrentPlayer().hasInsertedAllTokens() && successes == 1) {
                insertToken();
                return;
            }

            selectedToken = selectToken(getCurrentPlayer().getCurrentToken() + 1);
        } else {
            payEveryone();
        }

        final int nextPos = board.getTokenPos(selectedToken) + getSpacesToMove(successes);

        final Space nextSpace = board.getSpace(nextPos);

        if (board.willTokenFinish(selectedToken, nextPos)) {
            removeTokenThatFinished(selectedToken);
        } else if (tokenCanLandOnSpace(nextPos)) {
            moveToken(selectedToken, nextSpace, nextPos);
        } else {
            landOnToken(selectedToken, nextSpace, nextPos);
        }

        selectedToken.getOwner().selectNextToken();

    }

    public void playToken(final Token token, int successes) {

        Token selectedToken = token;

        if (successes == 0) {
            // p a y
            if (getCurrentPlayer().hasTokensInPlay()) {
                payEveryone();
            }
            return;
        }

        if (selectedToken == null) {
            if (playerHasFinished()) {
                removePlayerAndAdvance();
                return;
            }

            if (!getCurrentPlayer().hasTokensInPlay() && successes > 0) {
                insertToken();
                return;
            }

            if (!getCurrentPlayer().hasInsertedAllTokens() && successes == 1) {
                insertToken();
                return;
            }

            selectedToken = selectToken(getCurrentPlayer().getCurrentToken() + 1);
        } else {
            payEveryone();
        }

        final int nextPos = board.getTokenPos(selectedToken) + getSpacesToMove(successes);

        final Space nextSpace = board.getSpace(nextPos);

        if (board.willTokenFinish(selectedToken, nextPos)) {
            removeTokenThatFinished(selectedToken);
        } else if (tokenCanLandOnSpace(nextPos)) {
            moveToken(selectedToken, nextSpace, nextPos);
        } else {
            landOnToken(selectedToken, nextSpace, nextPos);
        }

        selectedToken.getOwner().selectNextToken();

        Console.WriteLine("al jugador: " + getCurrentPlayer().getName() + " Le queda en la bolsa: " + getCurrentPlayer().getBalance() + "          sdasdadssadsadasdsadsad");
    }

    private void removePlayerAndAdvance() {
        removePlayer(getCurrentPlayer());
        advanceTurn();
    }

    private boolean tokenCanLandOnSpace(final int pos) {
        return board.getSpace(pos).getOwner() == null || board.getSpace(pos).getOwner() == getCurrentPlayer();
    }

    private void moveToken(final Token token, final Space nextSpace, final int nextPos) {
        Console.WriteLine("Token " + getCurrentPlayer().getTokenIndex(token) + " of player " + getCurrentPlayer().getName() + " moves to space at position " + nextPos);
        board.moveTokenToPos(token, nextPos);

        if (nextSpace instanceof ExteriorSpace) {
        } else if (nextSpace instanceof TriangleSpace) {
            payEveryoneDouble();
        } else if (nextSpace instanceof CentralSpace) {
            advanceTurn();
        } else if (nextSpace instanceof SquareSpace) {
            advanceTurn();
        }
    }

    private void landOnToken(final Token token, final Space nextSpace, final int nextPos) {
        Console.WriteLine("Token " + getCurrentPlayer().getTokenIndex(token) + " of player " + getCurrentPlayer().getName() + " moves to space occupied by " + nextSpace.getOwner().getName());

        if (nextSpace instanceof TriangleSpace) {
            advanceTurn();
        } else if (nextSpace instanceof CentralSpace) {
            board.removeTokenAtPos(token, nextPos);

            board.moveTokenToPos(token, nextPos);

            advanceTurn();
        } else if (nextSpace instanceof SquareSpace) {
            advanceTurn();
        }
    }

    private void removeTokenThatFinished(final Token token) {
        Console.WriteLine("Token " + getCurrentPlayer().getTokenIndex(token) + " of player " + getCurrentPlayer().getName() + " has finished");

        payEveryonePays();

        board.removeTokenAtPos(token, token.getCurrentPos());
        board.markTokenAsFinished(token);

        advanceTurn();
    }

    private boolean playerHasFinished() {
        if (getCurrentPlayer().getTokensCount() == 6) {
            for (Token token : getCurrentPlayer().getTokens()) {
                if (token.getCurrentPos() >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private void advanceTurn() {
        turn++;

        if (turn > getPlayerCount()) {
            turn = 1;
        }

        Console.WriteLine("It is now player " + getCurrentPlayer().getName() + "'s turn");
    }

    private int countCoins(final int amount) {
        Random random = new Random();
        int successes = 0;

        Console.WriteLine("Throwing " + amount + " coin" + (amount == 1 ? "" : "s") + " for player " + getCurrentPlayer().getName());

        for (int i = 0; i < amount; i++) {
            if (random.nextBoolean()) {
                successes++;
            }
        }

        Console.WriteLine(successes + " coin" + (successes == 1 ? "" : "s") + " landed on true");

        return successes;
    }

    private void pay(final Player player) {
        final Player currentPlayer = getCurrentPlayer();

        currentPlayer.setBalance(currentPlayer.getBalance() - bet);

        player.setBalance(player.getBalance() + bet);
    }

    private void payEveryone() {
        for (Player player : players) {
            if (!player.equals(getCurrentPlayer())) {
                pay(player);
            }
        }

        Console.WriteLine("Jugador " + getCurrentPlayer().getName() + " Pago a los otros");
        Console.WriteLine("al jugador: " + getCurrentPlayer().getName() + " Le queda en la bolsa: " + getCurrentPlayer().getBalance() + "--------------------------------------------------------------------------------------------------------");

        if (getCurrentPlayer().isBroke()) {
            removePlayerAndAdvance();
        } else {
            advanceTurn();
        }
    }

    private void payEveryoneDouble() {
        for (int i = 0; i < 2; i++) {
            payEveryone();
        }

        if (getCurrentPlayer().isBroke()) {
            removePlayerAndAdvance();
        } else {
            advanceTurn();
        }
    }

    private void payEveryonePays() {
        for (Player player : players) {
            if (player.equals(getCurrentPlayer())) {
                pay(getCurrentPlayer());

                if (player.isBroke()) {
                    removePlayer(player);
                }
            }
        }
    }

    private int getSpacesToMove(final int successes) {
        switch (successes) {
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            case 3 -> {
                return 3;
            }
            case 4 -> {
                return 4;
            }
            case 5 -> {
                return 5;
            }
        }

        return 0;
    }

    private int getPlayerCount() {
        return players.size();
    }

    private Player getPlayer(final int index) {
        return players.get(index);
    }

    private Player getCurrentPlayer() {
        return getPlayer(turn - 1);
    }

    private int getCurrentPlayerStartPos() {
        final int boardSize = board.getBoardSize();
        final int sectionSquares = boardSize / 4;
        final int blades = 4;

        return (boardSize - sectionSquares * (1 + (blades - (turn)))) + 1;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getTurn() {
        return turn;
    }

    public Board getBoard() {
        return board;
    }

}
