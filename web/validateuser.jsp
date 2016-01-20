<%-- 
    Document   : validateuser
    Created on : Jan 20, 2016, 12:46:10 PM
    Author     : mayank
--%>

<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Connection.Conn"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
            Connection con;
            con = Conn.getCon();
            String mem="select Name from new_members order by Name";
            ResultSet rs;
            Statement stmt;
            int i;    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Validate User</title>
    </head>
    <body>
        <h1>Select user to validate</h1>
        <form method="get" action="Validate">
            <%  
            int count = 0;
            try 
            {
                stmt = con.createStatement();
                rs=stmt.executeQuery(mem);
                while (rs.next()) 
                {
                    ++count;
                }  
                if(rs.first())
                {
        %>
Name        <select name="user">
        <%
                    for(i=0;i<count;i++)
                    {            
                        String name=rs.getString("Name");
        %>
                        <option value="<%= name %>"><%=name%></option>
        <%                
                        rs.next();
                    }
        %>
            </select>
        <%
                }
            }
            catch(Exception e)
            {
                out.print("ERROR");
            }
            con.close();
        %>
           
            <button type="submit">Validate</button>
        </form>
    </body>
</html>
