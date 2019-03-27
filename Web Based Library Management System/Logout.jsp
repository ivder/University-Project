<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
Cookie ck=new Cookie("username","");  
ck.setMaxAge(0);  
response.addCookie(ck); 
response.sendRedirect("IndexLogin.jsp");
%>