import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import Connection.Conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            
            //Component?comp=2&person=150001
            //String query1="update components set holderid=?,holder=?,available=? where componentid=?";
            String query1="insert into componentinfo(cid,cname,holderid,holder) values(?,?,?,?)";
            String getName="select Name from members where ID=?";
            String getAvailability="select available,componentname from components where componentid=?";
            String setAvailability="update components set available=? where componentid=?";
            ResultSet rs,rs2;
            PreparedStatement ps,ps2,ps3,ps4;
            String cname,name;
            int cid,avail,id;
            try {
                cid=Integer.parseInt(request.getParameter("comp"));
                id=Integer.parseInt(request.getParameter("person"));
                
                ps=con.prepareStatement(getAvailability);
                ps.setInt(1, cid);
                rs=ps.executeQuery();
                rs.absolute(1); 
                avail=rs.getInt("available");
                cname=rs.getString("componentname");
                avail--;
                
                ps2=con.prepareStatement(getName);
                ps2.setInt(1, id);
                rs=ps2.executeQuery();
                rs.absolute(1); 
                
                name=rs.getString("Name");
                
                ps4=con.prepareStatement(setAvailability);
                ps4.setInt(1, avail);
                ps4.setInt(2, cid);
                int j=ps4.executeUpdate();
                
                ps3=con.prepareStatement(query1);
                ps3.setInt(1, cid);
                ps3.setString(2, cname);
                ps3.setInt(3, id);
                ps3.setString(4, name);
                int i=ps3.executeUpdate();
                 
                if(i==1)
                {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Allocate</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>The component "+ cname +" is allocated to "+name+"</h1>");
                    out.println("<a href=\"componentpage.jsp\">Go to admin home</a>");
                    out.println("</body");
                    out.println("</html");
                }
                con.close();
                
            } catch (SQLException ex) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Allocate</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Error</h1>");
                out.println("<a href=\"componentpage.jsp\">Go to admin home</a>");
                out.println("</body");
                out.println("</html");
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

/*
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
*/