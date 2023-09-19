package filter;

import java.io.IOException;
import java.sql.Connection;

import conexao.ConexaoBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/painel/*"})
public class FilterPainel extends HttpFilter implements Filter {

	private static Connection conn;
	
    public FilterPainel() {

    }


	public void destroy() {
		//Matar os processos do servidor
		//Encerrar a conexão com o Banco de Dados
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	//Interceptar requisições e respostas do sistema
    	//Validar a autenticação
    	//Commit e Rollback
    	//Validar e Redirecionar páginas de aplicação
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession sessao = req.getSession();
			
			String usuarioLogado = (String) sessao.getAttribute("usuario");
			String urlAutenticar = req.getServletPath();
			
			if (usuarioLogado == null && !urlAutenticar.equalsIgnoreCase("/painel/ServletOi")) {
				RequestDispatcher redireciona = request.getRequestDispatcher("/login");
				request.setAttribute("msg", "Por Favor efetue o Login!!!");
				redireciona.forward(request, response);
				return;
			}else {
				chain.doFilter(request, response);
			}
			conn.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			RequestDispatcher redireciona = request.getRequestDispatcher("erros.jsp");
			request.setAttribute("msg", e.getMessage());
			redireciona.forward(request, response);
			try {
				//desfazer as alterações no banco
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exceptione.
				e.printStackTrace();
			}
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		//Iniciar a conexão com o Banco de Dados
		conn = ConexaoBanco.getConnection();
	}

}
