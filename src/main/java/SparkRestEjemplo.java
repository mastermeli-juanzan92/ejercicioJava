import com.google.gson.Gson;

import java.text.CollationElementIterator;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import static spark.Spark.*;

public class SparkRestEjemplo {

    public static void main(String[] args) throws ApiException {

        port(8080);
        final ApiService apiService = new ApiServiceMapImpl();

        Date date1 = new Date(2019, 4, 15);
        Date date2 = new Date(2019, 4, 20);
        Usuario u1 = new Usuario(1, "juan", "bere");
        Usuario u2 = new Usuario(2, "Alan", "Brito");
        Usuario u3 = new Usuario(3, "Victor", "cabra");
        Proyecto p1 = new Proyecto(1, "marketplace", u1);
        Proyecto p2 = new Proyecto(2, "android", u2);
        Incidente i1 = new Incidente(1, Clasificacion.NORMAL,
                "server apagado", u1, u3, Estado.ASIGNADO, date1, null, 2);
        Incidente i2 = new Incidente(2, Clasificacion.NORMAL,
                "server apagado", u2, u3, Estado.RESUELTO, date1, date2, 2);
        try {
            apiService.addUsuario(u1);
            apiService.addUsuario(u2);
            apiService.addUsuario(u3);

        } catch (Exception exception) {
            throw new ApiException(exception.getMessage());
        }

        apiService.addProyecto(p1);
        apiService.addProyecto(p2);
        apiService.addIncidente(i1);
        apiService.addIncidente(i2);

        post("/usuario", (request, response) -> {
            //agrega un nuevo usuario
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            apiService.addUsuario(usuario);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS)
            );
        });

        post("/proyecto", (request, response) -> {
            //agrega un nuevo proyecto
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            apiService.addProyecto(proyecto);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS)
            );
        });

        post("/incidente", (request, response) -> {
            //agrega un nuevo incidente
            response.type("application/json");
            Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);
            apiService.addIncidente(incidente);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS)
            );
        });

        get("/usuario", (request, response) -> {
            //busca todos los usuarios
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS, new Gson().toJsonTree(
                    apiService.getUsuarios()))
            );
        });

        get("/proyecto", (request, response) -> {
            //busca todos los proyectos
            Collection<Proyecto> proyectos = apiService.getProyectos();
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS, new Gson().toJsonTree(
                    proyectos))
            );
        });

        //busca todos los incidente
        get("/incidente", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS, new Gson().toJsonTree(
                    apiService.getIncidentes()))
            );
        });

        //busca todos los incidentes de un usuario responsable
        //busca el usuario por id
        get("/incidente/usuario:id", (request, response) -> {
            response.type("application/json");
            Usuario usuario = apiService.getUsuario(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS, new Gson().toJsonTree(
                    apiService.getIncidenteXUsuario(usuario)))
            );
        });
        //busca un usuario por parametro: id usuario
        get("/usuario/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS, new Gson().toJsonTree(
                    apiService.getUsuario(
                            Integer.parseInt(request.params(":id")))))
            );
        });
        //busca un proyecto por parametro: id proyecto
        get("/proyecto/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS, new Gson().toJsonTree(
                    apiService.getProyecto(
                            Integer.parseInt(request.params(":id")))))
            );
        });

        //busca todos los proyectos de un usuario
        //parametro de busqueda de usuario : id usuario
        get("/proyecto/usuario:id", (request, response) -> {
            response.type("application/json");
            if (apiService.usuarioExist(Integer.parseInt(request.params(":id")))) {
                Usuario propietario = apiService.getUsuario(Integer.parseInt(request.params(":id")));
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.SUCCESS, new Gson().toJsonTree(
                        apiService.getProyectoXPropietario(propietario)))
                );
            } else {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.ERROR, "Api Error")
                );
            }
        });
        //modificar un usuario
        put("/usuario", (request, response) -> {
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            Usuario usuarioEditado = apiService.editUsuario(usuario);

            if (usuarioEditado != null) {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.SUCCESS, new Gson().toJsonTree(
                        usuarioEditado))
                );
            } else {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al editar el integrante") //se puede manejar con excepciones
                );
            }
        });

        //modificar un proyecto
        put("/proyecto", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            Proyecto proyectoEditado = apiService.editProyecto(proyecto);

            if (proyectoEditado != null) {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.SUCCESS, new Gson().toJsonTree(
                        proyectoEditado))
                );
            } else {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al editar el usuario") //se puede manejar con excepciones
                );
            }
        });

        //borrar un usuario con el parametro id
        delete("/usuario/:id", (request, response) -> {
            response.type("application/json");
            if (apiService.usuarioExist(Integer.parseInt(request.params(":id")))) {
                apiService.deleteUsuario(Integer.parseInt(request.params(":id")));
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        "Usuario borrado")
                );
            } else {
                apiService.deleteUsuario(Integer.parseInt(request.params(":id")));
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Usuario No pudo ser borrado")
                );
            }
        });

        //borrar un proyecto por el parametro id
        delete("/proyecto/:id", (request, response) -> {

            response.type("application/json");
            apiService.deleteProyecto(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    "Proyecto borrado")
            );

        });



        System.out.println("Work with port: " + port());
    }
}
