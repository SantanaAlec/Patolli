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
     * @return The instance of the program is returned, if there's none a new one is created
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

    private boolean finished = false;

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
            Console.WriteLine("Bet has to be greater than 0 in order to play");
            return false;
        }

        board.init(4, 2);

        final StringBuilder sb = new StringBuilder();
        sb.append("Player Order: ");

        for (int i = 0; i < getPlayerCount(); i++) {
            sb.append(getPlayer(i).getName());
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());

        Console.WriteLine(sb.toString());

        return true;
    }

    public void addPlayer(final Player player) {
        Console.WriteLine("Adding new player to game " + player.getIdString());
        players.add(player);
    }

    public void addPlayers(final ArrayList<Player> players) {
        Console.WriteLine("Adding " + players.size() + " player" + (players.size() == 1 ? "" : "s") + " to game ");
        this.players.addAll(players);
    }

    private void removePlayer(final Player player) {
        Console.WriteLine("Removing player " + player.getName() + " from game");
        board.removeTokensFromPlayer(player);
        players.remove(player);
    }

    public void shufflePlayers() {
        Console.WriteLine("Shuffling order of players");

        Collections.shuffle(players);
    }

    private void insertToken() {
        final Player player = getCurrentPlayer();

        final Token token = player.createAndAssignToken(getCurrentPlayerStartPos());

        board.insertNewTokenAtPos(token, token.getInitialPos());

        Console.WriteLine("Inserted token " + player.getTokenIndex(token) + " in board for player " + player.getName() + " at position " + token.getInitialPos());
    }

    public Token selectToken(final int index) {
        final Player player = getCurrentPlayer();
        final Token token = player.getToken(index);

        Console.WriteLine("Selecting token " + index + " of player " + player.getName() + " in position " + token.getCurrentPos());

        return token;
    }

    public void play(final Token token) {
        final int successes = countCoins(5);
        playToken(token, successes);
    }

    private void playToken(final Token token, final int successes) {

        if (shouldGameEnd()) {
            finished = true;
            return;
        }

        Token selectedToken = token;

        if (successes == 0) {
            // p a y
            if (getCurrentPlayer().tokensInPlay() != 0) {
                Console.WriteLine("Player " + getCurrentPlayer().getName() + " is unable to move any tokens");
                payEveryone();
            } else {
                Console.WriteLine("Player " + getCurrentPlayer().getName() + "'s turn is skipped");
            }

            getCurrentPlayer().selectNextToken();

            advanceTurn();

            return;
        }

        if (getCurrentPlayer().tokensInPlay() == 0) {
            insertToken();

            getCurrentPlayer().selectNextToken();

            advanceTurn();
            return;
        }

        if (!getCurrentPlayer().hasInsertedAllTokens() && successes == 1) {
            insertToken();

            getCurrentPlayer().selectNextToken();

            advanceTurn();
            return;
        }

        if (selectedToken == null) {
            selectedToken = selectToken(getCurrentPlayer().getCurrentToken());
        } else {
            Console.WriteLine("Player " + getCurrentPlayer().getName() + " pays " + bet + " to move token " + getCurrentPlayer().getTokenIndex(selectedToken) + " at position " + selectedToken.getCurrentPos());
            payEveryone();
        }

        final int nextPos = board.getTokenPos(selectedToken) + getSpacesToMove(successes);

        final Space nextSpace = board.getSpace(nextPos);

        Console.WriteLine("Token " + getCurrentPlayer().getTokenIndex(selectedToken) + " of player " + getCurrentPlayer().getName() + " moves to space at position " + nextPos);

        if (board.willTokenFinish(selectedToken, nextPos)) {
            removeTokenThatFinished(selectedToken);
        } else if (tokenCanLandOnSpace(nextPos)) {
            moveToken(selectedToken, nextSpace, nextPos);
        } else {
            landOnToken(selectedToken, nextSpace, nextPos);
        }

        getCurrentPlayer().selectNextToken();

        if (playerHasFinished()) {
            removePlayer(getCurrentPlayer());
            advanceTurn();
            return;
        }

        advanceTurn();
    }

    private boolean shouldGameEnd() {
        return getPlayerCount() < 2;
    }

    private boolean tokenCanLandOnSpace(final int pos) {
        return board.getSpace(pos).getOwner() == null || board.getSpace(pos).getOwner() == getCurrentPlayer();
    }

    private void moveToken(final Token token, final Space nextSpace, final int nextPos) {
        board.moveTokenToPos(token, nextPos);

        if (nextSpace instanceof ExteriorSpace) {
            Console.WriteLine("Player " + getCurrentPlayer().getName() + " landed on an exterior space");
            grantExtraTurn();
        } else if (nextSpace instanceof TriangleSpace) {
            Console.WriteLine("Player " + getCurrentPlayer().getName() + " landed on an triangle space");
            payEveryoneDouble();
        }
    }

    private void landOnToken(final Token token, final Space nextSpace, final int nextPos) {
        Console.WriteLine("Token " + getCurrentPlayer().getTokenIndex(token) + " of player " + getCurrentPlayer().getName() + " moves to space occupied by " + nextSpace.getOwner().getName());

        if (nextSpace instanceof CentralSpace) {
            Console.WriteLine("Player " + getCurrentPlayer().getName() + " destroys " + nextSpace.getOwner().getName() + "'s token");
            board.removeTokenAtPos(token, nextPos);

            board.moveTokenToPos(token, nextPos);
        } else {
            Console.WriteLine("Player " + getCurrentPlayer().getName() + " returns to previous position");
        }
    }

    private void removeTokenThatFinished(final Token token) {
        Console.WriteLine("Token " + getCurrentPlayer().getTokenIndex(token) + " of player " + getCurrentPlayer().getName() + " has successfully looped around the board");

        payEveryonePays();

        Console.WriteLine("Removing token " + getCurrentPlayer().getTokenIndex(token) + " of player " + getCurrentPlayer().getName());
        board.removeTokenAtPos(token, token.getCurrentPos());
        board.markTokenAsFinished(token);
    }

    private boolean playerHasFinished() {
        if (getCurrentPlayer().hasInsertedAllTokens()) {
            boolean result = true;

            for (Token token : getCurrentPlayer().getTokens()) {
                if (token.getCurrentPos() >= 0) {
                    result = false;
                }
            }

            return result;
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

    private void grantExtraTurn() {
        turn--;

        if (turn < 1) {
            turn = getPlayerCount();
        }
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

    private void pay(final Player to, final Player from) {
        Console.WriteLine("Player " + from.getName() + " pays " + bet + " to player " + to.getName());

        from.setBalance(from.getBalance() - bet);

        to.setBalance(to.getBalance() + bet);

        Console.WriteLine("Player " + from.getName() + ":  " + from.getBalance() + " | Player " + to.getName() + ": " + to.getBalance());
    }

    private void payEveryone() {
        Console.WriteLine("Player " + getCurrentPlayer().getName() + " pays " + bet + " to everyone");

        for (Player player : players) {
            if (!player.equals(getCurrentPlayer())) {
                pay(player, getCurrentPlayer());
            }
        }

        if (getCurrentPlayer().isBroke()) {
            Console.WriteLine("Player " + getCurrentPlayer().getName() + " is unable to pay any more bets and cannot continue playing");
            removePlayer(getCurrentPlayer());
        }
    }

    private void payEveryoneDouble() {
        Console.WriteLine("Player " + getCurrentPlayer().getName() + " pays " + bet * 2 + " to everyone");

        for (int i = 0; i < 2; i++) {
            payEveryone();
        }

        if (getCurrentPlayer().isBroke()) {
            Console.WriteLine("Player " + getCurrentPlayer().getName() + " is unable to pay any more bets and cannot continue playing");
            removePlayer(getCurrentPlayer());
        }
    }

    private void payEveryonePays() {
        Console.WriteLine("Everyone pays " + bet + " to " + getCurrentPlayer().getName());
        final ArrayList<Player> playersToRemove = new ArrayList<>();

        for (Player player : players) {
            if (!player.equals(getCurrentPlayer())) {
                pay(getCurrentPlayer(), player);
            }

            if (player.isBroke()) {
                Console.WriteLine("Player " + player.getName() + " is unable to pay any more bets and cannot continue playing");
                playersToRemove.add(player);
            }
        }

        for (Player player : playersToRemove) {
            removePlayer(player);
        }

    }

    private int getSpacesToMove(final int successes) {
        int result = 0;

        switch (successes) {
            case 1 -> {
                result = 1;
            }
            case 2 -> {
                result = 2;
            }
            case 3 -> {
                result = 3;
            }
            case 4 -> {
                result = 4;
            }
            case 5 -> {
                result = 10;
            }
        }

        Console.WriteLine("Player " + getCurrentPlayer().getName() + " moves " + result + " space" + (result == 1 ? "" : "s"));

        return result;
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

    public boolean hasFinished() {
        return finished;
    }

    public Board getBoard() {
        return board;
    }

}
