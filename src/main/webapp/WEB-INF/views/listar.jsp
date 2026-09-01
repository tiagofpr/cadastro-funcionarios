<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Funcionários cadastrados</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>Funcionários cadastrados</h1>

    <c:if test="${param.sucesso == 'cadastrado'}">
        <p class="alerta alerta-sucesso">Funcionário cadastrado com sucesso!</p>
    </c:if>
    <c:if test="${param.sucesso == 'atualizado'}">
        <p class="alerta alerta-sucesso">Funcionário atualizado com sucesso!</p>
    </c:if>
    <c:if test="${param.sucesso == 'deletado'}">
        <p class="alerta alerta-sucesso">Funcionário removido com sucesso!</p>
    </c:if>

    <p><a href="${pageContext.request.contextPath}/funcionarios/novo">Cadastrar novo funcionário</a></p>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Cargo</th>
            <th>Salário</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="funcionario" items="${funcionarios}">
            <tr>
                <td>${funcionario.id}</td>
                <td>${funcionario.nome}</td>
                <td>${funcionario.cargo}</td>
                <td>${funcionario.salario}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/funcionarios/editar?id=${funcionario.id}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/funcionarios/deletar"
                          onsubmit="return confirm('Tem certeza que deseja deletar este funcionário?');" style="display:inline;">
                        <input type="hidden" name="id" value="${funcionario.id}">
                        <button type="submit">Deletar</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>