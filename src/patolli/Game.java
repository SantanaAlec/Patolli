package patolli;

import entities.spaces.Token;
import entities.spaces.Player;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import entities.spaces.CentralSpace;
import entities.spaces.ExteriorSpace;
import entities.spaces.Space;
import entities.spaces.SquareSpace;
import entities.spaces.TriangleSpace;
import java.util.Collections;

/**
 *
 * @author Alec_
 *
 * ESTABLER UN LIMITE DE APUESTA A 1/3 MAX Cuando te caen encima en el centro,
 * se elimina, no puedes usar esa ficha de nuevo quitamos tu max de fichas a -1
 * Para todos los momentos de "PAGA APUESTA" es con el monto fijo inicial, nunca
 * se mueve hasta que acaba la partida
 */
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
            instance = new Game();
        }

        return instance;
    }

    private Game() {
    }

    private static int code;
    private static ArrayList<Player> players = new ArrayList<>();
    private static int budget = 0;
    private static int tokens = 2;
    private static int bet;
    private static boolean[] dice = new boolean[5];
    private static int turn = 0;
    private final Board board = Board.getInstance();

    //Run corre el juego
    public void run() {
        init();
    }

    //Inicializa el tablero
    private void init() {
        board.init();
    }

    /**
     * Avanzas el numero de puntos que te salgan: Si son 5 puntos avanzas 10
     * casillas Si son 5 lisas pierdes turno y pagas bet Cuando sacas una ficha,
     * le cobras la bet de los jugadores y repites turno Si caes en casillas
     * exteriores, repite turno Si caes en casillas de triangulo pagas doble bet
     */
    /**
     *
     * @param apuesta
     */
    public void betting(int apuesta) {
        setBet(apuesta); //betAmount = Integer.parseInt( betInput.getText());

        for (Player jugador : players) {
            jugador.setBag(jugador.getBag() - getBet());//Agarrar el budget que ya tenian y restarle la bet 
        }
    }

    public void addPlayer(Player jugador) {
        players.add(jugador);
        //sout para ver que hace
        System.out.println("Jugador: " + jugador.getName() + " Agregado");
    }

    private void removePlayer(final Player player) {
        board.removeTokensFromPlayer(player);
        players.remove(player);
    }

    //Método que lanza los dados
    public int throwDice(final int throws_) {
        Random random = new Random();
        int dadosTrue = 0;

        for (int i = 0; i < throws_; i++) {
            dice[i] = random.nextBoolean();

            if (dice[i]) {
                dadosTrue++;
            }
        }

        return dadosTrue;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        Game.players = players;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        Game.bet = bet;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        Game.budget = budget;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        Game.tokens = tokens;
    }

    //Métodos nuevos
    public void betWinner(Token token) {
        //Saber que token sale, obtener al jugador ganador, obtener la bolsa de los demás, restarle la apuesta y sumar la suma del total al ganador
    }

    //Comparar los -2 de las fichas para establecer al ganador
    //contar -2
    //Agarrar el array de tokens, ver su posición, contar los -2 de cada token
    //comparar con los de los demás jugadores
    //(¿Como comparo a los jugadores? foreach, checa sus tokens, juntar y comparar sus tokens para saber si el que compare es mayor al anterior y si no continua y comparo al otro, etc.)
    public Player getPlayerInLead() {
        int count = 0;
        int ahead = 0;
        Player leader = null;

        //Por cada jugador
        for (Player player : players) {
            //Por cada token del jugador
            for (Token token : player.getTokens()) {
                //Cuyo token haya llegado a la meta
                if (token.getActualPosition() == -2) {
                    //Dime la cantidad
                    count += 1;
                }
            }
            //Compara la cantidad de tokens del jugado anterior y evaluala con el nuevo para asignar el mayor
            if (count > ahead) {
                ahead = count;
                leader = player;
            }
        }
        return leader;
    }

    //- Si cae en una casilla ya ocupada por alguien más
    public void ocuppedSpace(Space space, Token token) {
        if (space instanceof CentralSpace) {
            if (space.getOwner() != token.getOwner()) {
                space.clearTokens();
                space.insertToken(token);
            }
        }
    }

    /**
     * Método para los tiros iniciales donde; con que saque 1 solo true puede
     * meter fichas
     *
     * @param player
     * @return
     */
    public void firstThrow(Player player) {
        //Establecer quien esta lanzando los dados
        int coin = throwDice(5);
        System.out.println("1er lanzada del jugador: " + player.getName() + " saco: " + coin);
        switch (coin) {
            case 0 ->
                advanceTurn();
            default -> {
                Token tokeni = new Token(player, getCurrentPlayerStartPos());
                board.insertToken(tokeni, getCurrentPlayerStartPos());
                tokeni.getOwner().addToken(tokeni);
                advanceTurn();
                System.out.println("La ficha esta en la posición: " + tokeni.getInitialPosition());
            }
        }
    }

    //Avanzar turno para saber a que jugador de la lista le toca el turno
    public void advanceTurn() {
        turn++;
        if (turn > players.size()) {
            turn = 0;
        }
    }

    //- Si cae encima de alguien más en el CENTRO para eliminarlo
    //- Si saca 0 pierde turno y paga a los demás
    //- Tiro inicial que no cuentan los 5 ceros para penalización, sino con que saques 1 Punto metás ficha
    //- El orden de las fichas que entraron
    //- Si saca 5 trues que avance 10
    /**
     *
     * @param token
     * @return
     */
    public void moverToken(Token token) {
//        Establecer quien esta lanzando los dados
//FALTA: ESTABLECER QUE TIPO DE CASILLA CALLO, PARA PAGAR, ELIMINAR FICHA O REGRESARLA
        int coin = throwDice(5);
        switch (coin) {
            case 0 ->
                payEveryoneDouble();
            case 1 -> {
                //Verificar que ficha esta por ganar, aplicar el turno a las fichas
                //Si ya estan todas tienes que moverla de 1 en 1
                getCurrentPlayer().nextToken();
                if (board.isTokenAboutToWin(token, token.getActualPosition() + 1)) {
                    board.goalToken(token, token.getActualPosition() + 1);
                } else {
                    board.move(token, token.getActualPosition() + 1);
                }
            }
            case 2 -> {
                getCurrentPlayer().nextToken();
                if (board.isTokenAboutToWin(token, token.getActualPosition() + 2)) {
                    board.goalToken(token, token.getActualPosition() + 2);
                } else {
                    board.move(token, token.getActualPosition() + 2);
                }
            }
            case 3 -> {
                getCurrentPlayer().nextToken();
                if (board.isTokenAboutToWin(token, token.getActualPosition() + 3)) {
                    board.goalToken(token, token.getActualPosition() + 3);
                } else {
                    board.move(token, token.getActualPosition() + 3);
                }
            }
            case 4 -> {
                getCurrentPlayer().nextToken();
                if (board.isTokenAboutToWin(token, token.getActualPosition() + 4)) {
                    board.goalToken(token, token.getActualPosition() + 4);
                } else {
                    board.move(token, token.getActualPosition() + 4);
                }
            }
            case 5 -> {
                getCurrentPlayer().nextToken();
                if (board.isTokenAboutToWin(token, token.getActualPosition() + 10)) {
                    board.goalToken(token, token.getActualPosition() + 10);
                } else {
                    board.move(token, token.getActualPosition() + 10);
                }
            }
            default ->
                throw new AssertionError();
        }

        System.out.println("La ficha del jugador: " + token.getOwner().getName() + " Se movera: " + coin + " Casillas");
        System.out.println("La ficha esta en: " + token.getActualPosition());
    }

    /**
     * Método para obtener la casilla inicial de x jugador
     *
     * @return
     */
    private int getCurrentPlayerStartPos() {
        final int boardSize = board.getBoardSize();
        final int sectionSquares = boardSize / 4;
        final int blades = 4;

        return boardSize - sectionSquares * (1 + (blades - (turn + 1)));
    }

    //Si cae en una casilla de triangulo
    private void payEveryoneDouble() {
        for (Player player : players) {
            if (!player.equals(getCurrentPlayer())) {
                for (int i = 0; i < 2; i++) {
                    pay(player);
                }
            }
        }
    }

    private void removePlayerAndAdvance() {
        removePlayer(getCurrentPlayer());
        advanceTurn();
    }

    public Player getCurrentPlayer() {
        return players.get(turn-1);
    }

    private void pay(final Player player) {
        final Player currentPlayer = getCurrentPlayer();

        currentPlayer.setBag(currentPlayer.getBag() - bet);

        player.setBag(player.getBag() + bet);

        if (currentPlayer.isBroke()) {
            removePlayerAndAdvance();
        } else {
            advanceTurn();
        }
    }

    // Si un jugador no puede seguir jugando ya sea por:
    //Saber si un jugador ya saco todas las fichas
    //Saber si un jugador perdio las fichas
    //Saber si un jugador se quedo sin dinero
    //Saber si aún tiene fichas para seguir metiendo al tablero
    //Seleccionar el token y pagar la apuesta a los demás jugadores por seleccionarlo
    public void losers() {

    }

    public void decideWinner() {

    }

    //Aplicar el orden aleatoreo inicial a los jugadores
    public void shufflePlayers() {
        Collections.shuffle(players);
    }

