package gestion.militar.Vistas;

import java.util.Scanner;

public class MenuPrincipal extends MenuBase {
    private final MenuAsignaciones menuAsignaciones;
    private final MenuConsultas menuConsultas;
    private final MenuOficiales menuOficiales;
    private final MenuReservas menuReservas;
    private final MenuSoldados menuSoldados;
    private final MenuCuarteles menuCuarteles;

    public MenuPrincipal(Scanner scanner, MenuAsignaciones menuAsignaciones,
            MenuConsultas menuConsultas, MenuOficiales menuOficiales, MenuReservas menuReservas,
            MenuSoldados menuSoldados, MenuCuarteles menuCuarteles) {
        super(scanner);
        this.menuAsignaciones = menuAsignaciones;
        this.menuConsultas = menuConsultas;
        this.menuOficiales = menuOficiales;
        this.menuReservas = menuReservas;
        this.menuSoldados = menuSoldados;
        this.menuCuarteles = menuCuarteles;
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        System.out.println("=== Gestion Militar ===");
        while (!salir) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Gestionar Soldados");
            System.out.println("2. Gestionar Oficiales");
            System.out.println("3. Gestionar Cuarteles");
            System.out.println("4. Asignaciones");
            System.out.println("5. Reservas");
            System.out.println("6. Consultas");
            System.out.println("0. Salir");
            switch (leerOpcion("Ingrese opción: ")) {
                case 1:
                    menuSoldados.mostrar();
                    break;
                case 2:
                    menuOficiales.mostrar();
                    break;
                case 3:
                    menuCuarteles.mostrar();
                    break;
                case 4:
                    menuAsignaciones.mostrar();
                    break;
                case 5:
                    menuReservas.mostrar();
                    break;
                case 6:
                    menuConsultas.mostrar();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion inválida.");
                    break;
            }
        }
    }

}
