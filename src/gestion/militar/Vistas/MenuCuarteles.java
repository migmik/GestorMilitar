package gestion.militar.Vistas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gestion.militar.Controladores.CuartelesControlador;
import gestion.militar.Enums.CamposCuartelEnum;
import gestion.militar.Excepciones.EntidadDuplicadaException;
import gestion.militar.Excepciones.EntidadNoEncontradaException;
import gestion.militar.Modelos.Cuartel;

public class MenuCuarteles extends MenuBase {

    CuartelesControlador cuartelesControlador;

    public MenuCuarteles(Scanner scanner, CuartelesControlador cuartelesControlador) {
        super(scanner);
        this.cuartelesControlador = cuartelesControlador;
    }

    @Override
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestion de Cuarteles ---");
            System.out.println("1. Ingresar cuartel");
            System.out.println("2. Modificar datos de cuartel");
            System.out.println("3. Consultar cuartel por codigo");
            System.out.println("4. Listar todos los cuarteles");
            System.out.println("5. Eliminar cuartel");
            System.out.println("0. Volver");
            switch (leerOpcion("Ingrese opcion: ")) {
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
        String nombre = leerTexto("Ingrese nombre del cuartel: ");
        int capacidad = leerEntero("Ingrese capacidad del cuartel: ");
        try {
            cuartelesControlador.ingresar(nombre, capacidad);
            System.out.println("Cuartel registrado con exito.");
        } catch (EntidadDuplicadaException | IllegalArgumentException e) {
            System.out.println("No se pudo registrar: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error tecnico: " + e.getMessage());
        }
    }

    private void modificar() {
        listar();
        int codigo = leerEntero("Ingrese codigo del cuartel a modificar: ");
        try {
            Cuartel cuartelExistente = cuartelesControlador.consultarPorCodigo(codigo);
            System.out.println("Modificacion del cuartel: " + cuartelExistente.toString());
            boolean salir = false;
            while (!salir) {
                System.out.println("\nQue dato desea modificar?");
                CamposCuartelEnum[] campos = CamposCuartelEnum.values();
                for (int i = 0; i < campos.length; i++) {
                    System.out.println((i + 1) + ". " + campos[i].getDescripcion());
                }
                System.out.println("0. Volver al menu anterior");
                int opcion = leerOpcion("Seleccione una opcion: ");

                if (opcion == 0) {
                    salir = true;
                } else if (opcion >= 1 && opcion <= campos.length) {
                    CamposCuartelEnum campoSeleccionado = campos[opcion - 1];
                    String nuevoValor = leerTexto(
                            "Ingrese el nuevo valor para " + campoSeleccionado.getDescripcion() + ": ");
                    try {
                        cuartelesControlador.modificar(codigo, campoSeleccionado, nuevoValor);
                        System.out.println("Campo actualizado con exito.");
                    } catch (RuntimeException e) {
                        System.out.println("No se pudo actualizar: " + e.getMessage());
                    }
                } else {
                    System.out.println("Opcion invalida");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void consultar() {
        int codigo = leerEntero("Ingrese codigo de cuartel: ");
        try {
            Cuartel cuartel = cuartelesControlador.consultarPorCodigo(codigo);
            System.out.println("Cuartel encontrado: \n" + cuartel.toString());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error tecnico: " + e.getMessage());
        }
    }

    private void listar() {
        List<Cuartel> cuarteles = new ArrayList<>();
        try {
            cuarteles = cuartelesControlador.listarTodos();
            System.out.println("Lista de cuarteles:\n");
            cuarteles.forEach(System.out::println);
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error tecnico: " + e.getMessage());
        }
    }

    private void eliminar() {
        int codigo = leerEntero("Ingrese codigo de cuartel a eliminar: ");
        try {
            cuartelesControlador.eliminar(codigo);
            System.out.println("Cuartel eliminado con exito.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error tecnico: " + e.getMessage());
        }
    }
}
