import java.util.Date;

public class Incidente {
    private int id ;
    private Clasificacion clasificacion ;
    private String descripcion ;
    private Usuario usuarioReportador;
    private Usuario usuarioResponsable;
    private Estado estado ;
    private Date fechaCreacion;
    private Date fechaSolucion ;
    private int proyectoID ;

    public Incidente(){

    }

    public Incidente(int id, Clasificacion clasificacion,
                     String descripcion, Usuario usuarioReportador,
                     Usuario usuarioResponsable, Estado estado, Date fechaCreacion,
                     Date fechaSolucion, int proyectoID) {
        this.id = id;
        this.clasificacion = clasificacion;
        this.descripcion = descripcion;
        this.usuarioReportador = usuarioReportador;
        this.usuarioResponsable = usuarioResponsable;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaSolucion = fechaSolucion;
        this.proyectoID = proyectoID;
    }

    public int getProyectoID() {
        return proyectoID;
    }

    public void setProyectoID(int proyectoID) {
        this.proyectoID = proyectoID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuarioReportador() {
        return usuarioReportador;
    }

    public void setUsuarioReportador(Usuario usuarioReportador) {
        this.usuarioReportador = usuarioReportador;
    }

    public Usuario getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(Usuario usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }
}
