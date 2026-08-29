package gestion.militar.Vistas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import gestion.militar.Controladores.ReservasControlador;
import gestion.militar.Excepciones.CapacidadExcedidaException;
import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Reserva;

public class MenuReservas extends MenuBase {

    ReservasControlador reservasControlador;

    public MenuReservas(Scanner scanner, ReservasControlador reservasControlador) {
        super(scanner);
        this.reservasControlador = reservasControlador;
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Reservas ---");
            System.out.println("1. Ingresar reserva");
            System.out.println("2. Modificar reserva");
            System.out.println("3. Consultar reserva por código de soldado");
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
            reservasControlador.ingresar(codigoSoldado, codigoCuartel);
            System.out.println("Reserva realizada con exito.");
        } catch (EntidadNoEncontradaException | CapacidadExcedidaException | EntidadDuplicadaException e) {
            System.out.println("No se pudo reservar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo del soldado para modificar su reserva: ");
        int codigoCuartel = leerEntero("Ingrese  codigo del nuevo cuartel: ");
        try {
            reservasControlador.modificar(codigo, codigoCuartel);
            System.out.println("Reserva modificada con exito.");
        } catch (EntidadNoEncontradaException | CapacidadExcedidaException | EntidadDuplicadaException e) {
            System.out.println("No se pudo modificar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo del soldado para consultar la reserva: ");
        try {
            Reserva reserva = reservasControlador.consultarPorCodigoSoldado(codigo);
            System.out.println("Reserva encontrada:\n " + reserva.toString());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void listar() {
        List<Reserva> reservas = new ArrayList<>();
        try {
            reservas = reservasControlador.listarTodos();
            System.out.println("Lista de reservas:\n");
            reservas.forEach(System.out::println);
            if (reservas.isEmpty())
                System.out.println("No hay reservas registradas.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo del soldado para eliminar la reserva: ");
        try {
            reservasControlador.eliminar(codigo);
            System.out.println("Reserva eliminada con exito.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error técnico: " + e.getMessage());
        }
    }
}