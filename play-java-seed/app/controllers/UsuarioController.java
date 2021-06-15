package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final Logger logger = LoggerFactory.getLogger("controller");

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        logger.debug("In UsuarioController.create(), input is: {}", json.toString());
        Usuario usuario = UsuarioBBDD.getInstance().addUsuario(Json.fromJson(json, Usuario.class));
        JsonNode jsonObject = Json.toJson(usuario);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }
    
 
    public Result retrieve(int id) {
        logger.debug("In UsuarioController.retrieve(), retrieve usuario with idUsuario: {}",id);
        if (UsuarioBBDD.getInstance().getUsuario(id) == null) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(UsuarioBBDD.getInstance().getUsuario(id));
        logger.debug("In UsuarioController.retrieve(), result is: {}",jsonObjects.toString());
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result listUsuarios() {
        ArrayList<UsuarioShort> result = UsuarioBBDD.getInstance().getAllUsuarios();
        logger.debug("In UsuarioController.listUsuarios(), result is: {}",result.toString());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));

    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        logger.debug("In UsuarioController.retrieve(), delete usuario with idUsuario: {}",id);
        if (!UsuarioBBDD.getInstance().deleteUsuario(id)) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " deleted", true));
    }
/*
    public Result update(Http.Request request, int id) throws SQLException, ClassNotFoundException {

        logger.debug("In EmployeeController.update(), update employee with id: {}",id);
        if (EmployeeBBDD.getInstance().getEmployee(id) == null) {
            return notFound(ApplicationUtil.createResponse("Employee with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(EmployeeBBDD.getInstance().getEmployee(id));
        Employee employee = EmployeeBBDD.getInstance().updateEmployee(Json.fromJson(jsonObjects, Employee.class));
        JsonNode jsonObject = Json.toJson(employee);
        logger.debug("In EmployeeController.update(), employee is: {}",employee);
        return created(ApplicationUtil.createResponse(jsonObjects, true));
    }
    */
}