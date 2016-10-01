package Package;

import java.util.Calendar;

public class Passaportes {
	
	private Integer id;
	private Integer userId;
	private String pais;
	private Calendar validade;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Calendar getValidade() {
		return validade;
	}
	public void setValidade(Calendar dataValidade) {
		this.validade = dataValidade;
	}
	
}
