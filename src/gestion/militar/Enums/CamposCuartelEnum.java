package gestion.militar.Enums;

public enum CamposCuartelEnum {
    NOMBRE("Nombre"), CAPACIDAD("Capacidad");

    private final String descripcion;

    private CamposCuartelEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
