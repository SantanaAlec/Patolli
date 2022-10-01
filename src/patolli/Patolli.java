package patolli;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alec_
 *
 * ESTABLER UN LIMITE DE APUESTA A 1/3 MAX Cuando te caen encima en el centro,
 * se elimina, no puedes usar esa ficha de nuevo quitamos tu max de fichas a -1
 * Para todos los momentos de "PAGA APUESTA" es con el monto fijo inicial, nunca
 * se mueve hasta que acaba la partida
 */
public class Patolli {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //game();
        //Pruebas main
        Player player1 = new Player("Alec", Color.BLUE, 1000);
        Player player2 = new Player("Demian", Color.GREEN, 1000);
        Player player3 = new Player("Santana", Color.RED, 1000);
        Player player4 = new Player("Celaya", Color.MAGENTA, 1000);

        ArrayList<Player> jug = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        board = new Board(1000, 3, players);
    }

    private static int code;
    //private static Player jugador;
    private static ArrayList<Player> players = new ArrayList<>();
    //private Board tablero;
    private static int budget = 0;
    private static int tokens = 2;
    private static int bet;
    private static boolean[] dice = new boolean[5]; //True es punto, False es liso
    private static Board board;

    /**
     * Avanzas el numero de puntos que te salgan: Si son 5 puntos avanzas 10
     * casillas Si son 5 lisas pierdes turno y pagas bet Cuando sacas una ficha,
     * le cobras la bet de los jugadores y repites turno Si caes en casillas
     * exteriores, repite turno Si caes en casillas de triangulo pagas doble bet
     */
//    public static void game() {
//        setBudget(100);
//        
////        Player jugador1 = new Player("Jose", new Color(105, 55, 43), tokens, budget);
////        Player jugador2 = new Player("Alec", Color.BLUE, tokens, budget);
//        
////        agregarJugador(jugador1);
////        agregarJugador(jugador2);
//        
//        if(players.size() >= 1 && players.size() <= 4){
//            //Si solo hay 1 espera a que se unan más, según el max configurado de la sala
//            
//            //Gráfico
//             players.get(0).setBet(30);
//             
//            betting( players.get(0).getBet());
//
//            for (Player jugador : players) {
//                jugador.toString();
//            }
//        }
//    }

    /**
     * 
     * @param apuesta 
     */
    public static void betting(int apuesta) {
        setBet(apuesta); //betAmount = Integer.parseInt( betInput.getText());

        for (Player jugador : players) {
            jugador.setBag(jugador.getBag() - getBet());//Agarrar el budget que ya tenian y restarle la bet 
        }
    }

    public static void agregarJugador(Player jugador) {
        players.add(jugador);
    }

    //Método que lanza los dados
    public static int lanzarDados(final int throws_) {
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

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Patolli.players = players;
    }

    public static int getBet() {
        return bet;
    }

    public static void setBet(int bet) {
        Patolli.bet = bet;
    }

    public static int getBudget() {
        return budget;
    }

    public static void setBudget(int budget) {
        Patolli.budget = budget;
    }

    public static int getTokens() {
        return tokens;
    }

    public static void setTokens(int tokens) {
        Patolli.tokens = tokens;
    }

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(Board board) {
        Patolli.board = board;
    }

    //Métodos nuevos
    public void betWinner(Token token) {
        //Saber que token sale, obtener al jugador ganador, obtener la bolsa de los demás, restarle la apuesta y sumar la suma del total al ganador
    }

    //Comparar los -2 de las fichas para establecer al ganador
    //- Si cae en una casilla ya ocupada por alguien más
    //- Si cae encima de alguien más en el CENTRO para eliminarlo
    //- Si saca 0 pierde turno y paga a los demás
    //- Tiro inicial que no cuentan los 5 ceros para penalización, sino con que saques 1 Punto metás ficha
    //- El orden de las fichas que entraron
    //- Si saca 5 trues que avance 10
////    public int moverToken(Token token) {
////        //Establecer quien esta lanzando los dados
////        switch (Patolli.lanzarDados(5)) {
////            case 0 ->
////                playersDemand(token);
////            case 1 ->
////                token.setPosition(token.getPosition() + 1); //Tratar de arreglar la posición para que funcione como Space
////            case 2 ->
////                token.setPosition(token.getPosition() + 2);
////            case 3 ->
////                token.setPosition(token.getPosition() + 3);
////            case 4 ->
////                token.setPosition(token.getPosition() + 4);
////            case 5 ->
////                token.setPosition(token.getPosition() + 10);
////            default ->
////                throw new AssertionError();
////
////        
////    
////
////    return numero de caso;
////        }
////    }
    
    //Si cae en una casilla de triangulo
////       public void playersDemand(Token token) {
////        for (Player player : Patolli.getPlayers()) {
////            if (player.equals(token.getOwner())) {
////                player.setBag(player.getBag() - ((Patolli.getBet() * 2) * (Patolli.getPlayers().size() - 1)));
////                if (player.getBag() <= 0) {
////                    //Establecer que perdio el juego y ponerlo en gris pero que continue para los demás
////                }
////            } else {
////                player.setBag(player.getBag() + ((Patolli.getBet() * 2)));
////            }
////        }
////    }

    //Avanzar turno para saber a que jugador de la lista le toca el turno
    // Si un jugador no puede seguir jugando ya sea por:
    //Saber si un jugador ya saco todas las fichas
    //Saber si un jugador perdio las fichas
    //Saber si un jugador se quedo sin dinero
    //Saber si aún tiene fichas para seguir metiendo al tablero
    //Seleccionar el token y pagar la apuesta a los demás jugadores por seleccionarlo
    //Aplicar el orden aleatoreo inicial a los jugadores
}
