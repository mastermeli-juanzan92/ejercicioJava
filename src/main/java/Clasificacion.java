public enum Clasificacion {

    CRITICO("Success"),
    NORMAL("Normal"),
    ERROR("Error");

    private String clasificacion;

    Clasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
