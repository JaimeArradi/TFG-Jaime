package services;

import entities.*;
import entities.ValoracionShort;
import play.db.Database;
import play.db.Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ValoracionBBDD {

    private static final ValoracionBBDD instance=new ValoracionBBDD();
    public static ValoracionBBDD getInstance() {
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

    public Valoracion addValoracion(Valoracion valoracion) throws SQLException, ClassNotFoundException {
        if (conector()) {
            try {

                int id = valoracion.getId();
                String comentario = valoracion.getComentario();
                int puntuacion = valoracion.getPuntuacion();
                int idUsuario = valoracion.getUsuario().getId();
                int idRuta = valoracion.getRuta().getId();

                createStatement.executeUpdate("INSERT INTO valoracion (idValoracion,comentario,puntuacion," +
                        "idUsuario,idRuta) VALUES (" + id + ", '" + comentario + "', '" + puntuacion + "', '" + idUsuario + "'," +
                        "'" + idRuta +"');",Statement.RETURN_GENERATED_KEYS);
                ResultSet genUri = createStatement.getGeneratedKeys();
                genUri.next();
                id =genUri.getInt(1);
                String patron = "/valoracion/";
                String uri = patron+id;
                createStatement.executeUpdate("UPDATE  valoracion set uriValoracion ='" + uri + "' where idValoracion = "+ id + ";");
            }catch (Exception e){
                e.printStackTrace();
            }
            con.close();

        }
        return valoracion;
    }

    public Valoracion getValoracion(int id) {
        Valoracion valoracion = new Valoracion();
        try {
            if(conector()){

                String queryBBDD = "select * from valoracion INNER JOIN usuario ON valoracion.idUsuario=usuario.idUsuario INNER JOIN ruta ON valoracion.idRuta=ruta.idRuta where valoracion.idValoracion=" + id + ";";

                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (rS == null){
                    valoracion= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            valoracion.setId(rS.getInt("idValoracion"));
                            valoracion.setComentario(rS.getString("comentario"));
                            valoracion.setPuntuacion(rS.getInt("puntuacion"));
                            valoracion.setUsuario(new UsuarioShort(rS.getInt("idUsuario"),rS.getString("uriUsuario")));
                            valoracion.setRuta(new RutaShort(rS.getInt("idRuta"),rS.getString("uriRuta")));
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
                    valoracion=null;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valoracion;
    }

        public ArrayList<ValoracionShort> getAllValoraciones() {
            ArrayList<ValoracionShort> valoracionLista = new ArrayList();
            try {
                if(conector()){
                    String queryBBDD = "select * from valoracion;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            ValoracionShort valoracion = new ValoracionShort();
                            valoracion.setId(rS.getInt("idValoracion"));
                            valoracion.setUri(rS.getString("uriValoracion"));
                            valoracionLista.add(valoracion);//lista de valoracion short

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i=0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                else{
                    return valoracionLista;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("El tamano de la lista es" + valoracionLista.size());
            return valoracionLista;

        }

    public ArrayList<ValoracionShort> getAllValoraciones1(int id) {
        ArrayList<ValoracionShort> valoracionLista = new ArrayList();
        try {
            if(conector()){
                String queryBBDD = "SELECT * FROM valoracion INNER JOIN usuario ON valoracion.idUsuario=usuario.idUsuario INNER JOIN ruta ON valoracion.idRuta=ruta.idRuta where valoracion.idRuta=" + id + ";";
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);

                    while (rS.next()) {
                        ValoracionShort valoracion = new ValoracionShort();
                        valoracion.setId(rS.getInt("idValoracion"));
                        valoracion.setUri(rS.getString("uriValoracion"));
                        valoracionLista.add(valoracion);//lista de valoracion short

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    i=0;
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            else{
                return valoracionLista;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("El tamano de la lista es" + valoracionLista.size());
        return valoracionLista;

    }

        /*
    public Valoracion updateValoracion(Valoracion valoracion ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = valoracion.getId();
            String name= valoracion.getComentario();
            int puntuacion = valoracion.getPuntuacion();
            String sexo = valoracion.getSexo();
            String bio = valoracion.getBio();
            String terreno = valoracion.getTerreno();
            String carne = valoracion.getCarne();
            int nivel = valoracion.getNivel();
            //string moto...
            Boolean intercomunicador = valoracion.getIntercomunicador();
            System.out.println(name);
            String queryBBDD = "update valoracion set name='"+name+"', department='"+department+"',salary="+salary+" where id="+id+";";

            try {
                createStatement.executeUpdate(queryBBDD);
            } catch (SQLException ex) {
                Logger.getLogger(ValoracionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
            }

            try {

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ValoracionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
            }
        }
        else{

        }
    } catch (SQLException ex) {
        Logger.getLogger(ValoracionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(ValoracionBBDD.class.getComentario()).log(Level.SEVERE, null, ex);
    }
        return valoracion;
    }


         */
    public boolean deleteValoracion(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from valoracion where idValoracion="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                    valor = true;
                    return valor;
                } catch (SQLException ex) {
                    Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{

            }
        } catch (SQLException ex) {
            Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ValoracionBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
}
