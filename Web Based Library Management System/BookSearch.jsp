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
<%
Cookie ck[]=request.getCookies();
 String name=ck[1].getValue();  
 out.print("User: "+name);
%>
</td>
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
<h2>도서 검색</h2><br>
<form action=BookSearch2.jsp method=GET>
<%
int MAX_PAGE = 0;
int currentPageNo=0;
%>
<table align=center><tr>
<td><select name=searchField>
<option value="title" selected>제목</option>
<option value="author">저자명</option>
<option value="year">출판년도</option>
<option value="publisher">출판사</option>
</select></td>
<td><input type=text name=query></td>
<td><select name=loanOption>
<option value="loaned" selected>나의 대출 도서</option>
<option value="all">전체 더사</option></td>
<td><a href=\"BookSearch2.jsp?pages="+currentPageNo+"&noOfPages="+MAX_PAGE+"\"><input type=submit value=검색 ></a></td>
</tr></table>
</form>
</div>


</body>
</html>