//NOTAS:
    /**
     * Si gana 1 jugador (porque saco su mayor numero de fichas primero)
     * Mientras los demás compiten por el segundo lugar y el segundo saca la
     * misma cantidad de fichas entonces recive el cobro de la apuesta -un
     * jugador (El que termino antes) Si tienes todas las fichas en el centro, y
     * te caen encima, es instakill?
     */
}

//        private static Game instance;
//
//    /**
//     * Singleton pattern to keep a single instance of this class program running
//     *
//     * @return The instance of the program is returned, if there's none a new one is created
//     */
//    public static Game getInstance() {
//        if (instance == null) {
//            instance = new Game();
//        }
//
//        return instance;
//    }
//
//    private Game (){
//    }
//
//    private final ArrayList<Player> players = new ArrayList<>();
//
//    private final Board board = Board.getInstance();
//
//    private int bet;
//
//    private int turn = 0;
//
//    public void run() {
//        init();
//    }
//
//    private void init() {
//        board.init();
//    }
//
//    public void addPlayer(final Player player) {
//        players.add(player);
//    }
//
//    private void removePlayer(final Player player) {
//        board.removeTokensFromPlayer(player);
//        players.remove(player);
//    }
//
//    public void shufflePlayers() {
//        Collections.shuffle(players);
//    }
//
//    private void insertToken() {
//        final Player player = getCurrentPlayer();
//        final Token token = player.createAndAssignToken(getCurrentPlayerStartPos());
//
//        board.insertTokenAtPos(token, token.getInitialPos());
//    }
//
//    public Token selectToken(final int index) {
//        return getCurrentPlayer().getToken(index);
//    }
//
//    public void playToken(final Token token) {
//        final int successes = countCoins(5);
//        Token selectedToken = token;
//
//        if (successes == 0) {
//            // p a y
//            payEveryone();
//            return;
//        }
//
//        if (selectedToken == null) {
//            if (!getCurrentPlayer().hasTokensInPlay() && successes > 0) {
//                insertToken();
//                advanceTurn();
//                return;
//            }
//
//            if (!playerHasFinished()) {
//                removePlayerAndAdvance();
//                return;
//            }
//
//            selectedToken = selectToken(getCurrentPlayer().getCurrentToken());
//        } else {
//            payEveryone();
//        }
//        
//        final int nextPos = board.getTokenPos(selectedToken) + getSpacesToMove(successes);
//
//        final Space nextSpace = board.getSpace(nextPos);
//
//        if (spaceHasSameOwnerAsToken(nextPos)) {
//            moveToken(selectedToken, nextSpace, nextPos);
//        } else {
//            landOnToken(selectedToken, nextSpace, nextPos);
//        }
//
//        selectedToken.getOwner().selectNextToken();
//    }
//
//    private void removePlayerAndAdvance() {
//        removePlayer(getCurrentPlayer());
//        advanceTurn();
//    }
//
//    private boolean spaceHasSameOwnerAsToken(final int pos) {
//        return board.getSpace(pos).getOwner() == getCurrentPlayer();
//    }
//
//    private int getSpacesToMove(final int successes) {
//        switch (successes) {
//            case 1 -> {
//                return 1;
//            }
//            case 2 -> {
//                return 2;
//            }
//            case 3 -> {
//                return 3;
//            }
//            case 4 -> {
//                return 4;
//            }
//            case 5 -> {
//                return 5;
//            }
//        }
//
//        return 0;
//    }
//
//    private void moveToken(final Token token, final Space nextSpace, final int nextPos) {
//        if (board.willTokenFinish(token, nextPos)) {
//            payEveryonePays();
//
//            board.removeTokenAtPos(token, nextPos);
//            board.markTokenAsFinished(token);
//
//            advanceTurn();
//
//            return;
//        }
//
//        board.moveTokenToPos(token, nextPos);
//
//        if (nextSpace instanceof ExteriorSpace) {
//        } else if (nextSpace instanceof TriangleSpace) {
//            payEveryoneDouble();
//        } else if (nextSpace instanceof CentralSpace) {
//            advanceTurn();
//        } else if (nextSpace instanceof SquareSpace) {
//            advanceTurn();
//        }
//    }
//
//    private void landOnToken(final Token token, final Space nextSpace, final int nextPos) {
//        if (board.willTokenFinish(token, nextPos)) {
//            payEveryonePays();
//
//            board.removeTokenAtPos(token, nextPos);
//            board.markTokenAsFinished(token);
//
//            advanceTurn();
//
//            return;
//        }
//        
//        if (nextSpace instanceof TriangleSpace) {
//            advanceTurn();
//        } else if (nextSpace instanceof CentralSpace) {
//            board.removeTokenAtPos(token, nextPos);
//
//            board.moveTokenToPos(token, nextPos);
//
//            advanceTurn();
//        } else if (nextSpace instanceof SquareSpace) {
//            advanceTurn();
//        }
//    }
//
//    private boolean playerHasFinished() {
//        if (getCurrentPlayer().getTokensCount() == 6) {
//            for (Token token : getCurrentPlayer().getTokens()) {
//                if (token.getCurrentPos() >= 0) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    private int getPlayerCount() {
//        return players.size();
//    }
//
//    private Player getCurrentPlayer() {
//        return players.get(turn);
//    }
//
//    private int getCurrentPlayerStartPos() {
//        final int boardSize = board.getBoardSize();
//        final int sectionSquares = boardSize / 4;
//        final int blades = 4;
//
//        return boardSize - sectionSquares * (1 + (blades - (turn + 1)));
//    }
//
//    private void advanceTurn() {
//        turn++;
//
//        if (turn >= getPlayerCount()) {
//            turn = 0;
//        }
//    }
//
//    private int countCoins(final int amount) {
//        Random random = new Random();
//        int successes = 0;
//
//        for (int i = 0; i < amount; i++) {
//            if (random.nextBoolean()) {
//                successes++;
//            }
//        }
//
//        return successes;
//    }
//
//    private void pay(final Player player) {
//        final Player currentPlayer = getCurrentPlayer();
//
//        currentPlayer.setBalance(currentPlayer.getBalance() - bet);
//
//        player.setBalance(player.getBalance() + bet);
//
//        if (currentPlayer.isBroke()) {
//            removePlayerAndAdvance();
//        } else {
//            advanceTurn();
//        }
//    }
//
//    private void payEveryone() {
//        for (Player player : players) {
//            if (!player.equals(getCurrentPlayer())) {
//                pay(player);
//            }
//        }
//    }
//
//    private void payEveryoneDouble() {
//        for (Player player : players) {
//            if (!player.equals(getCurrentPlayer())) {
//                for (int i = 0; i < 2; i++) {
//                    pay(player);
//                }
//            }
//        }
//    }
//
//    private void payEveryonePays() {
//        for (Player player : players) {
//            if (player.equals(getCurrentPlayer())) {
//                pay(getCurrentPlayer());
//            }
//        }
//    }

