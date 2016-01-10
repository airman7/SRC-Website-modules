
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Student</h1>
        <center><pre><form method="get" action="CheckStudent">
<fieldset>
<%-- 
    Name        <input required name="name" min="1" type="text"><br>
    Branch      <select name="branch">
                <option value="CS">Computer Science</option>
                <option value="IT">Information Technology</option>
                <option value="EC">Elec. and tele.</option>
                <option value="Civil">Civil</option>
    </select><br>
    
--%>
    SRC ID      <input required name="id" min="1" type="text"><br>
    Password    <input required name="pass" type="password"><br>
                <input value="Submit" type="SUBMIT" class="button">
</fieldset>        </form></pre></center>
    <a href="componentpage.jsp">component module</a>
    </body>
</html>
