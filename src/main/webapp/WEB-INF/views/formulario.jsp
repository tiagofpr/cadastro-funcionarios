<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="editando" value="${not empty funcionario.id}"/>

<!DOCTYPE html>
<html>
<head>
    <title>${editando ? 'Editar' : 'Cadastrar'} Funcionário</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>${editando ? 'Editar' : 'Cadastrar'} Funcionário</h1>

    <c:if test="${not empty erro}">
        <p class="alerta alerta-erro">${erro}</p>
    </c:if>

    <form method="post"
          action="${pageContext.request.contextPath}${editando ? '/funcionarios/editar' : '/funcionarios/novo'}">

        <c:if test="${editando}">
            <input type="hidden" name="id" value="${funcionario.id}">
        </c:if>

        <label>Nome:</label>
        <input type="text" name="nome" value="${funcionario.nome}">

        <label>Cargo:</label>
        <input type="text" name="cargo" value="${funcionario.cargo}">

        <label>Salário:</label>
        <input type="text" name="salario" value="${funcionario.salario}">

        <button type="submit">${editando ? 'Salvar alterações' : 'Salvar'}</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/funcionarios">Voltar para a lista</a></p>
</body>
</html>