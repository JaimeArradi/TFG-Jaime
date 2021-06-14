package services;
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


public class EmployeeBBDD {

    private static final EmployeeBBDD instance=new EmployeeBBDD();
    public static EmployeeBBDD getInstance() {
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

    public Employee addEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        if (conector() == true) {

            int id = employee.getId();
            String name= employee.getName();
            String department= employee.getDepartment();
            int salary = employee.getSalary();

            createStatement.executeUpdate("INSERT INTO employee (id,name,department,salary) VALUES ("+id+", '" + name + "', '" + department + "', '" + salary + "')");
            con.close();

        }
        return employee;
    }
    public Employee getEmployee(int id) {
        Employee employee = new Employee();
        try {
            if(conector()==true){

                String queryBBDD = "select * from employee where id=" + id + ";";
                int i=0;
                try {
                    rS = createStatement.executeQuery(queryBBDD);
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rS == null){
                    employee= null;

                }
                else{

                    try {
                        while (rS.next()) {
                            employee.setId(rS.getInt("id"));
                            //employee.setId(Integer.parseInt(rS.getString("id")));
                            employee.setName(rS.getString("name"));
                            employee.setDepartment(rS.getString("department"));
                            //employee.setSalary(Integer.parseInt(rS.getString("salary")));
                            employee.setSalary(rS.getInt("salary"));

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i = 0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            else{
                    employee=null;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employee;
    }

        public ArrayList<Employee> getAllEmployees() {
            ArrayList<Employee> empleadoLista = new ArrayList();
            try {
                if(conector()==true){
                    String queryBBDD = "select * from employee;";
                    int i=0;
                    try {
                        rS = createStatement.executeQuery(queryBBDD);

                        while (rS.next()) {
                            Employee empleado = new Employee();
                            empleado.setId(Integer.parseInt(rS.getString("id")));
                            empleado.setName(rS.getString("name"));
                            empleado.setDepartment(rS.getString("department"));
                            empleado.setSalary(Integer.parseInt(rS.getString("salary")));
                            System.out.println("xxxxxxxx"+ empleado.getName());
                           empleadoLista.add(empleado);

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        i=0;
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                else{
                    return empleadoLista;
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El tamano de la lista es" + empleadoLista.size());
            return empleadoLista;

        }

    public Employee updateEmployee(Employee employee ) throws SQLException, ClassNotFoundException {
        try {
        if (conector() == true) {
            int id = employee.getId();
            String name= employee.getName();
            String department= employee.getDepartment();
            int salary = employee.getSalary();
            System.out.println(name);
            System.out.println(department);
            System.out.println(salary);
            String queryBBDD = "update employee set name='"+name+"', department='"+department+"',salary="+salary+" where id="+id+";";

            try {
                createStatement.executeUpdate(queryBBDD);
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{

        }
    } catch (SQLException ex) {
        Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
        return employee;
    }

    public boolean deleteEmployee(int id) throws SQLException, ClassNotFoundException {
        boolean valor= false;
        try {
            if (conector() == true) {

                String queryBBDD = "delete from employee where id="+id+";";

                try {
                    createStatement.executeUpdate(queryBBDD);
                    valor = true;
                    return valor;
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
}
