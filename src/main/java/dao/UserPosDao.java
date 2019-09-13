package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.jdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.UserPosJava;

public class UserPosDao {

	private Connection connection;

	public UserPosDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(UserPosJava userPosjava) {
		try {
			String sql = "insert into userposjava (nome, email) values(?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userPosjava.getNome());// parametro 2, valor "Fulano de Tal";
			insert.setString(2, userPosjava.getEmail());
			insert.execute();
			connection.commit();// salva no banco
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void salvarTelefone(Telefone telefone) throws SQLException {
		try {
			String sql = "INSERT INTO telefoneuser " + 
					"(numero,tipo,usuariopessoa)" + 
					"VALUES (?,?,?)";
			PreparedStatement instrucao = connection.prepareStatement(sql);
			instrucao.setString(1, telefone.getNumero());
			instrucao.setString(2, telefone.getTipo());
			instrucao.setLong(3, telefone.getUsuario());
			System.out.println(telefone);
			instrucao.execute();
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}
	}

	public List<UserPosJava> listar() throws Exception {
		List<UserPosJava> list = new ArrayList<UserPosJava>();

		String sql = "select * from userposjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			UserPosJava userPosJava = new UserPosJava();
			userPosJava.setId(resultado.getLong("id"));
			userPosJava.setNome(resultado.getString("nome"));
			userPosJava.setEmail(resultado.getString("email"));

			list.add(userPosJava);
		}
		return list;
	}

	public UserPosJava getById(Long id) throws Exception {
		UserPosJava retorno = new UserPosJava();

		String sql = "select * from userposjava where id = " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // repete enquanto tiver dados
			// UserPosJava userPosJava = new UserPosJava();
			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));
		}
		return retorno;
	}
	
	public List<BeanUserFone> listaUserFone (Long idUser) {
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		
		String sql = "SELECT u.nome, u.email, t.numero " + 
				"FROM userposjava u full join telefoneuser t " + 
				"		on u.id = t.usuariopessoa " +
				"WHERE u.id = " + idUser;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				BeanUserFone userFone = new BeanUserFone();
				userFone.setEmail(result.getString("email"));
				userFone.setNome(result.getString("nome"));
				userFone.setNumero(result.getString("numero"));
				
				beanUserFones.add(userFone);
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanUserFones;
	}

	public void atualizar(UserPosJava userPosJava) {
		try {
			String sql = "update userposjava set nome = ? where id = " + userPosJava.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userPosJava.getNome());

			statement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
	
	public void deletar(Long id) {
		try {
			String sql = "delete from userposjava where id = " + id;
			PreparedStatement instrucao = connection.prepareStatement(sql);
			instrucao.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deletarPhone(Long id) {
		try {
			String sqlPhone = "delete from telefoneuser where usuariopessoa = " + id;
			String sqlUser = "delete from userposjava WHERE id = " + id;
			PreparedStatement instrucao = connection.prepareStatement(sqlPhone);
			instrucao.executeUpdate();
			connection.commit();
			
			instrucao = connection.prepareStatement(sqlUser);
			instrucao.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/*
	 * public PreparedStatement statement(String sql) throws Exception {
	 * PreparedStatement statement = connection.prepareStatement(sql); return
	 * statement; }
	 */
}
