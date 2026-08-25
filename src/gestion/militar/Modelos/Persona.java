package gestion.militar.Modelos;

public abstract class Persona {
    private int codigo;
    private String dni;
    private String apellido;
    private String nombre;

    // cosntructor sin codigo id
    public Persona(String dni, String apellido, String nombre) {
        setDni(dni);
        setApellido(apellido);
        setNombre(nombre);
    }

    public Persona(int codigo, String dni, String apellido, String nombre) {
        this.codigo = codigo;
        setDni(dni);
        setApellido(apellido);
        setNombre(nombre);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío.");
        }
        if (!dni.matches("[0-9]{7,8}")) {
            throw new IllegalArgumentException("El DNI debe contener entre 7 y 8 dígitos numéricos.");
        }
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        this.apellido = apellido;
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

    @Override
    public String toString() {
        return " [codigo=" + codigo + ", dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + "]";
    }

    public abstract String mostrarInfo();

}
