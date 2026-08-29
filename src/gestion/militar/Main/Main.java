package gestion.militar.Main;

import java.sql.Connection;
import java.util.Scanner;

import gestion.militar.BaseDeDatos.ConfiguracionBD;
import gestion.militar.Controladores.AsignacionesControlador;
import gestion.militar.Controladores.CuartelesControlador;
import gestion.militar.Controladores.OficialesControlador;
import gestion.militar.Controladores.ReservasControlador;
import gestion.militar.Controladores.SoldadosControlador;
import gestion.militar.DAOS.AsignacionDAO;
import gestion.militar.DAOS.CuartelDAO;
import gestion.militar.DAOS.OficialDAO;
import gestion.militar.DAOS.ReservaDAO;
import gestion.militar.DAOS.SoldadoDAO;
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
                Connection conexion = ConfiguracionBD.conexion();
                // daos
                OficialDAO oficialDAO = new OficialDAO(conexion);
                SoldadoDAO soldadoDAO = new SoldadoDAO(conexion);
                CuartelDAO cuartelDAO = new CuartelDAO(conexion);
                ReservaDAO reservaDAO = new ReservaDAO(conexion);
                AsignacionDAO asignacionDAO = new AsignacionDAO(conexion);
                // controladores
                OficialesControlador oficialesControlador = new OficialesControlador(oficialDAO);
                SoldadosControlador soldadosControlador = new SoldadosControlador(soldadoDAO);
                CuartelesControlador cuartelesControlador = new CuartelesControlador(cuartelDAO, reservaDAO);
                ReservasControlador reservasControlador = new ReservasControlador(reservaDAO, soldadosControlador,
                                cuartelesControlador);
                AsignacionesControlador asignacionesControlador = new AsignacionesControlador(asignacionDAO,
                                oficialesControlador, cuartelesControlador);
                // vistas
                MenuAsignaciones menuAsignaciones = new MenuAsignaciones(scanner, asignacionesControlador);
                MenuConsultas menuConsultas = new MenuConsultas(scanner, reservasControlador, asignacionesControlador);
                MenuCuarteles menuCuarteles = new MenuCuarteles(scanner, cuartelesControlador);
                MenuOficiales menuOficiales = new MenuOficiales(scanner, oficialesControlador);
                MenuReservas menuReservas = new MenuReservas(scanner, reservasControlador);
                MenuSoldados menuSoldados = new MenuSoldados(scanner, soldadosControlador);
                MenuPrincipal menuPrincipal = new MenuPrincipal(scanner, menuAsignaciones, menuConsultas, menuOficiales,
                                menuReservas, menuSoldados, menuCuarteles);

                menuPrincipal.mostrar();
                scanner.close();
        }
}
