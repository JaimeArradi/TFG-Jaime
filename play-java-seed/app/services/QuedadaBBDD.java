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

   // public void addQuedada(String name,String department,  String pass, int salary ) throws SQLException, ClassNotFoundException {
    public Quedada addQuedada(Quedada quedada) throws SQLException, ClassNotFoundException {
        if (conector() == true) {
            try {

                int id = quedada.getId();
                String name = quedada.getName();
                String horaInicial = quedada.getHoraInicial();
                String horaFinal = quedada.getHoraFinal();
                String lugarPartida = quedada.getLugarPartida();
                String lugarFinal = quedada.getLugarFinal();
                String paradas = quedada.getParadas();
                //set id + uri ruta???
                //usuarios + usuariosInv + usuariosRecomen + usuCreador
                int intercomunicador;

                createStatement.executeUpdate("INSERT INTO quedada (idQuedada,name,horaInicial,horaFinal," +
                        "lugarPartida,lugarFinal,paradas) VALUES (" + id + ", '" + name + "', '" + horaInicial + "'," +
                        " '" + horaFinal + "','" + lugarPartida + "','" + lugarFinal + "', '" + paradas+"')");
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
            if(conector()==true){

                String queryBBDD = "select * from quedada INNER JOIN usuario ON quedada.idUsuCreador=usuario.idUsuario" +
                        " INNER JOIN ruta ON quedada.idRuta=ruta.idRuta where quedada.idQuedada=" + id + ";";

                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rS == null){
                    quedada= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            quedada.setId(rS.getInt("idQuedada"));
                            quedada.setName(rS.getString("name"));
                            quedada.setUsuarioCreador(new UsuarioShort(rS.getInt("idUsuario"),rS.getString("uriUsuario")));
                            quedada.setHoraInicial(rS.getString("horaInicial"));
                            quedada.setHoraFinal(rS.getString("horaFinal"));
                            quedada.setLugarPartida(rS.getString("lugarPartida"));
                            quedada.setLugarFinal(rS.getString("lugarFinal"));
                            //quedada.setValoracion(rS.getInt("valoracion"));
                            quedada.setRuta(new RutaShort(rS.getInt("idRuta"),rS.getString("uriRuta")));
                            //usuarios + usuariosInv + usuariosRecomen
                            quedada.setParadas(rS.getString("paradas"));
                            quedada.setUriQuedada(rS.getString("uriQuedada"));

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i = 0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(QuedadaBBDD.class.getName()).log(Level.SEVERE, null, ex);
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
