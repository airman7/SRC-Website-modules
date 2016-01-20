import Connection.Conn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddComponent extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //AddComponent?cname=&price=&quant=&company=
            
            Connection con=Conn.getCon();
            String insert="insert into components(componentname,price,quantity,company,available) values(?,?,?,?,?)";
            PreparedStatement ps;
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>AddComponent</title>");            
            out.println("</head>");
            out.println("<body>");
                
            try {
                int temp=Integer.parseInt(request.getParameter("quant"));
                ps=con.prepareStatement(insert);
                ps.setString(1, request.getParameter("cname"));
                ps.setInt(2, Integer.parseInt(request.getParameter("price")));
                ps.setInt(3, temp);
                ps.setString(4, request.getParameter("company"));
                ps.setInt(5, temp);
                int n=ps.executeUpdate();
                
                if(n==1)
                    out.println("<h1>Component added</h1>");
                else
                    out.println("<h1>ERROR</h1>");
            
                out.println("<a href=\"componentpage.jsp\">Return to home</h1>");
                con.close();
            } catch (SQLException ex) {
                out.println("<h1>ERROR</h1>");
            }
            
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