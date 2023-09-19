package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;

import dao.LoginRepository;

@WebServlet("/ServletOi")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private LoginRepository loginRepository = new LoginRepository();
	
    public ServletLogin() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("email");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				Usuario user01 = new Usuario();
				user01.setUsuario(login);
				user01.setSenha(senha);
				if (loginRepository.validarLogin(user01)) {
					request.getSession().setAttribute("usuario", user01.getUsuario());
					if (url == null || url.equals("null")) {
						url = "painel/inicio.jsp";
					}
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
				}else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("login.jsp");
					request.setAttribute("msg", "Usuário ou Senha incorretos");
					redirecionar.forward(request, response);
				}
			}else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("login.jsp");
				request.setAttribute("msg", "Informe o LOGIN e SENHA corretamente");
				redirecionar.forward(request, response);
	 		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
