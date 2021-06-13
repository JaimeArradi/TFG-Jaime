package services;

import entities.Usuario;
import play.db.Database;
import play.db.Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioBBDD {

    private static final UsuarioBBDD instance=new UsuarioBBDD();
    public static UsuarioBBDD getInstance() {
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

   // public void addUsuario(String name,String department,  String pass, int salary ) throws SQLException, ClassNotFoundException {
    public Usuario addUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        if (conector() == true) {

            int id = usuario.getId();
            //usuario.setId(id);
            String name= usuario.getName();
            //usuario.setName(name);
            String department= usuario.getDepartment();
            int salary = usuario.getSalary();

            createStatement.executeUpdate("INSERT INTO usuario (id,name,department,salary) VALUES ("+id+", '" + name + "', '" + department + "', '" + salary + "')");
            con.close();

        }
        return usuario;
    }
    public Usuario getUsuario(int id) {
        Usuario usuario = new Usuario();
        try {
            if(conector()==true){

                String queryBBDD = "select * from usuario where id=" + id + ";";
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rS == null){
                    usuario= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            usuario.setId(rS.getInt("id"));
                            //usuario.setId(Integer.parseInt(rS.getString("id")));
                            usuario.setName(rS.getString("name"));
                            usuario.setDepartment(rS.getString("department"));
                            //usuario.setSalary(Integer.parseInt(rS.getString("salary")));
                            usuario.setSalary(rS.getInt("salary"));

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i = 0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            else{
                    usuario=null;

            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

        public ArrayList<Usuario> getAllUsuarios() {
            ArrayList<Usuario> empleadoLista = new ArrayList();
            try {
                if(conector()==true){
                    String queryBBDD = "select * from usuario;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            Usuario empleado = new Usuario();
                            empleado.setId(Integer.parseInt(rS.getString("id")));
                            empleado.setName(rS.getString("name"));
                            empleado.setDepartment(rS.getString("department"));
                            empleado.setSalary(Integer.parseInt(rS.getString("salary")));
                            System.out.println("xxxxxxxx"+ empleado.getName());
                           empleadoLista.add(empleado);

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i=0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                else{
                    return empleadoLista;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El tamano de la lista es" + empleadoLista.size());
            return empleadoLista;

        }

    public Usuario updateUsuario(Usuario usuario ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = usuario.getId();
            String name= usuario.getName();
            String department= usuario.getDepartment();
            int salary = usuario.getSalary();
            System.out.println(name);
            System.out.println(department);
            System.out.println(salary);
            String queryBBDD = "update usuario set name='"+name+"', department='"+department+"',salary="+salary+" where id="+id+";";

            try {
                createStatement.executeUpdate(queryBBDD);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{

        }
    } catch (SQLException ex) {
        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
        return usuario;
    }

    public boolean deleteUsuario(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from usuario where id="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                    valor = true;
                    return valor;
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{

            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }



    /*
    private static UsuarioService instance;
    private Map<Integer, Usuario> usuarios = new HashMap<>();

    public static UsuarioService getInstance() {
        if (instance == null) {
            instance = new UsuarioService();
        }
        return instance;
    }

    public Usuario addUsuario(Usuario usuario) {
        int id = usuarios.size()+1;
        usuario.setId(id);
        usuarios.put(id, usuario);
        return usuario;
    }

    public Usuario getUsuario(int id) {
        return usuarios.get(id);
    }

    public Set<Usuario> getAllUsuarios() {
        return new HashSet<>(usuarios.values());
    }

    public Usuario updateUsuario(Usuario usuario) {
        int id = usuario.getId();
        if (usuarios.containsKey(id)) {
            usuarios.put(id, usuario);
            return usuario;
        }
        return null;
    }

    public boolean deleteUsuario(int id) {
        return usuarios.remove(id) != null;
    }
    */

}
