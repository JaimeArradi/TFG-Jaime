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

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.sql.SQLException;
import java.util.*;
import java.io.*;


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
/*
    public Result retrieve(int id) {
        Usuario usuario = UsuarioBBDD.getInstance().getUsuario(id);

        if (usuario == null) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(usuario);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }
*/
/*
    public Result retrieve(Http.Request request,int id) {
        Usuario usuario = UsuarioBBDD.getInstance().getUsuario(id);

        if (usuario == null) {
            return notFound(ApplicationUtil.createResponse("Usuario with idUsuario:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(usuario);
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }*/

    public Result retrieve(Http.Request request,int id) {
        Usuario usuario = UsuarioBBDD.getInstance().getUsuario(id);
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

                    Template template = cfg.getTemplate("usuario.ftl");
                    StringWriter sw = new StringWriter();
                    Map<String, Object> mapa = new TreeMap<String, Object>();
                    mapa.put("usuario", usuario); // a usuario lo llamo "usuario"
                    template.process(mapa, sw);
                    output = sw.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ok(output).as("text/html");
            }
         else {
                    JsonNode jsonObjects = Json.toJson(UsuarioBBDD.getInstance().getUsuario(id));
            return ok(ApplicationUtil.createResponse(jsonObjects, true));

        }


    }

    public Result listUsuarios(Http.Request request) {
        ArrayList<UsuarioShort> result = UsuarioBBDD.getInstance().getAllUsuarios();

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

                Template template = cfg.getTemplate("usuarios.ftl");
                StringWriter sw = new StringWriter();
                Map<String, Object> mapa = new TreeMap<String, Object>();
                mapa.put("usuarios", result); // a usuario lo llamo "usuario"
                template.process(mapa, sw);
                output = sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ok(output).as("text/html");
        }
        else {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
            return ok(ApplicationUtil.createResponse(jsonData, true));
        }
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