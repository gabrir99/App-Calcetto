package client;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Field;
import domain.Match;
import domain.Profile;
import domain.Request;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class Client implements Runnable {
	static Logger logger = LoggerFactory.getLogger(Client.class);
	ObjectMapper om = new ObjectMapper();

	public Profile getId(String username) {
		String url = String.format("http://localhost:8080/Profile/byusername?username=%s", username);
		String json = Unirest.get(url).asString().getBody();

		// JSON to object mapping
		Profile p = null;
		try {
			p = om.readValue(json, Profile.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}

	public void postProfile(String username, String nome, String cognome, String provincia, String ruolo1, String ruolo2, String password) throws Exception{
		HttpResponse<kong.unirest.JsonNode> js;
		try
		{
			js =  Unirest.post("http://localhost:8080/Profile/add")
					.header("accept", "application/json")
					.field("username", username)
					.field("nome", nome)
					.field("cognome", cognome)
					.field("provincia", provincia)
					.field("ruolo1", ruolo1)
					.field("ruolo2", ruolo2)
					.field("password", password)
					.asJson();
		}
		catch (JSONException je) {
			throw new Exception();
		}
		catch(Exception exc) {
			throw new Exception();
		}
		if (!js.isSuccess()){
			throw new Exception();
		}
	}

	public void updateProfile(String username, String nome, String cognome, String provincia, String ruolo1, String ruolo2, String password) {
		Unirest.put("http://localhost:8080/Profile/" + username)
		.field("nome", nome)
		.field("cognome", cognome)
		.field("provincia", provincia)
		.field("ruolo1", ruolo1)
		.field("ruolo2", ruolo2)
		.field("password", password)
		.asJson();
	}

	public void getMatch(java.sql.Date data, java.sql.Time orario,int campo_id) {
		String url = String.format("http://localhost:8080/Match/bykey?giorno=" + data + "&orario=" + orario + "&=" + campo_id);
		String json = Unirest.get(url).asString().getBody();

		Match m = null;
		try {
			m = om.readValue(json, Match.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// logging
		logger.info(json);
		logger.info(m.toString());
	}

	public void postMatch(Date data, Time orario, int campo_id, String provincia, String organizzatore) throws Exception {
		String time = orario.toString();	
		String field_id = String.valueOf(campo_id);
		
		HttpResponse<kong.unirest.JsonNode> js;
		try {
			js = Unirest.post("http://localhost:8080/Match/add")
					.header("accept", "application/jason")
					.field("data", data)
					.field("orario", time)
					.field("campo_id", field_id)
					.field("provincia", provincia)
					.field("organizzatore", organizzatore)
					.asJson();	
		} catch(JSONException jexc) {
			throw new Exception();
		}
		if(!js.isSuccess())
			throw new Exception("Partita già esistente nel database");
	}

	public ArrayList<Field> getField(String provincia) throws JsonMappingException, JsonProcessingException {
		String url = String.format("http://localhost:8080/Field/byprovincia?provincia=%s", provincia);
		String json = Unirest.get(url).asString().getBody();

		ArrayList<Field> f = null;
		try {
			f = om.readValue(json, om.getTypeFactory().constructCollectionType(ArrayList.class, Field.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	public ArrayList<String> getProvince(){
		String url = new String("http://localhost:8080/Field/province");
		String json = Unirest.get(url).asString().getBody();

		ArrayList<String> l = new ArrayList<String>();

		char[] province = new char[2];
		int j = 0; char c; 
		for(int i = 0; i < json.length(); ++i) {
			c = json.charAt(i);
			if(c == ',' || c == ']') {
				l.add(String.valueOf(province));
				j = 0;
			}
			if(Character.isAlphabetic(c)) {
				province[j] = c;
				++j;
			}
		}
		return l;
	}

	public ArrayList<Request> getRequest(){
		String url = new String("http://localhost:8080/Request");
		String json = Unirest.get(url).asString().getBody();

		ArrayList<Request> r = new ArrayList<Request>();
		try {
			r = om.readValue(json,  om.getTypeFactory().constructCollectionType(ArrayList.class, Request.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public void postRequest(Match m, String ruolo, String nome_impianto, String nome_campo) throws Exception {
		String time = m.orario.toString();
		String campo = String.valueOf(m.campo_id);
		
		HttpResponse<kong.unirest.JsonNode> js;
		try {
			js = Unirest.post("http://localhost:8080/Request/add")
					.header("accept", "application/jason")
					.field("giorno", m.data)
					.field("orario_i", time)
					.field("id_campo", campo)
					.field("provincia", m.provincia)
					.field("organizzatore", m.organizzatore)
					.field("ruolo", ruolo)
					.field("nome_impianto", nome_impianto)
					.field("nome_campo", nome_campo)
					.asJson();
		} catch(JSONException jexc) {
			jexc.printStackTrace();
			throw jexc;
		}
		
		if(!js.isSuccess()) {
			throw new Exception("Richiesta non riuscita!");
		}	
	}

	@Override
	public void run() {
	}
}
