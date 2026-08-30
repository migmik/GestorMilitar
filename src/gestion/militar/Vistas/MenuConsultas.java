package gestion.militar.Vistas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gestion.militar.Controladores.AsignacionesControlador;
import gestion.militar.Controladores.ReservasControlador;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Asignacion;
import gestion.militar.Modelos.Reserva;

public class MenuConsultas extends MenuBase {

    ReservasControlador reservasControlador;
    AsignacionesControlador asignacionesControlador;

    public MenuConsultas(Scanner scanner, ReservasControlador reservasControlador,
            AsignacionesControlador asignacionesControlador) {
        super(scanner);
        this.reservasControlador = reservasControlador;
        this.asignacionesControlador = asignacionesControlador;
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
            switch (super.leerOpcion("Ingrese opcion: ")) {
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
        try {// Consultar soldado con su cuartel asignado
            Reserva reserva = reservasControlador.consultarPorCodigoSoldado(codigoSoldado);
            System.out.println("Resultado: \n" + reserva.getSoldado().mostrarInfo());
            System.out.println("\n" + reserva.getCuartel().toString());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error tecnico: " + e.getMessage());
        }
    }

    private void soldadosPorOficial() {
        int codigoOficial = leerEntero("Ingrese codigo del oficial: ");
        List<Reserva> reservasDelCuartel = new ArrayList<>();
        try {// Consultar soldados supervisados por un oficial
            Asignacion asignacion = asignacionesControlador.consultarPorCodigoOficial(codigoOficial);
            reservasDelCuartel = reservasControlador.listarReservasPorCuartel(asignacion.getCuartel().getCodigo());
            System.out.println("Soldados supervisados por el oficial " + asignacion.getOficial().mostrarInfo());
            for (Reserva reserva : reservasDelCuartel) {
                System.out.println(reserva.getSoldado().mostrarInfo());
            }
            if (reservasDelCuartel.isEmpty()) {
                System.out.println("El oficial no tiene soldados a su cargo");
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error tecnico: " + e.getMessage());
        }
    }

    private void oficialDeSoldado() {
        int codigoSoldado = leerEntero("Ingrese codigo del soldado: ");

        try {// Consultar oficial asignado a un soldado
            Reserva reserva = reservasControlador.consultarPorCodigoSoldado(codigoSoldado);
            Asignacion asignacion = asignacionesControlador
                    .consultarAsignacionPorCodigoDeCuartel(reserva.getCuartel().getCodigo());
            System.out.println("Oficial asignado al soldado " + reserva.getSoldado().mostrarInfo());
            System.out.println("\n" + asignacion.getOficial().mostrarInfo());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error tecnico: " + e.getMessage());
        }
    }
}
