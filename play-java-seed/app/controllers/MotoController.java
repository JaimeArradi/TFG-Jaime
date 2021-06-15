package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Moto;
import entities.MotoShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.MotoBBDD;
import utils.ApplicationUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class MotoController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger("controller");

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        logger.debug("In MotoController.create(), input is: {}", json.toString());
        Moto moto = MotoBBDD.getInstance().addMoto(Json.fromJson(json, Moto.class));
        JsonNode jsonObject = Json.toJson(moto);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }
    
 
    public Result retrieve(int id) {
        logger.debug("In MotoController.retrieve(), retrieve moto with idMoto: {}",id);
        if (MotoBBDD.getInstance().getMoto(id) == null) {
            return notFound(ApplicationUtil.createResponse("Moto with idMoto:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(MotoBBDD.getInstance().getMoto(id));
        logger.debug("In MotoController.retrieve(), result is: {}",jsonObjects.toString());
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result listMotos() {
        ArrayList<MotoShort> result = MotoBBDD.getInstance().getAllMotos();
        logger.debug("In MotoController.listMotos(), result is: {}",result.toString());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));

    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        logger.debug("In MotoController.retrieve(), delete moto with idMoto: {}",id);
        if (!MotoBBDD.getInstance().deleteMoto(id)) {
            return notFound(ApplicationUtil.createResponse("Moto with idMoto:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Moto with idMoto:" + id + " deleted", true));
    }
/*
    public Result update(Http.Request request, int id) throws SQLException, ClassNotFoundException {

        logger.debug("In MotoController.update(), update moto with id: {}",id);
        if (MotoBBDD.getInstance().getMoto(id) == null) {
            return notFound(ApplicationUtil.createResponse("Moto with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(MotoBBDD.getInstance().getMoto(id));
        Moto moto = MotoBBDD.getInstance().updateMoto(Json.fromJson(jsonObjects, Moto.class));
        JsonNode jsonObject = Json.toJson(moto);
        logger.debug("In MotoController.update(), moto is: {}",moto);
        return created(ApplicationUtil.createResponse(jsonObjects, true));
    }*/
}