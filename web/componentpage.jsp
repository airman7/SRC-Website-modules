<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Connection.Conn"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
            Connection con;
            con = Conn.getCon();
            String query1="select componentid,componentname,quantity from components";
            ResultSet rs;
            Statement stmt;
            int count = 0;
            try 
            {
                stmt = con.createStatement();
                rs=stmt.executeQuery(query1);
                while (rs.next()) 
                {
                    ++count;
                }       
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Component Page</title>
    </head>
    <body>
        <h1>Component Page</h1>
        <select>
        <%      int i;
                if(rs.first())
                {
                    for(i=0;i<count;i++)
                    {
                        
                        String component=rs.getString("componentname");
        %>
                        <option value="<%= component %>"><%=component%></option>
        <%                
                        rs.next();
                    }
                }
                else
                {
        %>
                    <option value="no">No component</option>
        <%
                }
            }
            catch (SQLException ex) 
            {
                out.print("ERROR");
            }
        %>        
        </select>
        <form method="get" action="Component">
             <button type="submit">Allocate</button>
        </form>
        <form method="get" action="FindComponent">
             <button type="button">Find</button>
        </form>  
    </body>
</html>
