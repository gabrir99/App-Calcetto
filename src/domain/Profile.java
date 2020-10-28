package domain;

public class Profile {
	public String username, nome, cognome, provincia, ruolo1, ruolo2; 
	public String password = null;

	public Profile(String username, String nome, String cognome, String provincia, String ruolo1, String ruolo2) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.provincia = provincia;
		this.ruolo1 = ruolo1;
		this.ruolo2 = ruolo2;
		this.username = username;
	}

	public Profile(String username, String nome, String cognome, String provincia, String ruolo1, String ruolo2,
			String password) {
		super();
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.provincia = provincia;
		this.ruolo1 = ruolo1;
		this.ruolo2 = ruolo2;
		this.password = password;
	}

	public Profile(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		nome = null;
		cognome = null; 
		provincia = null; 
		ruolo1 = null; 
		ruolo2 = null;
	}

	public Profile() {
		super();
	}

}
