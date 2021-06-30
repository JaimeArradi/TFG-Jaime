package services;

import entities.*;
import play.db.Database;
import play.db.Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InvitacionBBDD {

    private static final InvitacionBBDD instance=new InvitacionBBDD();
    public static InvitacionBBDD getInstance() {
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

    public Invitacion addInvitacion(Invitacion invitacion) throws SQLException, ClassNotFoundException {
        if (conector()) {
            try {

                int idUsuario = invitacion.getUsuario().getId();
                int idQuedada = invitacion.getQuedada().getId();

                createStatement.executeUpdate("INSERT INTO invitacion (idUsuario,idQuedada) VALUES ('" + idUsuario + "','" + idQuedada +"');",Statement.RETURN_GENERATED_KEYS);
                ResultSet genUri = createStatement.getGeneratedKeys();
                genUri.next();
                int id =genUri.getInt(1);
                String patron = "/invitacion/";
                String uri = patron+id;
                createStatement.executeUpdate("UPDATE  invitacion set uriInvitacion ='" + uri + "' where idInvitacion = "+ id + ";");
                //createStatement.executeUpdate("INSERT INTO quedadausuarios (idUsuario,idQuedada) VALUES ('" + idUsuario + "','" + idQuedada +"');"
                //falta pasar el rol, ¿tiene sentido hacer esta invitación o se hará desde quedada?
            }catch (Exception e){
                e.printStackTrace();
            }
            con.close();

        }
        return invitacion;
    }

    public Invitacion getInvitacion(int id) {
        Invitacion invitacion = new Invitacion();
        try {
            if(conector()){

                String queryBBDD = "select * from invitacion INNER JOIN usuario ON invitacion.idUsuario=usuario.idUsuario INNER JOIN quedada ON invitacion.idQuedada=quedada.idQuedada where invitacion.idInvitacion=" + id + ";";

                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (rS == null){
                    invitacion= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            invitacion.setIdInvitacion(rS.getInt("idInvitacion"));
                            invitacion.setUsuario(new UsuarioShort(rS.getInt("idUsuario"),rS.getString("uriUsuario")));
                            invitacion.setQuedada(new QuedadaShort(rS.getInt("idQuedada"),rS.getString("uriQuedada")));
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        i = 0;
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            else{
                    invitacion=null;

            }
        } catch (SQLException ex) {
            Logger.getLogger(InvitacionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InvitacionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invitacion;
    }

    public Invitacion getInvitacion1(int id, int idi) {
        Invitacion invitacion = new Invitacion();
        try {
            if(conector()){

                String queryBBDD = "SELECT * FROM usuario INNER JOIN invitacion ON invitacion.idUsuario = usuario.idUsuario INNER JOIN quedada on quedada.idQuedada=invitacion.idQuedada WHERE usuario.idUsuario="+id+" and invitacion.idInvitacion=" + idi + ";";

                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (rS == null){
                    invitacion= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            invitacion.setIdInvitacion(rS.getInt("idInvitacion"));
                            invitacion.setUsuario(new UsuarioShort(rS.getInt("idUsuario"),rS.getString("uriUsuario")));
                            invitacion.setQuedada(new QuedadaShort(rS.getInt("idQuedada"),rS.getString("uriQuedada")));
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            else{
                invitacion=null;

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return invitacion;
    }

        public ArrayList<InvitacionShort> getAllInvitaciones() {
            ArrayList<InvitacionShort> invitacionLista = new ArrayList();
            try {
                if(conector()){
                    String queryBBDD = "select * from invitacion;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            InvitacionShort invitacion = new InvitacionShort();
                            invitacion.setIdInvitacion(rS.getInt("idInvitacion"));
                            invitacion.setUriInvitacion(rS.getString("uriInvitacion"));
                            invitacionLista.add(invitacion);//lista de invitacion short

                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        i=0;
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
                else{
                    return invitacionLista;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            //System.out.println("El tamano de la lista es" + invitacionLista.size());
            return invitacionLista;

        }

    public ArrayList<InvitacionShort> getAllInvitaciones1(int id) {
        ArrayList<InvitacionShort> invitacionLista = new ArrayList();
        try {
            if(conector()){
                String queryBBDD = "SELECT * FROM usuario INNER JOIN invitacion ON invitacion.idUsuario = usuario.idUsuario WHERE usuario.idUsuario=" + id + ";";
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);

                    while (rS.next()) {
                        InvitacionShort invitacion = new InvitacionShort();
                        invitacion.setIdInvitacion(rS.getInt("idInvitacion"));
                        invitacion.setUriInvitacion(rS.getString("uriInvitacion"));
                        invitacionLista.add(invitacion);//lista de invitacion short

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    i=0;
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
            else{
                return invitacionLista;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        //System.out.println("El tamano de la lista es" + invitacionLista.size());
        return invitacionLista;

    }

        /*
    public Invitacion updateInvitacion(Invitacion invitacion ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = invitacion.getId();
            String name= invitacion.getComentario();
            int puntuacion = invitacion.getPuntuacion();
            String sexo = invitacion.getSexo();
            String bio = invitacion.getBio();
            String terreno = invitacion.getTerreno();
            String carne = invitacion.getCarne();
            int nivel = invitacion.getNivel();
            //string moto...
            Boolean intercomunicador = invitacion.getIntercomunicador();
            System.out.println(name);
            String queryBBDD = "update invitacion set name='"+name+"', department='"+department+"',salary="+salary+" where id="+id+";";

            try {
                createStatement.executeUpdate(queryBBDD);
            } catch (SQLException ex) {
                Logger.getLogger(InvitacionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
            }

            try {

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(InvitacionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
            }
        }
        else{

        }
    } catch (SQLException ex) {
        Logger.getLogger(InvitacionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(InvitacionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
    }
        return invitacion;
    }


         */
    public boolean deleteInvitacion(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from invitacion where idInvitacion="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                    valor = true;
                    return valor;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else{

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return valor;
    }
}
