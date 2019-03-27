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
<div id=divMenu>
<a href="IndexLogin.jsp">로그인</a>
</div>
<div id=divContent>
<h2>축하합니다! 회원으로 등록되었습니다.</h2>

<%
String username=request.getParameter("username"); 
 Cookie ck=new Cookie("username",username);  
 response.addCookie(ck);  
String password=request.getParameter("password"); 
String email=request.getParameter("email"); 
Class.forName("com.mysql.jdbc.Driver"); 
java.sql.Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_Servlet",
"root","1234"); 
Statement sql= DB.createStatement(); 
ResultSet rs; 
int rowsAffected=sql.executeUpdate("insert into user (username,password,email) values ('"+username+"','"+password+"','"+email+"')"); 
%>
</div>
</body>
</html>