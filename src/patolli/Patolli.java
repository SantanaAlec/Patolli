package patolli;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alec_
 * 
 * ESTABLER UN LIMITE DE APUESTA A 1/3 MAX
 * Cuando te caen encima en el centro, se elimina, no puedes usar esa ficha de nuevo quitamos tu max de fichas a -1
 * Para todos los momentos de "PAGA APUESTA" es con el monto fijo inicial, nunca se mueve hasta que acaba la partida
 */
public class Patolli {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //game();
    }

    private static int code;
    //private static Player jugador;
    private static ArrayList<Player> players = new ArrayList<>();
    //private Board tablero;
    private static int budget = 0;
    private static int tokens = 2;
    private static int bet;
    private static boolean[] dice = new boolean[5]; //True es punto, False es liso

    /**
     * Avanzas el numero de puntos que te salgan: Si son 5 puntos avanzas 10
     * casillas Si son 5 lisas pierdes turno y pagas bet Cuando sacas una ficha,
     * le cobras la bet de los jugadores y repites turno Si caes en casillas
     * exteriores, repite turno Si caes en casillas de triangulo pagas doble bet
     */
    public static void game() {
        setBudget(100);
        
//        Player jugador1 = new Player("Jose", new Color(105, 55, 43), tokens, budget);
//        Player jugador2 = new Player("Alec", Color.BLUE, tokens, budget);
        
//        agregarJugador(jugador1);
//        agregarJugador(jugador2);
        
        if(players.size() >= 1 && players.size() <= 4){
            //Si solo hay 1 espera a que se unan más, según el max configurado de la sala
            
            //Gráfico
             players.get(0).setBet(30);
             
            betting( players.get(0).getBet());

            for (Player jugador : players) {
                jugador.toString();
            }
        }
    }
    public static void betting(int apuesta) {
        setBet(apuesta); //betAmount = Integer.parseInt( betInput.getText());

        for (Player jugador : players) {
            jugador.setBag(jugador.getBag() - getBet());//Agarrar el budget que ya tenian y restarle la bet 
        }
    }

    public static void agregarJugador(Player jugador) {
        players.add(jugador);
    }

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

}
