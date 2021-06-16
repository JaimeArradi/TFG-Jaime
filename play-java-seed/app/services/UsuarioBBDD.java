package services;

import entities.Moto;
import entities.MotoShort;
import entities.Usuario;
import entities.UsuarioShort;
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
            try {

                int id = usuario.getId();
                String name = usuario.getName();
                int edad = usuario.getEdad();
                String sexo = usuario.getSexo();
                String bio = usuario.getBio();
                String terreno = usuario.getTerreno();
                String carne = usuario.getCarne();
                int nivel = usuario.getNivel();
                int motoId = usuario.getMoto().getId();
                //string moto...
                int intercomunicador;
                if (usuario.getIntercomunicador()) {
                    intercomunicador = 1;
                } else {
                    intercomunicador = 0;
                }

                createStatement.executeUpdate("INSERT INTO usuario (idUsuario,name,edad,sexo,bio,terreno," +
                        "carne,nivel,intercom,motoId) VALUES (" + id + ", '" + name + "', '" + edad + "', '" + sexo + "'," +
                        "'" + bio + "','" + terreno + "', '" + carne + "', '" + nivel + "','" + intercomunicador + "'" +
                        ",'" + motoId + "')");
            }catch (Exception e){
                e.printStackTrace();
            }
            con.close();

        }
        return usuario;
    }


    public Usuario getUsuario(int id) {
        Usuario usuario = new Usuario();
        System.out.println("1");
        try {
            if(conector()){

                String queryBBDD = "select * from usuario INNER JOIN moto ON usuario.idMoto=moto.idMoto " +
                        "where usuario.idUsuario=" + id + ";";

                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                    System.out.println("2");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rS == null){
                    usuario= null;
                    System.out.println("3");

                }
                else{

                    try {
                        while (rS.next()) {
                            System.out.println("4");
                            usuario.setId(rS.getInt("idUsuario"));
                            usuario.setName(rS.getString("name"));
                            usuario.setEdad(rS.getInt("edad"));
                            usuario.setSexo(rS.getString("sexo"));
                            usuario.setBio(rS.getString("bio"));
                            usuario.setTerreno(rS.getString("terreno"));
                            usuario.setCarne(rS.getString("carne"));
                            usuario.setNivel(rS.getInt("nivel"));
                            usuario.setIntercomunicador(rS.getBoolean("intercom"));
                            usuario.setMoto(new MotoShort(rS.getInt("idMoto"),rS.getString("uriMoto")));

                        } //while con toda la consulta y que saque los datos de moto, al objeto motoId le meto la id
                        //mejor hacer inner join y mostrar moto cojo solo id y uri
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                    try {
                        i = 0;
                        con.close();
                        System.out.println("5");
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
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
        System.out.println("6");
        return usuario;
    }

        public ArrayList<UsuarioShort> getAllUsuarios() {
            ArrayList<UsuarioShort> usuarioLista = new ArrayList();
            try {
                if(conector()){
                    String queryBBDD = "select * from usuario;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            UsuarioShort usuario = new UsuarioShort();
                            usuario.setId(rS.getInt("idUsuario"));
                            usuario.setUri(rS.getString("uriUsuario"));
                            usuarioLista.add(usuario);//lista de usuario short

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
                    return usuarioLista;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuarioBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El tamano de la lista es" + usuarioLista.size());
            return usuarioLista;

        }

        /*
    public Usuario updateUsuario(Usuario usuario ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = usuario.getId();
            String name= usuario.getName();
            int edad = usuario.getEdad();
            String sexo = usuario.getSexo();
            String bio = usuario.getBio();
            String terreno = usuario.getTerreno();
            String carne = usuario.getCarne();
            int nivel = usuario.getNivel();
            //string moto...
            Boolean intercomunicador = usuario.getIntercomunicador();
            System.out.println(name);
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


         */
    public boolean deleteUsuario(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from usuario where idUsuario="+id+";";

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
}
