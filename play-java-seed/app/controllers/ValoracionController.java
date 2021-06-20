package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Valoracion;
import entities.ValoracionShort;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ValoracionBBDD;
import utils.ApplicationUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ValoracionController extends Controller {

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        Valoracion valoracion = ValoracionBBDD.getInstance().addValoracion(Json.fromJson(json, Valoracion.class));
        JsonNode jsonObject = Json.toJson(valoracion);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }

    public Result retrieve(int id) {
        Valoracion valoracion = ValoracionBBDD.getInstance().getValoracion(id);
        if (valoracion == null) {
            return notFound(ApplicationUtil.createResponse("Valoracion with idValoracion:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(valoracion);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result listValoraciones() {
        ArrayList<ValoracionShort> result = ValoracionBBDD.getInstance().getAllValoraciones();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));

    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        if (!ValoracionBBDD.getInstance().deleteValoracion(id)) {
            return notFound(ApplicationUtil.createResponse("Valoracion with idValoracion:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Valoracion with idValoracion:" + id + " deleted", true));
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