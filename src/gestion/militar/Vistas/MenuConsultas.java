package gestion.militar.Vistas;

import java.util.Scanner;

public class MenuConsultas extends MenuBase {

    public MenuConsultas(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Consultas ---");
            System.out.println("1. Consultar soldado con su cuartel asignado");
            System.out.println("2. Consultar soldados supervisados por un oficial");
            System.out.println("3. Consultar oficial asignado a un soldado");
            System.out.println("0. Volver");
            switch (super.leerOpcion("Ingrese opción: ")) {
                case 1:
                    soldadoConCuartel();
                    break;
                case 2:
                    soldadosPorOficial();
                    break;
                case 3:
                    oficialDeSoldado();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Volviendo...");
                    break;
                default:
                    break;
            }
        }
    }

    private void soldadoConCuartel() {
        int codigoSoldado = leerEntero("Ingrese codigo del soldado: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void soldadosPorOficial() {
        int codigoOficial = leerEntero("Ingrese codigo del oficial: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void oficialDeSoldado() {
        int codigoSoldado = leerEntero("Ingrese codigo del soldado: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}