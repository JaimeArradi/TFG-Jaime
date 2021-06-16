package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Quedada;
import entities.QuedadaShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.QuedadaBBDD;
import utils.ApplicationUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class QuedadaController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger("controller");

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        logger.debug("In QuedadaController.create(), input is: {}", json.toString());
        Quedada quedada = QuedadaBBDD.getInstance().addQuedada(Json.fromJson(json, Quedada.class));
        JsonNode jsonObject = Json.toJson(quedada);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }


    public Result retrieve(int id) {
        logger.debug("In QuedadaController.retrieve(), retrieve quedada with idQuedada: {}",id);
        if (QuedadaBBDD.getInstance().getQuedada(id) == null) {
            return notFound(ApplicationUtil.createResponse("Quedada with idQuedada:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(QuedadaBBDD.getInstance().getQuedada(id));
        logger.debug("In QuedadaController.retrieve(), result is: {}",jsonObjects.toString());
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result listQuedadas() {
        ArrayList<QuedadaShort> result = QuedadaBBDD.getInstance().getAllQuedadas();
        logger.debug("In QuedadaController.listQuedadas(), result is: {}",result.toString());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));

    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        logger.debug("In QuedadaController.retrieve(), delete quedada with idQuedada: {}",id);
        if (!QuedadaBBDD.getInstance().deleteQuedada(id)) {
            return notFound(ApplicationUtil.createResponse("Quedada with idQuedada:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Quedada with idQuedada:" + id + " deleted", true));
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