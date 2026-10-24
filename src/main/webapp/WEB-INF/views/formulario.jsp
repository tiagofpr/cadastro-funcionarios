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
      action="${pageContext.request.contextPath}${editando ? '/funcionarios/editar' : '/funcionarios/novo'}"
      onsubmit="this.querySelector('button[type=submit]').disabled = true;">

    <c:if test="${editando}">
        <input type="hidden" name="id" value="${funcionario.id}">
    </c:if>

    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" value="${funcionario.nome}"
           required maxlength="100" pattern=".*\S.*"
           title="Informe o nome (não pode ser só espaços)">

    <label for="cargo">Cargo:</label>
    <input type="text" id="cargo" name="cargo" value="${funcionario.cargo}"
           required maxlength="100" pattern=".*\S.*"
           title="Informe o cargo (não pode ser só espaços)">

    <label for="salario">Salário:</label>
    <input type="number" id="salario" name="salario" value="${funcionario.salario}"
           required min="0.01" step="0.01">

    <button type="submit">${editando ? 'Salvar alterações' : 'Salvar'}</button>
</form>

    <p><a href="${pageContext.request.contextPath}/funcionarios">Voltar para a lista</a></p>
</body>
</html>