import Connection.Conn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

public class Test extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Connection con=Conn.getCon();    
        
        try (PrintWriter out = response.getWriter()) {
            
            System.out.println(request.getRequestURL());
            System.out.print(request.getParameter("called"));
            
            /* TODO output your page here. You may use following sample code. */
            Cookie c[]=request.getCookies();
            int i;
            String name="mayank";
            /*for(i=0;i<c.length;i++)
            {    
                if(c[i].getName()=="name")
                {
                    name =c[i].getValue();
                    break;
                }
            }
                    */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Test Module</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Welcome " + name + "</h1>");
            out.println("<form action=\"Test\"><center>");
            String query="select * from testquestions where questionID=?";
        
            Random rand=new Random();
            int randomNum = rand.nextInt(5) + 1;
            System.out.println(randomNum);
            String value="M00"+randomNum;
            System.out.println(value);
            
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, value);
            ResultSet rs=ps.executeQuery();
            ResultSetMetaData rdata=rs.getMetaData();
           
            int marks=0;
            
            rs.absolute(1);     //damn this!!
            String question=rs.getString("question");
            String option[]=new String[4];
            for(i=0;i<4;i++)
            {
                option[i]=rs.getString("option"+(i+1));
            }
            out.print("<p>Ques:"+question+"</p>");
            for(i=0;i<4;i++)
            {
                out.print("<input type=\"radio\" name=\"Option\" value=\""+(i+1)+"\" />"+option[i]+"<br>");
            }
            out.println("<br><input value=\"Submit\" type=\"SUBMIT\" class=\"button\"><br><br>");
            out.println("<input value=\"Previous Question\" type=\"BUTTON\" class=\"button\">");
            out.println("<input value=\"Next Question\" type=\"BUTTON\" class=\"button\">");
            
            //Test?Option=a
            /*
            if(rs.getString("answer")==userAnswer)
                marks+=3;
            String query2="update tablename set score";
            */
            //how to store choices for student
            
            out.println("</center></form>");
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