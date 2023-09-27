package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Sytru
 */
public class ConnectDB {
     public Connection getConnection() throws ClassNotFoundException{
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://THOMAS_ELMS\\SQLEXPRESS:1433;databaseName=Football_Management2";
            String user = "sa";
            String pass = "Pleiku2019*";
            conn = DriverManager.getConnection(url, user, pass);
            if(conn != null){
                //System. out.println( "Kết nối thành công!");
            }
            if(conn == null){
                System. out.println( "Kết nối không thành công!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}