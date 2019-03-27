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
<td> 
<% Cookie ck[]=request.getCookies();
 String name=ck[1].getValue();  
 out.print("User: "+name);%></td>
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
<h2>도서 등록이 완료되었습니다.</h2>

<%
String titlereg=request.getParameter("titlereg");   
String authorreg=request.getParameter("authorreg"); 
String yearreg=request.getParameter("yearreg");
String publisherreg=request.getParameter("publisherreg");
Class.forName("com.mysql.jdbc.Driver"); 
java.sql.Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_Servlet",
"root","1234"); 
Statement sql= DB.createStatement(); 
ResultSet rs; 
int rowsAffected=sql.executeUpdate("insert into list (title,author,year,publisher,status) values ('"+titlereg+"','"+authorreg+"','"+yearreg+"','"+publisherreg+"',0)"); 
%>
</div>
</body>
</html>