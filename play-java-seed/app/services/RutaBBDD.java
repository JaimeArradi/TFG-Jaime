package services;

import entities.Ruta;
import entities.RutaShort;
import entities.ValoracionShort;
import play.db.Database;
import play.db.Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RutaBBDD {

    private static final RutaBBDD instance=new RutaBBDD();
    public static RutaBBDD getInstance() {
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

   // public void addRuta(String name,String department,  String pass, int salary ) throws SQLException, ClassNotFoundException {
    public Ruta addRuta(Ruta ruta) throws SQLException, ClassNotFoundException {
        if (conector() == true) {

            int id = ruta.getId();
            String name= ruta.getName();
            String recorrido = ruta.getRecorrido();
            int km = ruta.getKm();
            String estadoAsfalto = ruta.getEstadoAsfalto();
            String terreno = ruta.getTerreno();
            int dificultad = ruta.getDificultad();
            //valoracion = getval
            int duracion = ruta.getDuracion();
            String trafico = ruta.getTrafico();


            createStatement.executeUpdate("INSERT INTO ruta (idRuta,name,recorrido,km,estadoAsfalto,terreno,dificultad,duracion" +
                    ",trafico) VALUES ("+id+", '" + name + "', '" + recorrido + "', '" + km + "', '" + estadoAsfalto + "'," +
                    " '" + terreno +"', '" + dificultad + "', '" + duracion + "','" + trafico + "')");
            con.close();

        }
        return ruta;
    }
    public Ruta getRuta(int id) {
        Ruta ruta = new Ruta();
        try {
            if(conector()==true){

                String queryBBDD = "SELECT * FROM ruta INNER JOIN valoracion ON valoracion.idRuta = ruta.idRuta " +
                        "WHERE ruta.idRuta =" + id + ";";
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rS == null){
                    ruta= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            ruta.setId(rS.getInt("idRuta"));
                            ruta.setName(rS.getString("name"));
                            ruta.setRecorrido(rS.getString("recorrido"));
                            ruta.setKm(rS.getInt("km"));
                            ruta.setEstadoAsfalto(rS.getString("estadoAsfalto"));
                            ruta.setTerreno(rS.getString("terreno"));
                            ruta.setDificultad(rS.getInt("dificultad"));
                            ruta.addValoracion(new ValoracionShort(rS.getInt("idValoracion"),rS.getString("uriValoracion")));
                            //ruta.add y voy añadiendo a valoracionShort
                            //mas de una valoracion, como las muestros??
                            ruta.setDuracion(rS.getInt("duracion"));
                            ruta.setTrafico(rS.getString("trafico"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i = 0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            else{
                    ruta=null;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ruta;
    }

        public ArrayList<RutaShort> getAllRutas() {
            ArrayList<RutaShort> rutaLista = new ArrayList();
            try {
                if(conector()){
                    String queryBBDD = "select * from ruta;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            RutaShort ruta = new RutaShort();
                            ruta.setId(rS.getInt("idRuta"));
                            ruta.setUri(rS.getString("uriRuta"));
                           rutaLista.add(ruta);

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i=0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                else{
                    return rutaLista;
                }
            } catch (SQLException ex) {
                Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El tamano de la lista es" + rutaLista.size());
            return rutaLista;

        }
/*
    public Ruta updateRuta(Ruta ruta ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = ruta.getId();
            String name= ruta.getName();
            String recorrido = ruta.getRecorrido();
            int km = ruta.getKm();
            String estadoAsfalto = ruta.getEstadoAsfalto();
            String terreno = ruta.getTerreno();
            int dificultad = ruta.getDificultad();
            //valoracion = getval
            int duracion = ruta.getDuracion();
            String trafico = ruta.getTrafico();
            String queryBBDD = "update ruta set name='"+name+"', recorrido='"+recorrido+"',km="+km+"," +
                    "estadoAsfalto='"estadoAsfalto"', terreno='"+terreno+"', dificultad='"+dificultad+"'," +
                "duracion="+duracion+", trafico='"+trafico+"' where idRuta="+id+";";

            try {
                createStatement.executeUpdate(queryBBDD);
            } catch (SQLException ex) {
                Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{

        }
    } catch (SQLException ex) {
        Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
        return ruta;
    }
*/
    public boolean deleteRuta(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from ruta where idRuta="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                    valor = true;
                    return valor;
                } catch (SQLException ex) {
                    Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{

            }
        } catch (SQLException ex) {
            Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RutaBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
}
