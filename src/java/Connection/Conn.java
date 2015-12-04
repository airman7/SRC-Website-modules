package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conn {
    public static Connection getCon()
    {
        Connection con=null;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/src","root","root");
           
        } 
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    public static void main(String args[])
    {
       System.out.println(getCon());    
    }
    
}
