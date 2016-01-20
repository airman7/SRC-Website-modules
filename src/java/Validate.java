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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Validate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Validate New User</title>");            
            out.println("</head>");
            out.println("<body>");
            
            //Validate?user=Lokesh+Moondra
            Connection con=Conn.getCon();
            String select="select * from new_members where Name=?";
            String delete="delete from new_members where Name=?";
            String update="insert into members(Name,Email,Branch,College,Contact) values(?,?,?,?,?)";
            PreparedStatement ps;
            ResultSet rs;
            
            try {
                
                String name =request.getParameter("user");
                ps=con.prepareStatement(select);
                ps.setString(1, name);
                rs=ps.executeQuery();
                rs.absolute(1);
                String email=rs.getString("Email");
                String num=rs.getString("Contact");
                String branch=rs.getString("Branch");
                String clg=rs.getString("College");
                
                System.out.println(email);
                System.out.println(num);
                System.out.println(branch);
                System.out.println(clg);
                
                ps=con.prepareStatement(delete);
                ps.setString(1, name);
                ps.executeUpdate();
                
                ps=con.prepareStatement(update);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, branch);
                ps.setString(4, clg);
                ps.setString(5, num);
                int i=ps.executeUpdate();
                
                if(i==1)
                    out.println("<h1>User "+name+" Validated</h1>");
                else
                    out.print("<h1>ERROR</h1>");
                con.close();
            } catch (SQLException ex) {
                out.print("<h1>ERROR</h1>");
            }
            out.println("<br><a href=\"index.jsp\">Go to admin home</a>");
            out.println("</body>");
            out.println("</html>");
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