package AppStore.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DbConnection{
    private Connection conn;

    public DbConnection() throws Exception{
        Class.forName("org.h2.Driver");
        connect();
    }

    public void connect(){
        try{
            conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public Connection getConn() {
        connect();
        return conn;
    }

    public void closeConnection() throws Exception{
        conn.close();
    }

}
