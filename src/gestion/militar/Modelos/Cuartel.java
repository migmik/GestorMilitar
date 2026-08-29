package gestion.militar.Modelos;

public class Cuartel {
    private int codigo;
    private String nombre;
    private int capacidad;

    public Cuartel(int codigo, String nombre, int capacidad) {
        this.codigo = codigo;
        setNombre(nombre);
        setCapacidad(capacidad);
    }

    public Cuartel(String nombre, int capacidad) {
        setNombre(nombre);
        setCapacidad(capacidad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Cuartel [nombre=" + nombre + ", codigo=" + codigo + ", capacidad=" + capacidad + "]";
    }

}
