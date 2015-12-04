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

public class Test extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String query="select * from questions where qid=?";
        Connection con=Conn.getCon();    
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Cookie c[]=request.getCookies();
            int i;
            String name="";
            for(i=0;i<c.length;i++)
            {    
                if(c[i].getName()=="name")
                {
                    name =c[i].getValue();
                    break;
                }
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Test Module</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Welcome " + name + "</h1>");
            
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, "MCQ");
            ResultSet rs=ps.executeQuery();
            char ch='a';
            String option[]={"Mayank","Nakul","vinu","kittu"};
            for(i=0;i<4;i++)
            {
                out.print("<input type=\"radio\" name=\"Option\" value=\""+ch+"\" />"+option[i]+"<br>");
                ch++;
            }
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
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