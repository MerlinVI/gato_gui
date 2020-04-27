/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato;

import java.util.HashMap;
import java.util.Map;

public class Gato {

    private static Boolean turno = true;
    private static String[][] tablero = new String[3][3];
    private static Map<Boolean, String> mapJugadores = new HashMap<>();
    private static Map<Boolean, String> mapIconos = new HashMap<>();
    private static Gato gatoSingleton;

    /**
     * Constructor por default, al ser un método privado se vuelve inaccesibles
     * desde otras clases
     */
    private Gato() {
    }

    /**
     * Constructor de la clase que recibe los nombres de los jugadores
     *
     * @param jugador1
     * @param jugador2
     */
    private Gato(String jugador1, String jugador2) {
        mapJugadores.put(true, jugador1);
        mapJugadores.put(false, jugador2);
        mapIconos.put(true, "O");
        mapIconos.put(false, "X");
    }

    /**
     * Método para inicializar la instancia de la clase con los nombres de los
     * jugadores, los inconos y el tablero en blanco
     *
     * @param jugador1
     * @param jugador2
     * @return
     */
    public static Gato getGato(String jugador1, String jugador2) {
        if (gatoSingleton == null) {
            gatoSingleton = new Gato(jugador1, jugador2);
            initTablero();
        }
        return gatoSingleton;
    }

    /**
     * Reinicia los valores del tablero y del turno para poder empezar un nuevo
     * juego
     */
    public static void restartGame() {
        turno = true;
        initTablero();
    }

    /**
     * Método para obtener el simbolo que se colocará en la posición
     * correspondiente
     *
     * @param i
     * @param j
     * @return String con el valor correspondiente al turno
     */
    public static String tirar(int i, int j) {
        if (!disponible(i, j)) {
            return "El espacio ya fue usado";
        }
        tablero[i][j] = mapIconos.get(turno);

        //Cambio de turno al siguiente jugador
        turno = !turno;
        return tablero[i][j];
    }

    /**
     * Método que valida si existe un ganador o si el tablero se ha quedado sin
     * espacios disponibles.
     *
     * Si se detecta un ganador (en las filas, las columnas o alguna de las
     * diagonales) se asume que el jugador del turno actual ha lanzado una
     * jugada ganadora y por tanto se le declara vencedor
     *
     * Si no existe un ganador se valida si todos los espacios han sido
     * ocupados, si el tablero se encuentra lleno y no existe un ganador (pues
     * la verficación anterior ha sido false) se declara un empate
     *
     * Si no existe un ganador y el tablero no se encuentra lleno, se anuncia el
     * turno del siguiente jugador
     *
     * @return
     */
    public static String validarJugada() {
        if (hayGanador()) {
            return "El ganador es " + mapJugadores.get(turno);
        } else if (isTableroLleno()) {
            return "¡¡Empate!!";
        } else {
            return "Turno de " + mapJugadores.get(!turno);
        }
    }

    /**
     * Valida si alguno de las casillas del table sigue disponible (null). Si
     * aún hay espacios returna false
     *
     * @return
     */
    private static boolean isTableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método que valida si la casilla seleccionada se encuentra disponible o no
     *
     * @param i
     * @param j
     * @return
     */
    private static boolean disponible(int i, int j) {
        return tablero[i][j] == null;
    }

    /**
     * Método que valida si el jugador actual ha lanzado una jugada ganadora
     *
     * @return
     */
    public static boolean hayGanador() {
        return tresEnVertical() || tresEnHorizontal() || tresEnDiagonal();
    }

    /**
     * Método que valida si en alguna de las 3 columnas las casillas han sido
     * ocupadas (diferente de null) y si los valores de éstas son el mismo
     *
     * @return
     */
    private static boolean tresEnVertical() {
        for (int i = 0; i < 3; i++) {
            if (tablero[0][i] != null && tablero[1][i] != null && tablero[1][i] != null
                    && (tablero[0][i].equals(tablero[1][i]) && tablero[0][i].equals(tablero[2][i]))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que valida si en alguna de las filas las casillas han sido
     * ocupadas (son diferente de null) y si los valores de éstas son el mismo
     *
     * @return
     */
    private static boolean tresEnHorizontal() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] != null && tablero[i][1] != null && tablero[i][2] != null
                    && (tablero[i][0].equals(tablero[i][1]) && tablero[i][0].equals(tablero[i][2]))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que valida si la diagonal principal o la diagonal secundaria han
     * sido ocupadas (son diferentes de null) y si los valores de éstas son el
     * mismo
     *
     * @return
     */
    private static boolean tresEnDiagonal() {
        boolean isDiagonalPrincipal = tablero[0][0] != null && tablero[1][1] != null && tablero[2][2] != null
                && (tablero[0][0].equals(tablero[1][1]) && tablero[0][0].equals(tablero[2][2]));

        boolean isDiagonalSecundaria = tablero[0][2] != null && tablero[1][1] != null && tablero[2][0] != null
                && (tablero[0][2].equals(tablero[1][1]) && tablero[0][2].equals(tablero[2][0]));

        return isDiagonalPrincipal || isDiagonalSecundaria;
    }

    /**
     * Inicializa el tablero en nulos
     */
    private static void initTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = null;
            }
        }
    }

}
