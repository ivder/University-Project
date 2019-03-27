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
<h2>도서 검색 결과</h2><br>
<form action=Checkout.jsp method=GET>

<%

try {

out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
out.println("<table border=1 cellspacing=0 align=center>");

String searchfield=request.getParameterValues("searchField")[0];
String query=request.getParameterValues("query")[0];
String loanoption=request.getParameterValues("loanOption")[0];

out.println("<tr align=center bgcolor=lightgrey><td>고유번호</td><td>서명</td><td>조자</td><td>출판년도</td><td>출판사</td><td>처리</td><tr>");
Class.forName("com.mysql.jdbc.Driver");
Connection DB=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_Servlet", "root", "1234");
Statement sql=DB.createStatement(); 

ResultSet select=sql.executeQuery("select userid from user where username='"+name+"'");
select.next();
int sign = select.getInt("userid");

int PAGE_SIZE = 10;
int MAX_PAGE = 0;
int currentPageNo=0;

if(request.getParameter("pages")!=null){
currentPageNo=Integer.parseInt((String)request.getParameter("pages"));
MAX_PAGE=Integer.parseInt((String)request.getParameter("noOfPages"));
}

String sqlQuery = "select * from list";
String sqlCount = "select count(*) from list";
if (loanoption.equals("all") && query=="") {
	sqlQuery += " limit " + currentPageNo*PAGE_SIZE+", "+PAGE_SIZE;
}
if (loanoption.equals("loaned") && query=="") {
		sqlQuery += " where status="+sign;
		sqlCount += " where status="+sign;
}
if (loanoption.equals("loaned") && query!="") {
		sqlQuery += " where status="+sign+" and "+searchfield+"='"+query+"' ";
		sqlCount += " where status="+sign+" and "+searchfield+"='"+query+"' ";
}
if (loanoption.equals("all") && query!="") {
		sqlQuery += " where "+searchfield+"='"+query+"' ";
		sqlCount += " where "+searchfield+"='"+query+"' ";
}
ResultSet count =sql.executeQuery(sqlCount);
count.next();
MAX_PAGE = count.getInt(1) / PAGE_SIZE;

ResultSet cursor=sql.executeQuery(sqlQuery);
while(cursor.next()){
	out.println("<tr align=center>");
	out.println("<td>"+cursor.getInt("number")+"</td><td>"+cursor.getString("title")+"</td><td>"+cursor.getString("author")+"</td><td>"+cursor.getString("year")+"</td><td>"+cursor.getString("publisher")+"</td>");
	int status = cursor.getInt("status");
	if (status != 0 && status!=sign) out.println ("<td>대출중</td>");
	else {
		out.println("<td><a href=\"Checkout.jsp?bookId="+cursor.getInt("number")+"&username="+name+"&field1="+searchfield+"&field2="+query+"&field3="+loanoption+" \"");
		if (status==0) out.print(">대출</a></td>");
		else out.print(">반납</a></td>");
		out.println("</tr>");
	}
}
out.println("</table>");

out.println("<table align=center cellpadding=10 border=0> <tr>");

// First page
if (currentPageNo >= 1) {
int previousPage =currentPageNo-1;

out.println("<td><a href=\"BookSearch2.jsp?pages="+previousPage+"&noOfPages="+MAX_PAGE+"\"><b> Previous<< </b></a></td>");

} else {

}

if (MAX_PAGE > currentPageNo) {
// Next page
int nextPage = currentPageNo + 1;
int i=0;

for(i=1;i<=MAX_PAGE/PAGE_SIZE;i++)
{
	String ordernum=Integer.toString(i);
	out.print("<td><a href=\"BookSearch2.jsp?pages="+i+"&noOfPages="+MAX_PAGE+"\"><b>"+ordernum+"</b></a></td>");

}
out.print("<td><a href=\"BookSearch2.jsp?pages="+nextPage+"&noOfPages="+MAX_PAGE+"\"><b> >> Next </b></a></td>");

            // Last Page

} else {
}
out.println("</tr></table>");


} catch (Exception e) { out.println(e.getMessage()); }

%>
</form>
</div>
</body>
</html>