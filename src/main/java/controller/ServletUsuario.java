package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UsuarioRepository;



public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioRepository userRepository = new UsuarioRepository();
	
    public ServletUsuario() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			String acao = request.getParameter("acao");
			if (acao != null && !acao.isEmpty() & acao.equalsIgnoreCase("deletar")) {
				
				String userId = request.getParameter("id");
				
				userRepository.deletarUsuario(userId);
				request.setAttribute("msg", "Excluído com Sucesso!!!");
				request.getRequestDispatcher("painel/inicio.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
				
				String userId = request.getParameter("id");
				
				userRepository.deletarUsuario(userId);
				
				response.getWriter().write("Excluído com Ajax");
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("consultarAjax")) {
				
				String nome = request.getParameter("nomeBusca");
				List<Usuario> dadosUsuario = userRepository.consultarUsuarioLista(nome);
				ObjectMapper mapa = new ObjectMapper();
				String json = mapa.writeValueAsString(dadosUsuario);
				response.getWriter().write(json);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				String id = request.getParameter("id");
				Usuario user01 = userRepository.consultarUsuarioID(id);
				
				request.setAttribute("msg", "Usuário em Edição!!!");
				request.setAttribute("user01", user01);
				request.getRequestDispatcher("painel/inicio.jsp").forward(request, response);
				
			}else {
				request.getRequestDispatcher("painel/inicio.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			RequestDispatcher redireciona = request.getRequestDispatcher("painel/error.jsp");
			request.setAttribute("msg", e.getMessage());
			redireciona.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String mensagem = "Cadastro Realizado com Sucesso!!";
			String id = request.getParameter("id");
			String usuario = request.getParameter("usuario");
			String senha = request.getParameter("senha");
			
			Usuario user01 = new Usuario();
			
			user01.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			user01.setUsuario(usuario);
			user01.setSenha(senha);
			
			if (userRepository.vericaUsuario(user01.getUsuario()) && user01.getId() == null) {
				
				mensagem = "Usuário já cadastrado, informe outro usuário!!!";
				
			}else {
				
				if (user01.ehNovo()) {
					
					mensagem = "Gravado com Sucesso!!";
					
				}else {
					
					mensagem = "Atualizado com Sucesso!!";
					
				}
				user01 = userRepository.insereUsuario(user01);
			}
			
			request.setAttribute("msg", mensagem);
			request.setAttribute("user01", user01);
			request.getRequestDispatcher("painel/inicio.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			RequestDispatcher redireciona = request.getRequestDispatcher("erros.jsp");
			request.setAttribute("msg", e.getMessage());
			redireciona.forward(request, response);
		}
	}

}
