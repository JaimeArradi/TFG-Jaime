package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Ruta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.RutaBBDD;
import utils.ApplicationUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class RutaController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger("controller");

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        logger.debug("In RutaController.create(), input is: {}", json.toString());
        Ruta ruta = RutaBBDD.getInstance().addRuta(Json.fromJson(json, Ruta.class));
        JsonNode jsonObject = Json.toJson(ruta);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }
    
 
    public Result retrieve(int id) {
        logger.debug("In RutaController.retrieve(), retrieve ruta with idRuta: {}",id);
        if (RutaBBDD.getInstance().getRuta(id) == null) {
            return notFound(ApplicationUtil.createResponse("Ruta with idRuta:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(RutaBBDD.getInstance().getRuta(id));
        logger.debug("In RutaController.retrieve(), result is: {}",jsonObjects.toString());
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result listRutas() {
        ArrayList<Ruta> result = RutaBBDD.getInstance().getAllRutas();
        logger.debug("In RutaController.listRutas(), result is: {}",result.toString());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));

    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        logger.debug("In RutaController.retrieve(), delete ruta with idRuta: {}",id);
        if (!RutaBBDD.getInstance().deleteRuta(id)) {
            return notFound(ApplicationUtil.createResponse("Ruta with idRuta:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Ruta with idRuta:" + id + " deleted", true));
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