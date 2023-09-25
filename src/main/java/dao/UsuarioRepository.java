package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoBanco;
import model.Usuario;

public class UsuarioRepository {
	private Connection conn;
	
	public UsuarioRepository() {
		conn = ConexaoBanco.getConnection();
	}
	
	public Usuario insereUsuario(Usuario objeto) throws Exception{
		if (objeto.ehNovo()) {
			String sql = "INSERT INTO usuario(usuario, senha) VALUES(?, ?);";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, objeto.getUsuario());
			stmt.setString(2, objeto.getSenha());
			
			stmt.execute();
			
			conn.commit();	
		}else {
			String sql = "UPDATE usuario SET usuario=?, senha=? WHERE id = "+objeto.getId()+";";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, objeto.getUsuario());
			stmt.setString(2, objeto.getSenha());
			
			stmt.executeUpdate();
			
			conn.commit();
		}
		return this.consultarUsuario(objeto.getUsuario());
	}
	
	//Consulta Usuario após o Insert e Update
		public Usuario consultarUsuario(String usuario) throws Exception{
			Usuario user01 = new Usuario();
			
			String sql = "SELECT * FROM usuario WHERE usuario = '"+usuario+"'";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rst = stmt.executeQuery();
			
			while (rst.next()) {
				user01.setId(rst.getLong("id"));
				user01.setUsuario(rst.getString("usuario"));
				user01.setSenha(rst.getString("senha"));
			}
			
			return user01;
		}
	
	//Verficiar usuário e senha para Login
		public boolean vericaUsuario(String usuario) throws Exception{
			String sql = "SELECT COUNT(1) > 0 AS EXISTE FROM usuario where usuario = '"+usuario+"';";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet res = stmt.executeQuery();
			
			res.next();
			return res.getBoolean("existe");
		}
		
		//Deletar o Usuário JS
		public void deletarUsuario(String userId) throws Exception{
			String sql = "DELETE FROM usuario WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(userId));
			stmt.executeUpdate();
			conn.commit();
			
		}
		
		public List<Usuario> consultarUsuarioLista(String nome) throws Exception{
			List<Usuario> listaUsuario = new ArrayList<Usuario>();
			String sql = "SELECT * FROM usuario WHERE usuario like ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + nome + "%");
			ResultSet rst = stmt.executeQuery();
			while (rst.next()) {
				Usuario user01 = new Usuario();
				user01.setId(rst.getLong("id"));
				user01.setUsuario(rst.getString("usuario"));
				user01.setSenha(rst.getString("senha"));
				
				listaUsuario.add(user01);
			}
			return listaUsuario;
		}
		
		public Usuario consultarUsuarioID(String id) throws Exception{
			Usuario user01 = new Usuario();
			String sql = "SELECT * FROM usuario WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(id));
			ResultSet rst = stmt.executeQuery();
			while (rst.next()) {
				user01.setId(rst.getLong("id"));
				user01.setUsuario(rst.getString("usuario"));
				user01.setSenha(rst.getString("senha"));
			}
			return user01;
		}
}
