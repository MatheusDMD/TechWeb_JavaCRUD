package Package;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DAO {
	private Connection connection = null;
	public DAO() {
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	    try {
	    	connection = DriverManager.getConnection(
			"jdbc:mysql://localhost/meu_database", "root", "md2302");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaPessoa(Pessoas pessoa) {
		String sql = "INSERT INTO Pessoas" +
		"(nome,nascimento,altura) values(?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1,pessoa.getNome());
			stmt.setDate(2, new Date(
				pessoa.getNascimento().getTimeInMillis()));
			stmt.setDouble(3,pessoa.getAltura());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaPassaporte(Passaportes passaporte) {
		String sql = "INSERT INTO Passaportes" +
		"(pessoa_id,pais,validade) values(?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1,passaporte.getUserId());
			stmt.setString(2,passaporte.getPais());
			stmt.setDate(3, new Date(
				passaporte.getValidade().getTimeInMillis()));
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Pessoas> getLista() {

		List<Pessoas> pessoas = new ArrayList<Pessoas>();
		
		try{
			PreparedStatement stmt = connection.
			prepareStatement("SELECT * FROM Pessoas");
			ResultSet rs = stmt.executeQuery();
	
			while (rs.next()) {
				Pessoas pessoa = new Pessoas();
				pessoa.setId(rs.getInt("id"));
				pessoa.setNome(rs.getString("nome"));
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("nascimento"));
				pessoa.setNascimento(data);
				pessoa.setAltura(rs.getDouble("altura"));
				pessoas.add(pessoa);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoas;
	}
	
	public List<Passaportes> getListaPassaportes() {

		List<Passaportes> passaportes = new ArrayList<Passaportes>();
		
		try{
			PreparedStatement stmt = connection.
			prepareStatement("SELECT * FROM Passaportes");
			ResultSet rs = stmt.executeQuery();
	
			while (rs.next()) {
				Passaportes passaporte = new Passaportes();
				passaporte.setId(rs.getInt("id"));
				passaporte.setPais(rs.getString("pais"));
				passaporte.setUserId(rs.getInt("pessoa_id"));
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("validade"));
				passaporte.setValidade(data);
				passaportes.add(passaporte);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return passaportes;
	}
	
	public void altera(Pessoas pessoa) {
		String sql = "UPDATE Pessoas SET " +
	          "nome=?, nascimento=?, altura=? WHERE id=?";
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			stmt.setString(1, pessoa.getNome());
			stmt.setDate(2, new Date(pessoa.getNascimento()
				.getTimeInMillis()));
			stmt.setDouble(3, pessoa.getAltura());
			stmt.setInt(4, pessoa.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void remove(Integer id) {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("DELETE FROM Pessoas WHERE id=?");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			stmt.setLong(1, id);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}