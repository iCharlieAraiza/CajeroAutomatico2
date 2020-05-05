package mx.alexramos.cajero;

import java.util.Scanner;

/*
Programa que emula la interfaz de un cajero automático.



El programa pedirá al usuario su número de identificación

Una vez introducido pedirá al usuario su contraseña



Tras esto el programa presentará las siguientes opciones al usuario de forma indefinida hasta que éste introduzca un número diferente a los indicados:

Elija una de las siguientes opciones:
Escriba 1 para consultar su saldo
Escriba 2 para ingresar dinero
Escriba 3 para sacar dinero
Escriba 4 para consultar sus últimos movimientos
Para salir escriba cualquier otro número
Una vez escrita la opción, pulse la tecla Enter

Se creará un objeto de tipo CajeroAutomático y una vez el usuario ha elegido la opción, se llamará al método correspondiente de este objeto.
*/
public class Interfaz {
    public static void main(String[] args) {

        String identificador, contraseña;
        Scanner scanner = new Scanner(System.in);
        boolean valido = false;

        do{
            System.out.println("Por favor introduzca su número de identificación");
            identificador = scanner.nextLine();
            System.out.println("Por favor introduzca su contraseña");
            contraseña = scanner.nextLine();

            if(CajeroAutomatico.esValido(identificador, contraseña))
                valido = true;
            else
                System.out.println("¡Ups! Has ingresado un número de identificación o contraseña incorrecto. Vuelve a intetarlo\n");
        }while(!valido);

        CajeroAutomatico cajero = new CajeroAutomatico(identificador, contraseña);

        int opcionSeleccionada;
        do {
            System.out.println("Elija una de las siguientes opciones:");
            System.out.println("   Escriba 1 para consultar su saldo");
            System.out.println("   Escriba 2 para ingresar dinero");
            System.out.println("   Escriba 3 para sacar dinero");
            System.out.println("   Escriba 4 para consultar sus últimos movimientos");
            System.out.println("   Para salir escriba cualquier otro número");

            opcionSeleccionada = scanner.nextInt();

            switch (opcionSeleccionada) {
                case 1:
                    cajero.mostrarSaldo();
                    break;
                case 2:
                    cajero.ingresarDinero();
                    break;
                case 3:
                    cajero.sacarDinero();
                    break;
                case 4:
                    cajero.consultarUltimosMovimientos();
                    break;
                default:
                    cajero.salir();
            }
        } while (opcionSeleccionada == 1 || opcionSeleccionada == 2 || opcionSeleccionada == 3 || opcionSeleccionada == 4);

    }
}