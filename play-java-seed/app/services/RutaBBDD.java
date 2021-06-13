package services;

import entities.Ruta;
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
            //ruta.setId(id);
            String name= ruta.getName();
            //ruta.setName(name);
            String department= ruta.getDepartment();
            int salary = ruta.getSalary();

            createStatement.executeUpdate("INSERT INTO ruta (id,name,department,salary) VALUES ("+id+", '" + name + "', '" + department + "', '" + salary + "')");
            con.close();

        }
        return ruta;
    }
    public Ruta getRuta(int id) {
        Ruta ruta = new Ruta();
        try {
            if(conector()==true){

                String queryBBDD = "select * from ruta where id=" + id + ";";
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
                            //ruta.setId(Integer.parseInt(rS.getString("id")));
                            ruta.setName(rS.getString("name"));
                            ruta.setDepartment(rS.getString("department"));
                            //ruta.setSalary(Integer.parseInt(rS.getString("salary")));
                            ruta.setSalary(rS.getInt("salary"));

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

        public ArrayList<Ruta> getAllRutas() {
            ArrayList<Ruta> rutaLista = new ArrayList();
            try {
                if(conector()==true){
                    String queryBBDD = "select * from ruta;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            Ruta ruta = new Ruta();
                            ruta.setId(Integer.parseInt(rS.getString("id")));
                            ruta.setName(rS.getString("name"));
                            ruta.setDepartment(rS.getString("department"));
                            ruta.setSalary(Integer.parseInt(rS.getString("salary")));
                            System.out.println("xxxxxxxx"+ ruta.getName());
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

    public Ruta updateRuta(Ruta ruta ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = ruta.getId();
            String name= ruta.getName();
            String department= ruta.getDepartment();
            int salary = ruta.getSalary();
            System.out.println(name);
            System.out.println(department);
            System.out.println(salary);
            String queryBBDD = "update ruta set name='"+name+"', department='"+department+"',salary="+salary+" where id="+id+";";

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

    public boolean deleteRuta(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from ruta where id="+id+";";

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



    /*
    private static RutaService instance;
    private Map<Integer, Ruta> rutas = new HashMap<>();

    public static RutaService getInstance() {
        if (instance == null) {
            instance = new RutaService();
        }
        return instance;
    }

    public Ruta addRuta(Ruta ruta) {
        int id = rutas.size()+1;
        ruta.setId(id);
        rutas.put(id, ruta);
        return ruta;
    }

    public Ruta getRuta(int id) {
        return rutas.get(id);
    }

    public Set<Ruta> getAllRutas() {
        return new HashSet<>(rutas.values());
    }

    public Ruta updateRuta(Ruta ruta) {
        int id = ruta.getId();
        if (rutas.containsKey(id)) {
            rutas.put(id, ruta);
            return ruta;
        }
        return null;
    }

    public boolean deleteRuta(int id) {
        return rutas.remove(id) != null;
    }
    */

}
