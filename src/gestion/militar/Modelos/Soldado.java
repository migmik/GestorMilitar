package gestion.militar.Modelos;

public class Soldado extends Persona {

    public Soldado(String dni, String apellido, String nombre) {
        super(dni, apellido, nombre);
    }

    public Soldado(int codigo, String dni, String apellido, String nombre) {
        super(codigo, dni, apellido, nombre);
    }

    @Override
    public String toString() {
        return mostrarInfo();
    }

    @Override
    public String mostrarInfo() {
        return Soldado.class.getSimpleName() + super.toString();

    }

}
