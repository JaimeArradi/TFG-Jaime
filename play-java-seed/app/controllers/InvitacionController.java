package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Invitacion;
import entities.InvitacionShort;
import entities.RespuestaInvitacion;
import entities.Usuario;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.InvitacionBBDD;
import services.UsuarioBBDD;
import utils.ApplicationUtil;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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

    public Result modifyAction(Http.Request request, int id, int idi) throws SQLException, ClassNotFoundException {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        Boolean valor= InvitacionBBDD.getInstance().modifyInvitacion(Json.fromJson(json, RespuestaInvitacion.class), id, idi);
        if(valor){
            return ok(ApplicationUtil.createResponse("Invitacion modificada", true));
        }else{
            return badRequest(ApplicationUtil.createResponse("Invitacion no modificada", false));
        }
    }




    public Result retrieve1(Http.Request request, int id, int idi) {
        Invitacion invitacion = InvitacionBBDD.getInstance().getInvitacion1(id, idi);
        if (invitacion == null) {
            return notFound(ApplicationUtil.createResponse("Invitacion with idInvitacion:" + idi + " not found", false));
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

                Template template = cfg.getTemplate("invitacion.ftl");
                StringWriter sw = new StringWriter();
                Map<String, Object> mapa = new TreeMap<String, Object>();
                mapa.put("invitacion", invitacion);
                template.process(mapa, sw);
                output = sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ok(output).as("text/html");
        } else {
            JsonNode jsonObjects = Json.toJson(invitacion);
            return ok(ApplicationUtil.createResponse(jsonObjects, true));
        }
    }

    public Result listInvitaciones(Http.Request request) {
        ArrayList<InvitacionShort> result = InvitacionBBDD.getInstance().getAllInvitaciones();
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

                Template template = cfg.getTemplate("invitaciones.ftl");
                StringWriter sw = new StringWriter();
                Map<String, Object> mapa = new TreeMap<String, Object>();
                mapa.put("invitaciones", result); // a invitaciones lo llamo "invitaciones"
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
    
    public Result listInvitaciones1(Http.Request request,int id) {
        ArrayList<InvitacionShort> result = InvitacionBBDD.getInstance().getAllInvitaciones1(id);
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

                Template template = cfg.getTemplate("invitaciones.ftl");
                StringWriter sw = new StringWriter();
                Map<String, Object> mapa = new TreeMap<String, Object>();
                mapa.put("invitaciones", result); // a invitaciones lo llamo "invitaciones"
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