<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style/style.css" media="screen" />

<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>

<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css"/>

<title>Авторизация</title>
</head>
<body>


<div class="wrapper">
    <form class="form-signin" action="auth">       
      <h2 class="form-signin-heading">Авторизация</h2>
      <input type="text" class="form-control" name="login" placeholder="Логин" required="" autofocus="" />
      <input type="password" class="form-control" name="password" placeholder="Пароль" required=""/>      
     <!--<label class="checkbox">
        <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"> Remember me
      </label>--> 
      <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>   
    </form>
  </div>

</body>
</html>