package gestion.militar.Vistas;

public enum CamposPersonaEnum {
    DNI("DNI"), NOMBRE("Nombre"), APELLIDO("Apellido");

    private final String descripcion;

    private CamposPersonaEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
