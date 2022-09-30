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

public class Game {

    private static Game instance;

    /**
     * Singleton pattern to keep a single instance of this class program running
     *
     * @return The instance of the program is returned, if there's none a new one is created
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    private Game() {
    }

    private final ArrayList<Player> players = new ArrayList<>();

    private final Board board = Board.getInstance();

    private int bet;

    private int turn = 0;

    public void run() {
        init();
    }

    private void init() {
        board.init();
    }

    public void addPlayer(final Player player) {
        players.add(player);
    }

    private void removePlayer(final Player player) {
        board.removeTokensFromPlayer(player);
        players.remove(player);
    }

    public void shufflePlayers() {
        Collections.shuffle(players);
    }

    private void insertToken() {
        final Player player = getCurrentPlayer();
        final Token token = player.createAndAssignToken(getCurrentPlayerStartPos());

        board.insertTokenAtPos(token, token.getInitialPos());
    }

    public Token selectToken(final int index) {
        return getCurrentPlayer().getToken(index);
    }

    public void playToken(final Token token) {
        final int successes = throwCoins(5);

        if (successes == 0) {
            // p a y
            payEveryone();
            return;
        }

        if (!getCurrentPlayer().hasTokensInPlay() && successes > 0) {
            insertToken();
            advanceTurn();
            return;
        }

        if (!playerHasFinished()) {
            removePlayerAndAdvance();
            return;
        }

        final int prevPos = board.getTokenPos(token);
        final int nextPos = board.getTokenPos(token) + getSpacesToMove(successes);

        final Space nextSpace = board.getSpace(nextPos);

        if (spaceHasSameOwnerAsToken(nextPos)) {
            moveToken(token, nextSpace, prevPos, nextPos);
        } else {
            landOnToken(token, nextSpace, nextPos);
        }
    }

    private void removePlayerAndAdvance() {
        removePlayer(getCurrentPlayer());
        advanceTurn();
    }

    private boolean spaceHasSameOwnerAsToken(final int pos) {
        return board.getSpace(pos).getOwner() == getCurrentPlayer();
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

    private void moveToken(final Token token, final Space nextSpace, final int prevPos, final int nextPos) {
        if (board.willTokenFinish(token, prevPos, nextPos)) {
            payEveryonePays();

            board.removeTokenAtPos(token, nextPos);
            board.markTokenAsFinished(token);

            advanceTurn();

            return;
        }

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

    private int getPlayerCount() {
        return players.size();
    }

    private Player getCurrentPlayer() {
        return players.get(turn);
    }

    private int getCurrentPlayerStartPos() {
        final int boardSize = board.getBoardSize();
        final int sectionSquares = boardSize / 4;
        final int blades = 4;

        return boardSize - sectionSquares * (1 + (blades - (turn + 1)));
    }

    private void advanceTurn() {
        turn++;

        if (turn >= getPlayerCount()) {
            turn = 0;
        }
    }

    private int throwCoins(final int amount) {
        Random random = new Random();
        int successes = 0;

        for (int i = 0; i < amount; i++) {
            if (random.nextBoolean()) {
                successes++;
            }
        }

        return successes;
    }

    private void pay(final Player player) {
        final Player currentPlayer = getCurrentPlayer();

        currentPlayer.setBalance(currentPlayer.getBalance() - bet);

        player.setBalance(player.getBalance() + bet);

        if (currentPlayer.isBroke()) {
            removePlayerAndAdvance();
        } else {
            advanceTurn();
        }
    }

    private void payEveryone() {
        for (Player player : players) {
            if (!player.equals(getCurrentPlayer())) {
                pay(player);
            }
        }
    }

    private void payEveryoneDouble() {
        for (Player player : players) {
            if (!player.equals(getCurrentPlayer())) {
                for (int i = 0; i < 2; i++) {
                    pay(player);
                }
            }
        }
    }

    private void payEveryonePays() {
        for (Player player : players) {
            if (player.equals(getCurrentPlayer())) {
                pay(getCurrentPlayer());
            }
        }
    }

}
