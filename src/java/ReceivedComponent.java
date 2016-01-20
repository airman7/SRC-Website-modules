import Connection.Conn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReceivedComponent extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ReceivedComponent</title>");            
            out.println("</head>");
            out.println("<body>");
            
//ReceivedComponent?rec=2%2C150002
            Connection con=Conn.getCon();
            String delete="delete from componentinfo where cid=? AND holderid=?";
            String getAvailability="select available from components where componentid=?";
            String setAvailability="update components set available=? where componentid=?";
            PreparedStatement ps;
            ResultSet rs;
            int avail;
            
            try {
                String temp=request.getParameter("rec");
                int t=temp.indexOf(",");
                int comp=Integer.parseInt(temp.substring(0,t));
                int hold=Integer.parseInt(temp.substring(t+1));
                
                ps=con.prepareStatement(delete);
                ps.setInt(1, comp);
                ps.setInt(2, hold);
                ps.executeUpdate();
                
                ps=con.prepareStatement(getAvailability);
                ps.setInt(1,comp);
                rs=ps.executeQuery();
                rs.absolute(1); 
                avail=rs.getInt("available");
                avail++;
                
                ps=con.prepareStatement(setAvailability);
                ps.setInt(1,avail);
                ps.setInt(2,comp);
                ps.executeUpdate();
                
                out.println("<h1>Received Component </h1>");
            
            } catch (SQLException ex) {
                out.println("<h1>ERROR</h1>");
            }
                
            out.println("<a href=\"componentpage.jsp\">Go to admin home</a>");
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