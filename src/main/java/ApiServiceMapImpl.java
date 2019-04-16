import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ApiServiceMapImpl implements ApiService {

    private HashMap<Integer, Usuario> usuarioHashMap;
    private HashMap<Integer, Proyecto> proyectoHashMap;
    private HashMap<Integer, Incidente> incidenteHashMap;

    public ApiServiceMapImpl() {
        usuarioHashMap = new HashMap<Integer, Usuario>();
        proyectoHashMap = new HashMap<Integer, Proyecto>();
        incidenteHashMap = new HashMap<Integer, Incidente>();
    }

    @Override
    public Collection<Usuario> getUsuarios() {
        return usuarioHashMap.values();
    }

    @Override
    public Collection<Proyecto> getProyectos() {
        return proyectoHashMap.values();
    }

    @Override
    public Collection<Incidente> getIncidentes() {
        return incidenteHashMap.values();
    }

    @Override
    public void addUsuario(Usuario usuario) throws ApiException {
        try {
            if (usuarioValido(usuario)) {
                usuarioHashMap.put(usuario.getId(), usuario);
            }
        } catch (Exception exception) {
            throw new ApiException(exception.getMessage());
        }
    }

    @Override
    public Usuario getUsuario(int id) {
        return usuarioHashMap.get(id);
    }

    @Override
    public Usuario editUsuario(Usuario usuario) throws ApiException {
        try {
            if (usuarioValido(usuario)) {
                System.out.printf("Usuario Valido");
            }
            Usuario usuarioAEditar = usuarioHashMap.get(usuario.getId());
            usuarioAEditar.setApellido(usuario.getApellido());
            usuarioAEditar.setNombre(usuario.getNombre());
            return usuarioAEditar;

        } catch (Exception exception) {

            throw new ApiException("Error en editar un usuario");

        }
        finally {
            return usuario = null ;
        }


    }

    @Override
    public void deleteUsuario(int id) throws ApiException {
        Collection<Incidente> incidete = incidenteHashMap
                .values()
                .stream()
                .filter(x -> x.getUsuarioReportador().getId() == id || x.getUsuarioResponsable().getId() == id)
                .collect(Collectors.toList());
        Collection<Proyecto> proyectos = proyectoHashMap
                .values()
                .stream()
                .filter(x -> x.getPropietario().getId() == id)
                .collect(Collectors.toList());
        try {

            if (incidete.size() >= 1 || proyectos.size() >= 1) {
                throw new ApiException("El usuario es responsable de incidentes o propietario de proyectos");
            }

            usuarioHashMap.remove(id);
        } catch (Exception exception) {
            throw new ApiException(exception.getMessage());
        }

    }

    @Override
    public boolean usuarioExist(int id) {
        return usuarioHashMap.containsKey(id);
    }

    @Override
    public void addProyecto(Proyecto proyecto) {
        int userID = proyecto.getPropietario().getId();
        proyecto.setPropietario(usuarioHashMap.get(userID));
        proyectoHashMap.put(proyecto.getId(), proyecto);
    }

    @Override
    public Proyecto getProyecto(int id) throws ApiException {
        try {
            proyectoExist(id);
            return proyectoHashMap.get(id);
        } catch (Exception exception) {
            throw new ApiException(exception.getMessage());
        }
    }

    @Override
    public boolean proyectoExist(int id) {
        return proyectoHashMap.containsKey(id);
    }

    @Override
    public Proyecto editProyecto(Proyecto proyecto) throws ApiException {
        try {
            if (proyectoValido(proyecto)) {
                System.out.print("Usuario Valido");
            }
            Proyecto proyectoAEditar = proyectoHashMap.get(proyecto.getId());
            proyectoAEditar.setTitulo(proyecto.getTitulo());
            proyectoAEditar.setPropietario(proyecto.getPropietario());
            return proyectoAEditar;

        } catch (Exception exception) {
            throw new ApiException(exception.getMessage());
        }
    }


    @Override
    public void deleteProyecto(int id) throws ApiException {
        try {
            Collection<Incidente> incidentes = incidenteHashMap
                    .values()
                    .stream()
                    .filter(x -> x.getProyectoID() == id && x.getEstado() == Estado.ASIGNADO)
                    .collect(Collectors.toList());
            if (proyectoExist(id) && (incidentes.size() == 1)) {
                proyectoHashMap.remove(id);
            }
            ;

        } catch (Exception exception) {
            throw new ApiException(exception.getMessage());
        }
    }

    @Override
    public void addIncidente(Incidente incidente) throws ApiException {
        try {
            if (incidente.getFechaSolucion() != null) {
                if (incidente.getFechaCreacion().getTime() > incidente.getFechaSolucion().getTime()) {
                    throw new ApiException("La fecha de creacion no puede ser mayor que la fecha de solucion");
                }
            }
            if (incidenteValido(incidente)) {
                incidenteHashMap.put(incidente.getId(), incidente);
            }
        } catch (Exception exception) {
            throw new ApiException(exception.getMessage());
        }
    }

    @Override
    public Collection<Proyecto> getProyectoXPropietario(Usuario usuario) {

        Collection<Proyecto> proyectos = proyectoHashMap
                .values()
                .stream()
                .filter(x -> x.getPropietario().getId() == usuario.getId())
                .collect(Collectors.toList());
        return proyectos;
    }

    @Override
    public Collection<Incidente> getIncidenteXUsuario(Usuario usuario) {
        Collection<Incidente> incidentes = incidenteHashMap
                .values()
                .stream()
                .filter(x -> x.getUsuarioResponsable().getId() == usuario.getId())
                .collect(Collectors.toList());
        return incidentes;
    }

    @Override
    public Collection<Incidente> getIncidenteXProyecto(Proyecto proyecto) {
        Collection<Incidente> incidentes = incidenteHashMap
                .values()
                .stream()
                .filter(x -> x.getProyectoID() == proyecto.getId())
                .collect(Collectors.toList());
        return incidentes;

    }

    @Override
    public Collection<Incidente> getIncidentesXEstado(Estado estado) {
        Collection<Incidente> resspuestaIncidente = null;
        switch (estado.getStatus()) {

            case "Asignado":
                resspuestaIncidente = incidenteHashMap
                        .values()
                        .stream()
                        .filter(x -> x.getEstado().getStatus() == estado.getStatus())
                        .collect(Collectors.toList());


            case "Resuelto":
                resspuestaIncidente = incidenteHashMap
                        .values()
                        .stream()
                        .filter(x -> x.getEstado().getStatus().equals(estado.getStatus()))
                        .collect(Collectors.toList());
        }

        return resspuestaIncidente;
    }

    private boolean usuarioValido(Usuario usuario) throws ApiException {
        if (usuario.getId() <= 0) {
            throw new ApiException("El id del usuario no valido");
        }
        if (usuario.getApellido() == null) {
            throw new ApiException("Apellido no puede ser nulo");
        }
        if (usuario.getNombre() == null) {
            throw new ApiException("Nombre no puede ser nulo");
        }
        return true;
    }

    private boolean proyectoValido(Proyecto proyecto) throws ApiException {
        if (proyecto.getId() <= 0) {
            throw new ApiException("El id del usuario no valido");
        }
        if (proyecto.getPropietario() == null) {
            throw new ApiException("Apellido no puede ser nulo");
        }
        if (proyecto.getTitulo() == null) {
            throw new ApiException("Nombre no puede ser nulo");
        }
        return true;
    }

    private boolean incidenteValido(Incidente incidente) throws ApiException {
        if (incidente.getProyectoID() <= 0) {
            throw new ApiException("El proyecto id no es valido");
        }
        if (incidente.getId() <= 0) {
            throw new ApiException("El incidente id no es valido");
        }
        if (incidente.getDescripcion() == null) {
            throw new ApiException("El incidente debe tener una descripcion");
        }
        if (incidente.getFechaCreacion() == null) {
            throw new ApiException("La fecha de creacion no puede ser nula");
        }
        if (incidente.getClasificacion() == null) {
            throw new ApiException("La clasificacion no puede ser nulla");
        }
        return true;
    }
}
