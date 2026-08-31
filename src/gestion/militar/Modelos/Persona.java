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
        setCodigo(codigo);
        setDni(dni);
        setApellido(apellido);
        setNombre(nombre);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El codigo debe ser mayor a cero.");
        }
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("El DNI no puede estar vacio.");
        }
        String dniLimpio = dni.trim();
        if (!dniLimpio.matches("[0-9]{7,8}")) {
            throw new IllegalArgumentException("El DNI debe contener entre 7 y 8 digitos numericos.");
        }
        this.dni = dniLimpio;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        this.apellido = apellido.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + " | DNI: " + dni
                + " | Apellido: " + apellido
                + " | Nombre: " + nombre;
    }

    public abstract String mostrarInfo();

}
