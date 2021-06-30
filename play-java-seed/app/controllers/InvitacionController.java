package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Invitacion;
import entities.InvitacionShort;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.InvitacionBBDD;
import utils.ApplicationUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class InvitacionController extends Controller {

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        Invitacion invitacion = InvitacionBBDD.getInstance().addInvitacion(Json.fromJson(json, Invitacion.class));
        JsonNode jsonObject = Json.toJson(invitacion);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }

    public Result retrieve(int id) {
        Invitacion valoracion = InvitacionBBDD.getInstance().getInvitacion(id);
        if (valoracion == null) {
            return notFound(ApplicationUtil.createResponse("Invitacion with idInvitacion:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(valoracion);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result retrieve1(int id, int idi) {
        Invitacion invitacion = InvitacionBBDD.getInstance().getInvitacion1(id, idi);
        if (invitacion == null) {
            return notFound(ApplicationUtil.createResponse("Invitacion with idInvitacion:" + idi + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(invitacion);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result listInvitaciones() {
        ArrayList<InvitacionShort> result = InvitacionBBDD.getInstance().getAllInvitaciones();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));
    }

    public Result listInvitaciones1(int id) {
        ArrayList<InvitacionShort> result = InvitacionBBDD.getInstance().getAllInvitaciones1(id);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));
    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        if (!InvitacionBBDD.getInstance().deleteInvitacion(id)) {
            return notFound(ApplicationUtil.createResponse("Invitacion with idInvitacion:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Invitacion with idInvitacion:" + id + " deleted", true));
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