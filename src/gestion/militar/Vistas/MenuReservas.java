package gestion.militar.Vistas;

import java.util.Scanner;

public class MenuReservas extends MenuBase {

    public MenuReservas(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Reservas ---");
            System.out.println("1. Ingresar reserva");
            System.out.println("2. Modificar reserva");
            System.out.println("3. Consultar reserva por código");
            System.out.println("4. Listar todas las reservas");
            System.out.println("5. Eliminar reserva");
            System.out.println("0. Volver");
            switch (leerOpcion("Ingrese opción: ")) {
                case 1:
                    ingresar();
                    break;
                case 2:
                    modificar();
                    break;
                case 3:
                    consultar();
                    break;
                case 4:
                    listar();
                    break;
                case 5:
                    eliminar();
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

    private void ingresar() {
        int codigoSoldado = leerEntero("Ingrese codigo del soldado: ");
        int codigoCuartel = leerEntero("Ingrese codigo del cuartel: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo de reserva a modificar: ");
        int codigoSoldado = leerEntero("Ingrese nuevo codigo de soldado: ");
        int codigoCuartel = leerEntero("Ingrese nuevo codigo de cuartel: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo de reserva: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo de reserva a eliminar: ");

        try {
            // llamada al servicio
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}