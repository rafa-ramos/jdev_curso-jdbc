package pos_java_jdbc2.pos_java_jdbc2;

import java.util.List;

import org.junit.Test;

import conexao.jdbc.SingleConnection;
import dao.UserPosDao;
import model.BeanUserFone;
import model.Telefone;
import model.UserPosJava;

public class TesteBancoJDBC {
	
	@Test
	public void initBanco() {
		UserPosDao userPosDAO = new UserPosDao();
		UserPosJava userposjava = new UserPosJava();
		
		userposjava.setNome("Rafael Teste autoincrement2");
		userposjava.setEmail("rafael4@teste.com.br");
		
		userPosDAO.salvar(userposjava);
	}
	
	@Test
	public void initListar() {
		UserPosDao dao = new UserPosDao();
		try {
			List<UserPosJava> list = dao.listar();
			
			for (UserPosJava userPosJava : list) {
				System.out.println(userPosJava);
				System.out.println("-----------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initGetById() {
		try {
			UserPosDao dao = new UserPosDao();
			UserPosJava userposjava = dao.getById(4L);
			
			System.out.println(userposjava);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Test
	public void initAtualizar() {		
		try {
			UserPosDao dao = new UserPosDao();
			UserPosJava userposjava = dao.getById(4L);
			userposjava.setNome("Nome alterado");
			
			dao.atualizar(userposjava);
			
			System.out.println(userposjava);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void initDeletar() {
		try {
			UserPosDao dao = new UserPosDao();
			dao.deletar(8L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeInsertTelefone() {
		try {
			Telefone telefone = new Telefone();
			telefone.setNumero("999173035");
			telefone.setTipo("celular");
			telefone.setUsuario(11L);
			
			UserPosDao dao = new UserPosDao();
			dao.salvarTelefone(telefone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeCarregaFones () {
		UserPosDao dao = new UserPosDao();
		
		List<BeanUserFone> beanUserFones = dao.listaUserFone(4L);
		
		for (BeanUserFone beanUser : beanUserFones) {
			System.out.println(beanUser);
			System.out.println("-----------------");
		}
	}
	
	@Test
	public void deltePhoneUser() {
		try {
			UserPosDao dao = new UserPosDao();
			dao.deletarPhone(4L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
