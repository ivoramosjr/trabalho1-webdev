<jsp:useBean id="usuario" scope="request" type="br.com.senac.trabalho1.entity.Usuario"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Página Restrita :O</title>
</head>
<body>
    <div>
        <h1>Logado!</h1>
    </div>
    <div>
        <h3>Bem vindo(a) a página restrita usuário: ${usuario.login}</h3>
    </div>
</body>
</html>
