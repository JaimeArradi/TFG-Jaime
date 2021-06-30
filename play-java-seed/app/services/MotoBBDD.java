package services;

import entities.Moto;
import play.db.Database;
import play.db.Databases;
import entities.*;

import play.db.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotoBBDD {

    private static final MotoBBDD instance=new MotoBBDD();
    public static MotoBBDD getInstance() {
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
            con= DriverManager.getConnection(url, user, pass);
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
            e.printStackTrace();
        }
        return  valor;
    }

    public Moto getMoto(int id) {
        Moto moto = new Moto();
        try {
            if(conector()){

                String queryBBDD = "select * from moto where idMoto=" + id + ";";
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rS == null){
                    moto= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            moto.setId(rS.getInt("idMoto"));
                            moto.setEstilo(rS.getString("estilo"));
                            moto.setMarca(rS.getString("marca"));
                            moto.setModelo(rS.getString("modelo"));
                            moto.setPotencia(rS.getInt("potencia"));

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i = 0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            else{
                moto=null;

            }
        } catch (SQLException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moto;
    }

    public ArrayList<MotoShort> getAllMotos() {
        ArrayList<MotoShort> motoLista = new ArrayList();
        try {
            if(conector()){
                String queryBBDD = "select * from moto;";
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);

                    while (rS.next()) {
                        MotoShort moto = new MotoShort();
                        moto.setId(rS.getInt("idMoto"));
                        moto.setUri(rS.getString("uriMoto"));
                        motoLista.add(moto);

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    i=0;
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            else{
                return motoLista;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("El tamano de la lista es" + motoLista.size());
        return motoLista;

    }
    /*
    public Moto updateMoto(Moto moto ) throws SQLException, ClassNotFoundException {
        try {
            if (conector() == true) {
                int id = moto.getId();
                moto.setId(rS.getInt("idMoto"));
                moto.setEstilo(rS.getString("estilo"));
                moto.setMarca(rS.getString("marca"));
                moto.setModelo(rS.getString("modelo"));
                moto.setPotencia(rS.getInt("potencia"));
                String queryBBDD = "update moto set estilo='"+ estilo+"', marca='"+marca+"',modelo="+modelo+", potencia="+potenica+" where idMoto="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                } catch (SQLException ex) {
                    Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{

            }
        } catch (SQLException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moto;
    }
*/
    public boolean deleteMoto(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from moto where idMoto="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                    valor = true;
                    return valor;
                } catch (SQLException ex) {
                    Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{

            }
        } catch (SQLException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MotoBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    public Moto addMoto(Moto moto) throws SQLException, ClassNotFoundException {
        if (conector() == true) {

            String estilo = moto.getEstilo();
            String marca= moto.getMarca();
            String modelo= moto.getModelo();
            int potencia = moto.getPotencia();

            createStatement.executeUpdate("INSERT INTO moto (estilo, marca, modelo, potencia) VALUES ('" + estilo + "', '" + marca + "', '" + modelo + "', "+potencia+");",Statement.RETURN_GENERATED_KEYS);
            ResultSet genUri = createStatement.getGeneratedKeys();
            genUri.next();
            int id =genUri.getInt(1);
            String patron = "/motos/";
            String uri = patron+id;
            createStatement.executeUpdate("UPDATE  moto set uriMoto ='" + uri + "' where idMoto = "+ id + ";");
            con.close();


        }
        return moto;
    }
}
