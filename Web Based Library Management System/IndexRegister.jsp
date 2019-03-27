<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function checkForm(form){
var username=form.username.value;
var password=form.password.value;
var email=form.email.value;
if(username==""){
alert("Insert Name!");
return false;
}
if(password==""){
alert("Insert Password!");
return false;
}
if(email==""){
alert("Insert Email!");
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
<div id=divMenu>
<a href="IndexLogin.jsp">로그인</a>
</div>
<div id=divContent>
<h2>회원 등록</h2>
<form action=IndexRegisterOK.jsp onSubmit="return checkForm(this)" method=POST >
<table align=center>
<tr><td align=right>아이디</td><td><input type=text name=username value=""></input></td></tr>
<tr><td align=right>패스워드</td><td><input type=password name=password value=""></input></td></tr>
<tr><td align=right>전자메일주소*</td><td><input type=text name=email></input></td></tr>
<tr><td colspan=2 align=right><input type=submit value=등록></input></td></tr>
</table>
</form>
</div>
</body>
</html>