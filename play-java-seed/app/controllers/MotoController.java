package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Moto;
import entities.MotoShort;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.MotoBBDD;
import utils.ApplicationUtil;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
    
 
    public Result retrieve(Http.Request request,int id) {
        Moto moto = MotoBBDD.getInstance().getMoto(id);
        if (moto == null) {
            return notFound(ApplicationUtil.createResponse("Moto with idMoto:" + id + " not found", false));
        }
        if (request.accepts("text/html")) {
            String output = "error";
            try {
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
                cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "/templates/");
                cfg.setDefaultEncoding("UTF-8");
                cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
                cfg.setLogTemplateExceptions(false);

                cfg.setWrapUncheckedExceptions(true);
                cfg.setFallbackOnNullLoopVariable(false);
                cfg.setNumberFormat("computer");

                Template template = cfg.getTemplate("moto.ftl");
                StringWriter sw = new StringWriter();
                Map<String, Object> mapa = new TreeMap<String, Object>();
                mapa.put("moto", moto);
                template.process(mapa, sw);
                output = sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ok(output).as("text/html");
        } else {
            JsonNode jsonObjects = Json.toJson(moto);
            return ok(ApplicationUtil.createResponse(jsonObjects, true));
        }
    }

        public Result listMotos () {
            ArrayList<MotoShort> result = MotoBBDD.getInstance().getAllMotos();
            logger.debug("In MotoController.listMotos(), result is: {}", result.toString());
            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
            return ok(ApplicationUtil.createResponse(jsonData, true));

        }

        public Result delete ( int id) throws SQLException, ClassNotFoundException {
            logger.debug("In MotoController.retrieve(), delete moto with idMoto: {}", id);
            if (!MotoBBDD.getInstance().deleteMoto(id)) {
                return notFound(ApplicationUtil.createResponse("Moto with idMoto:" + id + " not found", false));
            }
            return ok(ApplicationUtil.createResponse("Moto with idMoto:" + id + " deleted", true));
        }
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
