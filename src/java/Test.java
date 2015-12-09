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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import javax.servlet.http.HttpSession;

public class Test extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        response.setContentType("text/html;charset=UTF-8");
        Connection con=Conn.getCon();
        HttpSession ses=request.getSession();
            
        try (PrintWriter out = response.getWriter()) 
        {
            int i;
            String name="";
            name= (String) ses.getAttribute("name");
            int num;
            String option[]=new String[4];
                
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
            //System.out.println(randomNum);
            String value="M00"+randomNum;
            //System.out.println(value);

            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, value);
            ResultSet rs=ps.executeQuery();
            //ResultSetMetaData rdata=rs.getMetaData();

            int marks;
            
            rs.absolute(1);     //damn this!!
            String question=rs.getString("question");
            for(i=0;i<4;i++)
            {
                option[i]=rs.getString("option"+(i+1));
            }
            

            if("1".equals(request.getParameter("called")))
            {    
                num=1;
                marks=0;
                
                String queryForLoop="select count(*) from testquestions";
                //where `question type ID`=MCQ
                Statement stmt=con.createStatement();
                ResultSet res=stmt.executeQuery(queryForLoop);
                res.absolute(1);
                int n=Integer.parseInt(res.getString(1));
                ses.setAttribute("totalQues",n);
                
                //Cookie c[]=request.getCookies();
                /*for(i=0;i<c.length;i++)
                {    
                    if(c[i].getName()=="name")
                    {
                        name =c[i].getValue();
                        break;
                    }
                }
                */
            }
            else
            {
                num=(Integer)ses.getAttribute("quesNum");
                marks=(Integer)ses.getAttribute("marks");

                if(num>(Integer)ses.getAttribute("totalQues"))
                {
                    con.close();
                    response.sendRedirect("congrats.jsp");
                }    
                else
                {
                    //Test?Option=a

                    if(rs.getString("answer").equalsIgnoreCase(request.getParameter("option")))
                    {
                        marks=marks+3;
                    }
                    else
                        marks=marks-1;
                    //String query2="update tablename set score";
                    //how to store choices for student
                }
            }
            
            out.print("<p>Ques"+num+":"+question+"</p>");
            num++;
            ses.setAttribute("quesNum", num);
            ses.setAttribute("marks", marks);
            
            for(i=0;i<4;i++)
            {
                out.print("<input type=\"radio\" name=\"option\" value=\""+(i+1)+"\" />"+option[i]+"<br>");
            }
            out.println("<br><input value=\"Submit\" type=\"SUBMIT\" class=\"button\"><br><br>");
            out.println("<input value=\"Previous Question\" type=\"BUTTON\" class=\"button\">");
            out.println("<input value=\"Next Question\" type=\"BUTTON\" class=\"button\">");

            out.println("</center></form>");
            out.println("</body>");
            out.println("</html>");
            
        } 
        catch (SQLException ex) 
        {
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