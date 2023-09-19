<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
	<jsp:include page="bootstrap.jsp"></jsp:include>
</head>
<body>
	<h1 style="text-align: center;">Painel Administrativo do Site</h1>
	<jsp:include page="navbar.jsp"></jsp:include>
	<div class="container">
	<form action="<%= request.getContextPath() %>/ServletUsuario" method="post" id="formUsuario">
		<div class="mb-3">
		  <label for="formGroupExampleInput" class="form-label">Código</label>
		  <input type="text" name="id" id="id" class="form-control" placeholder="Nome do Usuário" readonly="readonly" value="${user01.id}">
		</div>
		<div class="mb-3">
		  <label for="formGroupExampleInput" class="form-label">Usuário</label>
		  <input type="text" name="usuario" id="usuario" class="form-control" placeholder="Nome do Usuário" value="${user01.usuario}">
		</div>
		<div class="mb-3">
		  <label for="formGroupExampleInput2" class="form-label">Senha</label>
		  <input type="password" class="form-control" name="senha" id="senha" placeholder="Senha do Usuário" value="${user01.senha}">
		</div>
		<input type="hidden" name="acao" id="acao" value="">
		<button type="submit" class="btn btn-success">Salvar</button>
		<button type="button" class="btn btn-info" onclick="limparDados();">Novo</button>
		<button type="button" class="btn btn-warning" onclick="apagarUsuario();">Apagar</button>
		<button type="button" class="btn btn-danger" onclick="apagarUsuarioAjax();">Apagar Ajax</button>
		<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#meuModal">Consultar Usuário</button>
	</form>
</div>
<div class="container">
	<span id="mensagem">${msg}</span>
</div>
<script>
function limparDados() {
	var campus = document.getElementById("formUsuario").elements;
	
	for (x=0; x < campus.length; x++){
		campus[x].value = '';
	}
}
</script>
</body>
</html>