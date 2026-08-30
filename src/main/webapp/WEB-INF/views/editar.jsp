<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Editar Funcionário</title>
</head>
<body>
    <h1>Editar Funcionário</h1>

    <c:if test="${not empty erro}">
        <p style="color: red;">${erro}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/funcionarios/editar">

        <input type="hidden" name="id" value="${funcionario.id}">

        <label>Nome:</label><br>
        <input type="text" name="nome" value="${funcionario.nome}"><br><br>

        <label>Cargo:</label><br>
        <input type="text" name="cargo" value="${funcionario.cargo}"><br><br>

        <label>Salário:</label><br>
        <input type="text" name="salario" value="${funcionario.salario}"><br><br>

        <button type="submit">Salvar alterações</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/funcionarios">Voltar para a lista</a></p>
</body>
</html>