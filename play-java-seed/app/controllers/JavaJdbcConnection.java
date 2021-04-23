package controllers;

import play.db.Database;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

class JavaJdbcConnection {
        private Database db;
        private DatabaseExecutionContext executionContext;

        @Inject
        public JavaJdbcConnection(Database db, DatabaseExecutionContext executionContext) {
            this.db = db;
            this.executionContext = executionContext;
        }

        public CompletionStage<Void> obtenerEmployees() {
            return CompletableFuture.runAsync(
                    () -> {
                        // get jdbc connection

                        Connection connection = db.getConnection();
                        try{
                        PreparedStatement ps = connection.prepareStatement("SELECT * from employee");
                        ResultSet rs = ps.executeQuery();
                        while(rs.next()){
                            int id = rs.getInt("id");
                            String nombre = rs.getString("name");
                            System.out.println(nombre);
                        }}catch (Exception e){
                            System.out.println("Problema al acceder a la base de datos");
                        }
                        // do whatever you need with the db connection

                        return;
                    },
                    executionContext);
        }
    }
