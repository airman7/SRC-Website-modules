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
import java.util.Date;

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
            //response.setIntHeader("Refresh", 1);
            int i;
            int comp[];
            String answer,choice;
            String name="";
            name= (String) ses.getAttribute("name");
            int num;
            long time;
            int randomNum=0;
            Random rand;
            String option[]=new String[4];
            Date today = new Date();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Test Module</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Welcome " + name + "</h1>");
            
            try
            {
                time=today.getTime()-((long) ses.getAttribute("startTime"));
            }
            catch(Exception e)
            {
                time=0;
                ses.setAttribute("startTime", today.getTime());
            }
            out.println("Time:"+time/1000+"sec");
            
            out.println("<form action=\"Test\"><center>");
            String query="select * from testquestions where questionID=?";
            
            comp=(int[]) ses.getAttribute("completed");
            
            int temp=0;
            boolean flag=true;
            
            if(!"1".equals(request.getParameter("called")))    
            {
                while(flag)
                {
                    rand=new Random();
                    randomNum = rand.nextInt(5) + 1;
                    temp=0;
                    for(i=0;i<comp.length;i++)
                        if(comp[i]!=randomNum)
                            temp++;
                    if(temp==comp.length)
                        flag=false;
                }
                System.out.println("step one done");
            }
            else
            {
                rand=new Random();
                randomNum = rand.nextInt(5) + 1;
                System.out.println("step one done");
            }
            
            String value="M00"+randomNum;
            
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
                
                //make a list of question already done in session
                comp=new int[n];
                comp[0]=randomNum;
                ses.setAttribute("completed", comp);
                
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
                comp=(int[]) ses.getAttribute("completed");
                int k=0;
                for(i=0;i<comp.length;i++)
            {
                System.out.println("completed:"+comp[i]);
            }
                while(comp[k]!=0)
                    k++;
                comp[k]=randomNum;
                ses.setAttribute("completed", comp);
               
                
                num=(Integer)ses.getAttribute("quesNum");
                marks=(Integer)ses.getAttribute("marks");
                //answer stored in session
                answer=(String) ses.getAttribute("answer");
                
                //Test?option=1
                //option selsected by user
                choice=request.getParameter("option");
                
                if(choice.equals(answer))
                {
                    marks=marks+3;
                }
                else
                    marks=marks-1;
                //String query2="update tablename set score";
                 
                System.out.println(marks);
            
                if(num>(Integer)ses.getAttribute("totalQues"))
                {
                    con.close();
                    response.sendRedirect("congrats.jsp");
                }    
            }
            
            ses.setAttribute("answer",rs.getString("answer"));
                    
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