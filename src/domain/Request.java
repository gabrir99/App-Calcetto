package domain;

public class Request {
	public int id;
	public String ruolo, accepter, nome_impianto, nome_campo;
	public Match m;

	public Request(int id, Match m, String accepter, String ruolo, String nome_impianto, String nome_campo) {
		super();
		this.id = id;
		this.ruolo = ruolo;
		this.nome_impianto = nome_impianto;
		this.nome_campo = nome_campo;
		this.accepter = accepter;
		this.m = m;
	}

	public Request(Match m, String ruolo, String nome_impianto, String nome_campo) {
		super();
		this.m = m;
		this.ruolo = ruolo;
		this.nome_impianto = nome_impianto;
		this.nome_campo = nome_campo;
		accepter = null;
	}

	/*public Request(Match m, String ruolo, String accepter, String nome_impianto, String nome_campo) {
		super();
		this.m = m;
		this.ruolo = ruolo;
		this.accepter = accepter;
		this.nome_impianto = nome_impianto;
		this.nome_campo = nome_campo;
	}*/
	public Request() {
		super();
	}

}
