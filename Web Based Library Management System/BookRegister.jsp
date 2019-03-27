<html>
<head>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function checkForm(form){
var titlereg=form.titlereg.value;
var authorreg=form.authorreg.value;
var yearreg=form.yearreg.value;
var publishreg=form.publisherreg.value;
if(titlereg==""){
alert("제목 입력하세요!");
return false;
}
if(authorreg==""){
alert("저자명 입력하세요");
return false;
}
if(yearreg==""){
alert("출판년도 입력하세요");
return false;
}
if(publisherreg==""){
alert("출판사 입력하세요");
return false;
}
return true;
}
</script>
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
<h2>도서 등록</h2><br>
<form action=BookRegisterOK.jsp onSubmit="return checkForm(this)" method=POST >
<table align=center>
<tr><td align=right>제목</td><td><input type=text name=titlereg value=""></input></td></tr>
<tr><td align=right>저자명</td><td><input type=text name=authorreg value=""></input></td></tr>
<tr><td align=right>출판년도</td><td><input type=text name=yearreg></input></td></tr>
<tr><td align=right>출판사</td><td><input type=text name=publisherreg></input></td></tr>
<tr><td colspan=2 align=right><input type=submit value=등록></input></td></tr>
</table>
</form>
</div>


</body>
</html>