package mx.alexramos.banco;

import java.math.BigDecimal;
import java.util.Scanner;

/*
Programa que pide introducir una cantidad en pesos y devuelve la conversión a dólares.
El mensaje con el resultado será: *** pesos equivalen a *** dólares
*/
public class Conversor {
    public static void main(String[] args) {

        System.out.println("Bienvenido al conversor de moneda");
        System.out.println("Por favor, introduzca una cantidad en pesos y el programa devolverá la conversión a dólares");

        Scanner scanner = new Scanner(System.in);
        double pesos = scanner.nextDouble();

        double dolares = 0.041 * pesos;

        System.out.println(pesos + " pesos equivalen a " + dolares + " dólares");

        String pesosString = Double.toString(pesos);
        BigDecimal pesosBigDecimal = new BigDecimal(pesosString);
        double tasaDeCambio = 0.041;
        String tasaDeCambioString = Double.toString(tasaDeCambio);
        BigDecimal tasaDeCambioBigDecimal = new BigDecimal(tasaDeCambioString);

        BigDecimal dolaresBigDecimal = pesosBigDecimal.multiply(tasaDeCambioBigDecimal);

        System.out.println(pesos + " euros equivalen a " + dolaresBigDecimal.toString() + " dólares");
    }
}
