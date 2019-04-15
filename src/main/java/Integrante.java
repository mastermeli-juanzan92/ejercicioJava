public class Integrante implements Comparable<Integrante> {
    private String nombre;
    private String apellido;
    private String correo;

    public Integrante(String nombre, String apellido, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        Integrante aux = (Integrante) obj;
        if (this.apellido.compareToIgnoreCase(aux.apellido) == 0)
            res = true;
        return res;
    }

    @Override
    public int compareTo(Integrante o) {
        return this.apellido.compareToIgnoreCase(o.apellido);
    }

    @Override
    public String toString() {
        return "Hola: " + nombre + " " + apellido  + " " + correo;
    }
}
