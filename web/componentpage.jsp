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
            String query1="select componentid,componentname from components where available>0 order by componentname";
            String mem="select ID,Name from members order by Name";
            String find="select cname,holder,cid,holderid from componentinfo";
            ResultSet rs;
            Statement stmt;
            int i;    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Component Page</title>
    </head>
    <body>
        <h1>Component Page</h1>
        <h4>Allocate a component</h4>
        
        <form method="get" action="Component">
        <pre>
        <%  
            int count = 0;
            try 
            {
                stmt = con.createStatement();
                rs=stmt.executeQuery(query1);
                while (rs.next()) 
                {
                    ++count;
                }  
                if(rs.first())
                {
        %>
Component   <select name="comp">
        <%
                    for(i=0;i<count;i++)
                    {            
                        String component=rs.getString("componentname");
                        String componentid=rs.getString("componentid");
        %>
                        <option value="<%= componentid %>"><%=component%></option>
        <%                
                        rs.next();
                    }
        %>
            </select>
            
Member      <select name="person">
        <%      
                    stmt = con.createStatement();
                    rs=stmt.executeQuery(mem);
                    count =0;
                    while (rs.next()) 
                    {
                        ++count;
                    }  
                    if(rs.first())
                    for(i=0;i<count;i++)
                    {
                        String holder =rs.getString("Name");
                        String holderid=rs.getString("ID");
        %>
                        <option value="<%= holderid %>"><%=holder%></option>
        <%                
                        rs.next();
                    }
        %>
            </select>
        <%
                }
        %>        
            <button type="submit">Allocate</button>
        </pre></form>
        <hr>
        <h4>Find a component</h4>
        <form method="get" action="ReceivedComponent"><table border="1">
            <colgroup width="200">
            <colgroup width="200">
        <tr>
				<th>Component</th>
				<th>Name</th>
                                <th>Got back</th>
        </tr>
	<%      
                stmt = con.createStatement();
                rs=stmt.executeQuery(find);
                count =0;
                String component,holder;
                int cid,hid;
                while (rs.next()) 
                {
                    ++count;
                }  
                if(rs.first())
                {
                    for(i=0;i<count;i++)
                    {
                        component=rs.getString("cname");
                        holder =rs.getString("holder");
                        cid=rs.getInt("cid");
                        hid=rs.getInt("holderid");
        %>
                    <tr>
				<td><%= component %></td>
				<td><%= holder %></td>
                                <td><input type="radio" name="rec" value="<%=cid%>,<%=hid%>"> </td>
                    </tr>
        <%                
                        rs.next();
                    }
                }
                con.close();
            }
            catch (SQLException ex) 
            {
                out.print("ERROR");
            }
        %>
        </table> 
        <input value="Submit" type="submit" >
        </form>
        <hr>
        <h4>Add a new component</h4>
        <form method="get" action="AddComponent">
        <pre>
Name		<input  required type='text' name='cname' ><br>
Price		<input  required type='number' name='price'> <br>
Quantity        <input  required type='number' name='quant'><br>
Company		<input type='text' name='company'> <br>
                    <input value="Submit" type="submit" >		
        </pre>
        </form>
    </body>
</html>