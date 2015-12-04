import Connection.Conn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckStudent extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //CheckStudent?id=15001
        
        Connection con =Conn.getCon();
        PrintWriter out = response.getWriter();
        String query="select * from members where ID=?";
        PreparedStatement pres;
        try
        {
            pres = con.prepareStatement(query);
            pres.setString(1, request.getParameter("id"));
            ResultSet rs=pres.executeQuery();
            boolean b =rs.next();
            if(b)
            {    
                String name=rs.getString("Name");
                Cookie c=new Cookie("name", name);
                response.addCookie(c);
                con.close();
                response.sendRedirect("testpage.jsp");
            }
            else
            {
                con.close();
                 response.sendRedirect("index.jsp");
            }
               
        } 
        catch (SQLException ex) {
            Logger.getLogger(CheckStudent.class.getName()).log(Level.SEVERE, null, ex);
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
