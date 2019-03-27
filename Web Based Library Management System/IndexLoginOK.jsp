<html>
<head>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="Index.css">
<title>Books</title>
</head>
<body>
<div id=divBanner>도서 관리</div>
<div id=divContent>
<table align=center cellpadding="10">
<tr>
<td><%
String displayname=request.getParameter("username"); 
out.println("User: "+displayname);
%></td>
<td><a href="Logout.jsp">Logout</a></td>
</tr></table>
</div>

<div id=divMenu>
<table align=center cellpadding="5">
<tr>
<td><a href="BookSearch.jsp">도서검색</a></td>
<td><a href="BookRegister.jsp">도서등록</a></td>
</tr></table>
</div>
<div id=divContent>

<%
String username=request.getParameter("username"); 
Cookie ck=new Cookie("username",username);  
response.addCookie(ck);  
String password=request.getParameter("password"); 
Class.forName("com.mysql.jdbc.Driver"); 
java.sql.Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_Servlet",
"root","1234"); 
Statement sql= DB.createStatement(); 
ResultSet rs=sql.executeQuery("select * from user where username='"+username+"'"); 
if(rs.next()) 
{ 
if(rs.getString(3).equals(password)) 
{ 
out.println("<h2>");
out.println(username+" 님 환영합니다."); 
out.println("</h2>");
} 
else 
{ 
response.sendRedirect("WrongPassword.jsp");
} 
} 
else{
response.sendRedirect("WrongUsername.jsp");
}
%>
</div>
</body>
</html>