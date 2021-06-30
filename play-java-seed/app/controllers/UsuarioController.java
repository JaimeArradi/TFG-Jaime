package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Accion;
import entities.RespuestaInvitacion;
import entities.Usuario;
import entities.UsuarioShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.UsuarioBBDD;
import utils.ApplicationUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioController extends Controller {

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        Usuario usuario = UsuarioBBDD.getInstance().addUsuario(Json.fromJson(json, Usuario.class));
        JsonNode jsonObject = Json.toJson(usuario);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }

    public Result retrieve(int id) {
        Usuario usuario = UsuarioBBDD.getInstance().getUsuario(id);

        if (usuario == null) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(usuario);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    /*public Result retrieve(int id, int idi) {
        Usuario usuario = UsuarioBBDD.getInstance().getUsuario(id);

        if (usuario == null) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(usuario);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }*/
    /*
    public Result retrieve(int idUsuario, int idValoracion) {
        Usuario usuario = UsuarioBBDD.getInstance().getUsuario(id);

        if (usuario == null) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(usuario);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }
    */

    public Result listUsuarios() {
        ArrayList<UsuarioShort> result = UsuarioBBDD.getInstance().getAllUsuarios();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));

    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        if (!UsuarioBBDD.getInstance().deleteUsuario(id)) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Usuario with idUsuario: " + id + " deleted", true));
    }
/*
    public Result update(Http.Request request, int id) throws SQLException, ClassNotFoundException {

        if (EmployeeBBDD.getInstance().getEmployee(id) == null) {
            return notFound(ApplicationUtil.createResponse("Employee with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(EmployeeBBDD.getInstance().getEmployee(id));
        Employee employee = EmployeeBBDD.getInstance().updateEmployee(Json.fromJson(jsonObjects, Employee.class));
        JsonNode jsonObject = Json.toJson(employee);
        return created(ApplicationUtil.createResponse(jsonObjects, true));
    }
    */
}