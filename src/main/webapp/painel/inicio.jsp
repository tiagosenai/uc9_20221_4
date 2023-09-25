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
		  <input type="text" name="id" id="id" class="form-control" placeholder="Código do Usuário" readonly="readonly" value="${user01.id}">
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

<div class="modal" id="meuModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Consultar Usuário</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
        <div class="input-group mb-3">
	  		<input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
	  		<div class="input-group-append">
	    		<button class="btn btn-success" type="button" onclick="consultarUsuario();">Consultar</button>
	  	</div>
	  	<table class="table" id="tableUsuaio">
		  <thead>
		    <tr>
		      <th scope="col">ID</th>
		      <th scope="col">Usuário</th>
		      <th scope="col">Opção</th>
		    </tr>
		  </thead>
		  <tbody>
		    
		  </tbody>
		</table>
	</div>
	<span id="totalResultados"></span>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Fechar</button>
      </div>

    </div>
  </div>
</div>

<script type="text/javascript">

	function editarUsuario(id){
		var urlAction = document.getElementById("formUsuario").action;
		window.location.href = urlAction + '?acao=buscarEditar&id='+id;
	}

	function consultarUsuario() {
		var nomeBusca = document.getElementById('nomeBusca').value;
		
		if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){
			var urlAction = document.getElementById("formUsuario").action;
			
			$.ajax({
				
				method: "get",
			    url : urlAction,
			    data : "nomeBusca=" + nomeBusca + '&acao=consultarAjax',
			    success: function (response) {
				
			    	var json = JSON.parse(response);
			    	$('#tableUsuaio > tbody > tr').remove();
			    	for (var x = 0; x < json.length; x++){
			    		$('#tableUsuaio > tbody').append('<tr> <td>'+json[x].id+'</td><td>'+json[x].usuario+'</td><td><button onclick="editarUsuario('+json[x].id+')" type="button" class="btn btn-info">Editar</button></tr>')
			    	}
			    	document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
				}
			
			}).fail(function(xhr, status, errorThrown){
				    alert('Erro ao deletar usuário com Ajax: ' + xhr.responseText);
			});
		}
	}

	function apagarUsuarioAjax() {
		if(confirm("Deseha realemente apagar o Usuário com Ajax")){
			var urlAction = document.getElementById("formUsuario").action;
			var idUser = document.getElementById('id').value;
			
			$.ajax({
				
				method: "get",
			    url : urlAction,
			    data : "id=" + idUser + '&acao=deletarajax',
			    success: function (response) {
					
			    	limparDados();
					document.getElementById('msg').textContent = response;
				}
			
			}).fail(function(xhr, status, errorThrown){
				    alert('Erro ao deletar usuário com Ajax: ' + xhr.responseText);
			});
		}
	}

	function apagarUsuario(){
		if(confirm("Deseja realmente apagar o usuário?")){
			document.getElementById("formUsuario").method = 'get';
			document.getElementById("acao").value = 'deletar';
			document.getElementById("formUsuario").submit();
		}
	}

	function limparDados() {
		var campus = document.getElementById("formUsuario").elements;
		
		for (x=0; x < campus.length; x++){
			campus[x].value = '';
		}
	}

</script>
</body>
</html>