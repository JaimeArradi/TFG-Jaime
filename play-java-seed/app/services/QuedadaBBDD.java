package services;

import entities.*;
import play.db.Database;
import play.db.Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class QuedadaBBDD {

    private static final QuedadaBBDD instance=new QuedadaBBDD();
    public static QuedadaBBDD getInstance() {
        return instance;
    }

    private static Connection con;
    private static final String driver="com.mysql.cj.jdbc.Driver";//"com.mysql.jdbc.Driver";
    private static final String user="jaime";
    private static final String pass="jaime";
    private static final String url="jdbc:mysql://localhost:3306/tfg_motos";
    Database database = Databases.createFrom(driver, url);
    private Statement createStatement;
    private ResultSet rS;
    private ResultSet rS1;

    protected boolean conector() throws SQLException, ClassNotFoundException {
        // Reseteamos a null la conexion a la bd
        con=null;
        boolean valor=false;
        try{

            int i=0;
            //  String valor= "fallo";
            Class.forName(driver);
            // Nos conectamos a la bd
            con=DriverManager.getConnection(url, user, pass);
            // Si la conexion fue exitosa mostramos un mensaje de conexion exitosa
            if (con!=null){
                System.out.println("La conexion no es nula");
                createStatement = con.createStatement();
                valor= true;
            }
            else{
                System.out.println("La conexion es nula");
            }
        }
        //Si la conexion NO fue exitosa mostramos un mensaje de error
        catch (ClassNotFoundException | SQLException e){
            System.out.println( e);
        }
        return  valor;
    }


    public Quedada addQuedada(Quedada quedada) throws SQLException, ClassNotFoundException {
        if (conector()) {
            try {

                String name = quedada.getName();
                String horaInicial = quedada.getHoraInicial();
                String horaFinal = quedada.getHoraFinal();
                String lugarPartida = quedada.getLugarPartida();
                String lugarFinal = quedada.getLugarFinal();
                String paradas = quedada.getParadas();
                int idRuta = quedada.getRuta().getId();
                int usuCreador =quedada.getCreador().getId();
                String tipo = quedada.getTipo();
                Boolean recomendar = quedada.getRecomendar();

                ArrayList<UsuarioShort> usuariosConfirmados= quedada.getUsuariosConfirmados();
                ArrayList<UsuarioShort> usuariosInvitados= quedada.getUsuariosInvitados();
                ArrayList<UsuarioShort> usuariosRecomendados= new ArrayList<>();
                if(recomendar){
                    try {
                        rS = createStatement.executeQuery("SELECT * FROM usuario WHERE idUsuario="+usuCreador+";");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    int nivel=0;
                    while(rS.next()) {
                        nivel = rS.getInt("nivel");
                    }
                    try {
                        rS1 = createStatement.executeQuery("SELECT * FROM usuario WHERE nivel ="+ nivel+" LIMIT 3;");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    while(rS1.next()){
                        UsuarioShort usuario = new UsuarioShort();
                        usuario.setUri(rS1.getString("uriUsuario"));
                        usuario.setId(rS1.getInt("idUsuario"));
                        quedada.getUsuariosRecomendados().add(usuario);
                    }
                    usuariosRecomendados = quedada.getUsuariosRecomendados();
                }

                //usuarios + usuariosInv + usuariosRecomen

                createStatement.executeUpdate("INSERT INTO quedada (name,horaInicial,horaFinal," +
                        "lugarPartida,lugarFinal,paradas, idRuta, idUsuCreador, tipo) VALUES ('" + name + "', '" + horaInicial + "'," +
                        " '" + horaFinal + "','" + lugarPartida + "','" + lugarFinal + "', '" + paradas+"'," +
                        " '" + idRuta+"', '" + usuCreador+"', '" + tipo+"');",Statement.RETURN_GENERATED_KEYS);
                ResultSet genUri = createStatement.getGeneratedKeys();
                genUri.next();
                int id =genUri.getInt(1);
                String patron = "/quedada/";
                String uri = patron+id;
                createStatement.executeUpdate("UPDATE  quedada set uriQuedada ='" + uri + "' where idQuedada = "+ id + ";");

                for(int i=0; i<usuariosConfirmados.size(); i++){
                    int idc = usuariosConfirmados.get(i).getId();
                    createStatement.executeUpdate("INSERT INTO quedadausuarios (idUsuario,idQuedada,rol) VALUES ("+idc+",'"+id+"','Conf');");
                }
                for(int i=0; i<usuariosInvitados.size(); i++){
                    int idi = usuariosInvitados.get(i).getId();
                    createStatement.executeUpdate("INSERT INTO quedadausuarios (idUsuario,idQuedada,rol) VALUES ("+idi+",'"+id+"','Inv');");
                    createStatement.executeUpdate("INSERT INTO invitacion (idUsuario,idQuedada) VALUES ("+idi+",'"+id+"');",Statement.RETURN_GENERATED_KEYS);
                    ResultSet genUri1 = createStatement.getGeneratedKeys();
                    genUri1.next();
                    int id1 =genUri1.getInt(1);
                    String patron1 = "/invitacion/";
                    String uri1 = patron1+id1;
                    createStatement.executeUpdate("UPDATE  invitacion set uriInvitacion ='" + uri1 + "' where idInvitacion = "+ id1 + ";");
                }
                for(int i=0; i<usuariosRecomendados.size(); i++){
                    int idr = usuariosRecomendados.get(i).getId();
                    createStatement.executeUpdate("INSERT INTO quedadausuarios (idUsuario,idQuedada,rol) VALUES ("+idr+","+id+",'Recomen');");
                    createStatement.executeUpdate("INSERT INTO invitacion (idUsuario,idQuedada) VALUES ("+idr+",'"+id+"');",Statement.RETURN_GENERATED_KEYS);
                    ResultSet genUri2 = createStatement.getGeneratedKeys();
                    genUri2.next();
                    int id2 =genUri2.getInt(1);
                    String uri2 = "/usuarios/"+idr+"/invitaciones/"+id2;
                    //String uri2 = patron2+id2;
                    createStatement.executeUpdate("UPDATE  invitacion set uriInvitacion ='" + uri2 + "' where idInvitacion = "+ id2 + ";");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            con.close();

        }
        return quedada;
    }


    public Quedada getQuedada(int id) {
        Quedada quedada = new Quedada();
        try {
            if(conector()){

                String queryBBDD = "select * from quedada INNER JOIN usuario ON quedada.idUsuCreador=usuario.idUsuario" +
                        " INNER JOIN ruta ON quedada.idRuta=ruta.idRuta where quedada.idQuedada=" + id + ";";
                String queryBBDD1 = "SELECT usuario.uriUsuario, usuario.idUsuario, quedadausuarios.rol FROM quedada INNER JOIN quedadausuarios on quedadausuarios.idQuedada = quedada.idQuedada INNER JOIN ruta ON quedada.idRuta=ruta.idRuta INNER JOIN usuario ON usuario.idUsuario=quedadausuarios.idUsuario WHERE quedada.idQuedada=" + id + ";";


                //meto el usuario creador commo lo tengo metido o dentro de la base de datos quedadausuarios con rol creador??
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (rS == null){
                    quedada= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            quedada.setId(rS.getInt("idQuedada"));
                            quedada.setName(rS.getString("name"));
                            quedada.setCreador(new UsuarioShort(rS.getInt("idUsuCreador"),rS.getString("uriUsuario")));
                            //usuarios invitados y recomendados como los meto
                            quedada.setHoraInicial(rS.getString("horaInicial"));
                            quedada.setHoraFinal(rS.getString("horaFinal"));
                            quedada.setLugarPartida(rS.getString("lugarPartida"));
                            quedada.setLugarFinal(rS.getString("lugarFinal"));
                            //quedada.setValoracion(rS.getInt("valoracion"));
                            quedada.setRuta(new RutaShort(rS.getInt("idRuta"),rS.getString("uriRuta")));
                            quedada.setTipo(rS.getString("tipo"));
                            //usuarios + usuariosInv + usuariosRecomen
                            quedada.setParadas(rS.getString("paradas"));
                            quedada.setUriQuedada(rS.getString("uriQuedada"));



                            /*
                            if(rS.getString("rol").equals("Recomen")){
                                quedada.addRecomendado(new UsuarioShort(rS.getInt("quedadaUsuarios.idUsuario"),rS.getString("QuedadadaUsuarios.uriUsuario")))
                            }
                            Hago query para usuCreador y con una rs nueva hago otra query para coger los usuairos invitados y recomendados
                            */
                            /*
                            RECOMENDADOR
                            quedada.getUsuarioCreador().getId()
                            usuario usuario;
                            Hacer query para sacar su nivel
                            setNivel

                            if nivel >= nivel - 1 && nivel<= nivel+1
                            cogo esos ususarios y los meto en recomendados
                             */

                        }
                        rS1 = createStatement.executeQuery(queryBBDD1);

                        while(rS1.next()){
                            String rol = rS1.getString("rol");

                            if(rol.equals("Conf")){
                                UsuarioShort usuario = new UsuarioShort();
                                usuario.setUri(rS1.getString("uriUsuario"));
                                usuario.setId(rS1.getInt("idUsuario"));
                                quedada.getUsuariosConfirmados().add(usuario);
                            }else if(rol.equals("Inv")){
                                UsuarioShort usuario = new UsuarioShort();
                                usuario.setUri(rS1.getString("uriUsuario"));
                                usuario.setId(rS1.getInt("idUsuario"));
                                quedada.getUsuariosInvitados().add(usuario);

                            }else{
                                UsuarioShort usuario = new UsuarioShort();
                                usuario.setUri(rS1.getString("uriUsuario"));
                                usuario.setId(rS1.getInt("idUsuario"));
                                quedada.getUsuariosRecomendados().add(usuario);
                            }
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                    try {
                        i = 0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                }

            }
            else{
                    quedada=null;

            }
        } catch (SQLException ex) {
            Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quedada;
    }

        public ArrayList<QuedadaShort> getAllQuedadas() {
            ArrayList<QuedadaShort> quedadaLista = new ArrayList();
            try {
                if(conector()){
                    String queryBBDD = "select * from quedada;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            QuedadaShort quedada = new QuedadaShort();
                            quedada.setId(rS.getInt("idQuedada"));
                            quedada.setUri(rS.getString("uriQuedada"));
                            quedadaLista.add(quedada);//lista de quedada short

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i=0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                else{
                    return quedadaLista;
                }
            } catch (SQLException ex) {
                Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El tamano de la lista es" + quedadaLista.size());
            return quedadaLista;

        }

        /*
    public Quedada updateQuedada(Quedada quedada ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = quedada.getId();
            String name= quedada.getName();
            int edad = quedada.getEdad();
            String sexo = quedada.getSexo();
            String bio = quedada.getBio();
            String terreno = quedada.getTerreno();
            String carne = quedada.getCarne();
            int nivel = quedada.getNivel();
            //string moto...
            Boolean intercomunicador = quedada.getIntercomunicador();
            System.out.println(name);
            String queryBBDD = "update quedada set name='"+name+"', department='"+department+"',salary="+salary+" where id="+id+";";

            try {
                createStatement.executeUpdate(queryBBDD);
            } catch (SQLException ex) {
                Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{

        }
    } catch (SQLException ex) {
        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
        return quedada;
    }


         */
    public boolean deleteQuedada(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from quedada where idQuedada="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                    valor = true;
                    return valor;
                } catch (SQLException ex) {
                    Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{

            }
        } catch (SQLException ex) {
            Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
}
