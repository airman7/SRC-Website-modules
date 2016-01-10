import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import Connection.Conn;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Component extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {

            Connection con;
            con = Conn.getCon();
            String query1="select componentid,componentname,quantity from components";
            ResultSet rs;
            ResultSetMetaData rdata;
            Statement stmt;
            int count = 0;

            try 
            {
                stmt = con.createStatement();
                rs=stmt.executeQuery(query1);
                rdata=rs.getMetaData();
                while (rs.next()) 
                {
                    ++count;
                }

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Component</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Allocate Component</h1>");

                out.println("<select>");
                int i;
                if(rs.first())
                {
                    for(i=0;i<count;i++)
                    {
                        
                        String component=rs.getString("componentname");
                        out.println("<option value=\""+component+"\">"+component+"</option>");
                        rs.next();
                    }
                }
                else
                {
                    out.println("<option value=\"null\">No component</option>");
                }
                out.println("</select>");

                out.println("<button type=\"button\" onclick= >Allocate</button>");
                out.println("<button type=\"button\" onclick= >Find</button>");
                out.println("</body>");
                out.println("</html>");
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(Component.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}