<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
try {
	
	Class.forName("com.mysql.jdbc.Driver");
    Connection DB=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_Servlet", "root", "1234");
    Statement sql=DB.createStatement();
	
	String name=request.getParameter("username");
	int booknumber=Integer.parseInt(request.getParameter("bookId"));
	String Field1=request.getParameter("field1");	
	String Field2=request.getParameter("field2");
	String Field3=request.getParameter("field3");
	
	ResultSet cursor = sql.executeQuery("select status from list where number="+booknumber);	
	cursor.next();
	int statussign = cursor.getInt("status");
	
  if(statussign==0){
	 cursor = sql.executeQuery("select userid from user where username='"+name+"'");
	 cursor.next();
	 int userID = cursor.getInt("userid");
	 int updateStatus = sql.executeUpdate("update list set status="+userID+" where number="+booknumber);
	 }
 else if(statussign > 0){
	int updateStatus =sql.executeUpdate("update list set status=0 where number="+booknumber);
}
 response.sendRedirect("BookCheckout.jsp?searchField="+Field1+"&query="+Field2+"&loanOption="+Field3);
 } catch (Exception e) {
 } 




%>