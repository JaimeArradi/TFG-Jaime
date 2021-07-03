package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Quedada;
import entities.QuedadaShort;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.QuedadaBBDD;
import utils.ApplicationUtil;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class QuedadaController extends Controller {

    public Result create(Http.Request request) throws SQLException, ClassNotFoundException {
        System.out.println(1);
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        Quedada quedada = QuedadaBBDD.getInstance().addQuedada(Json.fromJson(json, Quedada.class));
        JsonNode jsonObject = Json.toJson(quedada);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }

    public Result retrieve(Http.Request request, int id) {
        Quedada quedada = QuedadaBBDD.getInstance().getQuedada(id);
        if (quedada == null) {
            return notFound(ApplicationUtil.createResponse("Quedada with idQuedada:" + id + " not found", false));
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

                Template template = cfg.getTemplate("quedada.ftl");
                StringWriter sw = new StringWriter();
                Map<String, Object> mapa = new TreeMap<String, Object>();
                mapa.put("quedada", quedada);
                template.process(mapa, sw);
                output = sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ok(output).as("text/html");
        } else {
            JsonNode jsonObjects = Json.toJson(quedada);
            return ok(ApplicationUtil.createResponse(jsonObjects, true));
        }
    }

    public Result listQuedadas(Http.Request request) {
        ArrayList<QuedadaShort> result = QuedadaBBDD.getInstance().getAllQuedadas();
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

                Template template = cfg.getTemplate("quedadas.ftl");
                StringWriter sw = new StringWriter();
                Map<String, Object> mapa = new TreeMap<String, Object>();
                mapa.put("quedadas", result);
                template.process(mapa, sw);
                output = sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ok(output).as("text/html");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
            return ok(ApplicationUtil.createResponse(jsonData, true));

        }
    }

    public Result delete(int id) throws SQLException, ClassNotFoundException {
        if (!QuedadaBBDD.getInstance().deleteQuedada(id)) {
            return notFound(ApplicationUtil.createResponse("Quedada with idQuedada:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("Quedada with idQuedada:" + id + " deleted", true));
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