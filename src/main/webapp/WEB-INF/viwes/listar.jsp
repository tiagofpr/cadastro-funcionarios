<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Funcionários cadastrados</title>
</head>
<body>
    <h1>Funcionários cadastrados</h1>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Cargo</th>
            <th>Salário</th>
        </tr>
        <c:forEach var="funcionario" items="${funcionarios}">
            <tr>
                <td>${funcionario.id}</td>
                <td>${funcionario.nome}</td>
                <td>${funcionario.cargo}</td>
                <td>${funcionario.salario}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>