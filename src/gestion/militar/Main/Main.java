package gestion.militar.Main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gestion.militar.BaseDeDatos.ConfiguracionBD;
import gestion.militar.Controladores.SoldadosControlador;
import gestion.militar.DAOS.CuartelDAO;
import gestion.militar.DAOS.OficialDAO;
import gestion.militar.DAOS.SoldadoDAO;
import gestion.militar.Modelos.Cuartel;
import gestion.militar.Modelos.Oficial;
import gestion.militar.Modelos.Soldado;
import gestion.militar.Vistas.MenuAsignaciones;
import gestion.militar.Vistas.MenuConsultas;
import gestion.militar.Vistas.MenuCuarteles;
import gestion.militar.Vistas.MenuOficiales;
import gestion.militar.Vistas.MenuPrincipal;
import gestion.militar.Vistas.MenuReservas;
import gestion.militar.Vistas.MenuSoldados;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // conexion
        ConfiguracionBD configuracionBD = new ConfiguracionBD();
        Connection conexion = configuracionBD.conexion();
        // daos
        SoldadoDAO soldadoDAO = new SoldadoDAO(conexion);
        // controladores
        SoldadosControlador soldadosControlador = new SoldadosControlador(soldadoDAO);
        // vistas
        MenuAsignaciones menuAsignaciones = new MenuAsignaciones(scanner);
        MenuConsultas menuConsultas = new MenuConsultas(scanner);
        MenuCuarteles menuCuarteles = new MenuCuarteles(scanner);
        MenuOficiales menuOficiales = new MenuOficiales(scanner);
        MenuReservas menuReservas = new MenuReservas(scanner);
        MenuSoldados menuSoldados = new MenuSoldados(scanner, soldadosControlador);
        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner, menuAsignaciones, menuConsultas, menuOficiales,
                menuReservas, menuSoldados, menuCuarteles);

        menuPrincipal.mostrar();
        scanner.close();
    }
}