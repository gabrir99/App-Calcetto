package domain;

public class Field {
	public int id;
	public String provincia, nome_impianto, nome_campo;
	
	public Field() {
		super();
	}
	
	public Field(int id, String provincia, String nome_impianto, String nome_campo) {
		super();
		this.id = id;
		this.provincia = provincia;
		this.nome_impianto = nome_impianto;
		this.nome_campo = nome_campo;
	}

	public Field(String provincia, String nome_impianto, String nome_campo) {
		super();
		this.provincia = provincia;
		this.nome_impianto = nome_impianto;
		this.nome_campo = nome_campo;
	}

}
