package domain;

import java.sql.Date;
import java.sql.Time;

public class Match {
	public java.sql.Date data;
	public java.sql.Time orario; 
	public int campo_id;
	public String provincia; 
	public String organizzatore;
	
	public Match(Date data, Time orario, int campo_id, String provincia, String organizzatore) {
		super();
		this.data = data;
		this.orario = orario;
		this.campo_id = campo_id;
		this.provincia = provincia;
		this.organizzatore = organizzatore;
	}

}
