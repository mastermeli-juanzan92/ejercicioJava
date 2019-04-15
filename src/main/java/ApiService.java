import java.security.PublicKey;
import java.util.Collection;

public interface ApiService {

    public Collection<Usuario> getUsuarios();
    public Collection<Proyecto> getProyectos();
    public Collection<Incidente> getIncidentes();

    public void addUsuario(Usuario usuario) throws ApiException;
    public Usuario getUsuario(int id);
    public Usuario editUsuario(Usuario usuario) throws ApiException;
    public void deleteUsuario(int id) throws ApiException;
    public boolean usuarioExist(int id);

    public void addProyecto(Proyecto proyecto);
    public Proyecto getProyecto(int id) throws ApiException ;
    public Proyecto editProyecto(Proyecto proyecto) throws ApiException ;
    public void deleteProyecto(int id) throws ApiException ;
    public boolean proyectoExist (int id) ;

    public void addIncidente(Incidente Incidente) throws ApiException;
    public Collection<Proyecto> getProyectoXPropietario(Usuario usuario);
    public Collection<Incidente> getIncidenteXUsuario(Usuario usuario);
    public Collection<Incidente> getIncidenteXProyecto(Proyecto proyecto) ;
    //Segun el estado filtra por abierto o resuelto
    public Collection<Incidente> getIncidentesXEstado(Estado estado);

}
