package mx.alexramos.cajero;

import mx.alexramos.banco.CuentaBancaria;
import mx.alexramos.mock.SimuladorDeMovimientos;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/*

Clase que emula las funcionalidades de un cajero automático.

La clase tendrá una variable de instancia llamada cuentaBancariaActual de tipo CuentaBancaria

La clase tendrá un constructor que tendrá como parámetros un String que identifica al usuario y otro String que contiene una contraseña.

Como se trata de una emulación, en lugar de conectar con la base de datos del banco para que nos devuelva la cuenta bancaria del usuario lo que vamos a hacer es crear un objeto de la clase CuentaBancaria con un nombre cualquiera y un saldo aleatorio

Esta clase tendrá los siguientes métodos:



mostrarSaldo() -> Mostrará por pantalla el saldo de cuentaBancariaActual

ingresarDinero() -> Preguntará al usuario cuánto dinero quiere ingresar y lo ingresará en cuentaBancariaActual

sacarDinero() -> Preguntará al usuario cuánto dinero quiere sacar y lo sacará de cuentaBancariaActual

consultarUltimosMovimientos -> Este método de momento no hará nada

salir() -> Dará las gracias al usuario por usar sus servicios



*/
public class CajeroAutomatico {

    private CuentaBancaria cuentaBancariaActual;
    static public String directorioBase = System.getProperty("user.dir");
    private int cont;
    private  String direccionDeArchivoMovimientos;
    ArrayList<ArrayList<String>> listaDeMovimientos = new ArrayList<>();

    protected CajeroAutomatico(String identificador, String contraseña) {
        cuentaBancariaActual = new CuentaBancaria("Nombre cualquiera", calcularSaldo(identificador) );
    }

    static public boolean esValido(String identificador, String contraseña){
        try {
            Scanner entrada = new Scanner(new File(directorioBase + "/files/clientes.txt"));
            while (entrada.hasNextLine()) {
                String linea = entrada.nextLine();
                String[] partes = linea.split(":");

                if(identificador.equals(partes[0])){
                    if(contraseña.equals(partes[1]))
                        return true;
                }
            }
            entrada.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private double calcularSaldo(String identificador){
        double saldo = 0.0;
        String directorioBase = System.getProperty("user.dir");
        this.cont = 0;
        try {
            direccionDeArchivoMovimientos = directorioBase + "/files/"+identificador+".txt";

            Scanner entrada = new Scanner(new File(direccionDeArchivoMovimientos));
            while (entrada.hasNextLine()) {
                listaDeMovimientos.add(new ArrayList<>());

                String linea = entrada.nextLine();
                String[] partes = linea.split(":");
                saldo += Double.parseDouble(partes[2]);

                for (int i = 0; i < 3; i++)
                    listaDeMovimientos.get(cont).add(partes[i]);
                cont++;

            }
            entrada.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saldo;
    }

    protected void mostrarSaldo() {
        System.out.println("Su saldo es " + cuentaBancariaActual.obtenerSaldo());
    }

    protected void ingresarDinero() {
        System.out.println("¿Cuánto dinero quiere ingresar?");
        Scanner scanner = new Scanner(System.in);
        double cantidad = scanner.nextDouble();
        cuentaBancariaActual.ingresarDinero(cantidad);

        int cont = listaDeMovimientos.size();
        listaDeMovimientos.add(new ArrayList<>());
        listaDeMovimientos.get(cont).add( Integer.toString(cont+1) );
        listaDeMovimientos.get(cont).add("2");
        listaDeMovimientos.get(cont).add(Double.toString(cantidad));

    }

    protected void sacarDinero() {
        System.out.println("¿Cuánto dinero quiere sacar?");
        Scanner scanner = new Scanner(System.in);
        double cantidad = scanner.nextDouble();

        if(cantidad > cuentaBancariaActual.obtenerSaldo())
            System.out.println("Ups! No tienes saldo suficiente para retirar esa cantidad. \n");
        else{
            cuentaBancariaActual.sacarDinero(cantidad);

            int cont = listaDeMovimientos.size();
            listaDeMovimientos.add(new ArrayList<>());
            listaDeMovimientos.get(cont).add( Integer.toString(cont+1) );
            listaDeMovimientos.get(cont).add("3");
            listaDeMovimientos.get(cont).add(Double.toString(-cantidad));
        }

    }


    protected void consultarUltimosMovimientos() {
        SimuladorDeMovimientos generadorAleatorioDeMovimientos = new SimuladorDeMovimientos();
        System.out.println("¿Cuántos movimientos quiere consultar?");
        Scanner scanner = new Scanner(System.in);
        int numeroDeMovimientos = scanner.nextInt();

        numeroDeMovimientos = numeroDeMovimientos > listaDeMovimientos.size() ? listaDeMovimientos.size() : numeroDeMovimientos;

        int limite = listaDeMovimientos.size()-1;

        for(int i = limite; i > (limite - numeroDeMovimientos) ; i--){
            imprimirMovimiento(listaDeMovimientos.get(i).get(0), listaDeMovimientos.get(i).get(1), listaDeMovimientos.get(i).get(2));
        }

        System.out.println();
    }

    private void actualizarMovimientos(){
        String movimientos = "";

        for(int i = 0; i < listaDeMovimientos.size(); i++){
            movimientos = movimientos + listaDeMovimientos.get(i).get(0)+ ":"
                    +listaDeMovimientos.get(i).get(1)+":"
                    +listaDeMovimientos.get(i).get(2)+"\n";
        }


        try{

            FileWriter fw=new FileWriter(direccionDeArchivoMovimientos);
            fw.write(movimientos);
            fw.close();

        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }


    }

    protected void salir() {
        System.out.println("Muchas gracias por utilizar nuestros servicios");
        actualizarMovimientos();
    }

    public void imprimirMovimiento(String numeroDeMovimiento, String tipoDeMovimiento, String cantidad ){
        switch (Integer.parseInt(tipoDeMovimiento)) {
            case 1:
                tipoDeMovimiento = "TRANSFERENCIA";
                break;
            case 2:
                tipoDeMovimiento = "INGRESO";
                break;
            case 3:
                tipoDeMovimiento = "RETIRADA";
                break;
            case 4:
                tipoDeMovimiento = "PAGO_CON_TARJETA";
                break;
            case 5:
                tipoDeMovimiento = "NOMINA";
                break;
            default:
                tipoDeMovimiento = "PAGO_RECIBO";
        }
        String movimiento = numeroDeMovimiento + " " +  tipoDeMovimiento + " "+ String.format("%.2f", Double.parseDouble(cantidad) ) + " pesos";
        System.out.println(movimiento);

    }

}
