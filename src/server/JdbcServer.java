package server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import database.DBManager;
import domain.Match;
import domain.Profile;
import domain.Field;
import domain.Request;
import spark.Spark;

public class JdbcServer {
	static Logger logger = LoggerFactory.getLogger(JdbcServer.class);
	ObjectMapper om = new ObjectMapper();
	DBManager db;

	public void dbConnection() {
		try {
			//db = new DBManager(DBManager.JDBCdriverSQLServer, DBManager.JDBCurlSQLServer);
			db = new DBManager();
			db.ConnectDB();

			db.executeQuery("SELECT * FROM profilo");
		}
		catch(ClassNotFoundException e) {
			System.out.println("jdbc driver not found"); 
			throw new RuntimeException();
		}
		catch (SQLException e) {
			try {
				db.executeUpdate("DROP TABLE IF EXISTS profilo");
				db.executeUpdate("DROP TABLE IF EXISTS partita");
				db.executeQuery("create table profilo (\r\n" + 
						"username varchar(50) primary key, \r\n" + 
						"nome varchar(20) not null, \r\n" + 
						"cognome varchar(20) not null, \r\n" + 
						"provincia varchar(15) not null,\r\n" + 
						"ruolo1 varchar(20) not null, \r\n" + 
						"ruolo2 varchar(20)\r\n" + 
						"password varchar(20)\r\n" +
						")\r\n" + 
						"");
				
				db.executeQuery("create table partita (\r\n" + 
						"giorno date,\r\n" + 
						"orario_Inizio time, \r\n" + 
						"campo_id int references campo(id),\r\n" + 
						"provincia varchar(15) not null,\r\n" + 
						"organizzatore varchar(50) references profilo, \r\n" + 
						"primary key(giorno, orario_inizio, campo_id)\r\n" + 
						")");
				db.executeQuery("create table campo (\r\n" + 
						"id int unique not null identity(1, 1),\r\n" + 
						"provincia varchar(50),\r\n" + 
						"nome_impianto varchar(50), \r\n" + 
						"nome_campo varchar(50),\r\n" + 
						"primary key(provincia, nome_impianto, nome_campo)\r\n" + 
						")");
				db.executeQuery("create table richiesta(\r\n" + 
						"id_r int identity(1, 1), \r\n" + 
						"id_campo int references campo(id), \r\n" + 
						"giorno date, \r\n" + 
						"orario_i time(7), \r\n" + 
						"provincia varchar(50),\r\n" + 
						"nome_impianto varchar(50),\r\n" +
						"nome_campo varchar(50), \r\n" +
						"organizzatore varchar(50), \r\n" + 
						"ruolo varchar(20), \r\n" + 
						"accepter varchar(20), \r\n" + 
						"primary key(id_r, giorno, orario_i, id_campo)\r\n" + 
						")");
			} 
			catch (SQLException e1) {
				throw new RuntimeException();			
			}
		}
	}

	public void run() throws Exception{
		dbConnection();

		Spark.port(8080);

		// Configure resources

		/**
		 * Requests for Profile
		 */
		// POST - Add new
		post("/Profile/add", (request, response) -> {

			String username = (request.queryParams("username"));
			String nome = (request.queryParams("nome"));
			String cognome = (request.queryParams("cognome"));
			String provincia = (request.queryParams("provincia"));
			String ruolo1 = (request.queryParams("ruolo1")); 
			String ruolo2 = (request.queryParams("ruolo2"));
			String pass = request.queryParams("password");

			Profile p = new Profile(username, nome, cognome, provincia, ruolo1, ruolo2, pass);
			String query = String.format(
					"INSERT INTO profilo (username, nome, cognome, provincia, ruolo1, ruolo2, password) VALUES ('%s', '%s', '%s', '%s', '%s', '%s' , '%s')", username,
					nome, cognome, provincia, ruolo1, ruolo2, pass);
			try {
				db.executeUpdate(query);

				response.status(201);
				return om.writeValueAsString(p);
			}
			catch(Exception e)
			{
				throw new Exception();
			}
		});

		//PUT - Update
		put("/Profile/:username", (request, response) -> {
			String username = (request.params("username"));
			String nome = (request.queryParams("nome"));
			String cognome = (request.queryParams("cognome"));
			String provincia = (request.queryParams("provincia"));
			String ruolo1 = (request.queryParams("ruolo1")); 
			String ruolo2 = (request.queryParams("ruolo2"));
			String query;

			query = String.format("SELECT * FROM profilo WHERE username = '%s'", username);
			try
			{
				ResultSet rs = db.executeQuery(query);
				if (rs.next() == false) {
					response.status(404);
					return om.writeValueAsString("{status: failed}");
				}
			}
			catch (SQLException e) {
				throw new Exception();
			}
			Profile p = new Profile(username, nome, cognome, provincia, ruolo1, ruolo2);
			query = String.format("UPDATE profilo SET nome='%s', cognome='%s', provincia='%s', ruolo1='%s', ruolo2='%s' WHERE username = '%s'",
					nome, cognome, provincia, ruolo1, ruolo2, username);
			db.executeUpdate(query);
			return om.writeValueAsString(p);
		});

		//GET - get all
		get("/Profile", (request, response) -> {
			ArrayList<Profile> p = null;
			try 
			{
				String query; 
				query = String.format("SELECT * FROM profilo");
				ResultSet rs = db.executeQuery(query);

				p = new ArrayList<Profile>();
				while(rs.next()) {
					p.add(new Profile(rs.getString("username"), rs.getString("nome"), 
							rs.getString("cognome"), rs.getString("provincia"), rs.getString("ruolo1"), rs.getString("ruolo2")));
				}
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
			return om.writeValueAsString(p);
		});

		//GET - get by user name
		get("/Profile/byusername", (request, response) -> {
			String query, username; 
			username = request.queryParams("username");

			query = String.format("SELECT * FROM profilo WHERE username = '%s'", username);
			ResultSet rs = db.executeQuery(query);

			if(rs.next() == false) {
				response.status(404);
				return om.writeValueAsString("{status: failed}");	
			}
			Profile p = new Profile(rs.getString("username"), rs.getString("nome"), 
					rs.getString("cognome"), rs.getString("provincia"), rs.getString("ruolo1"), rs.getString("ruolo2"), rs.getString("password"));
			return om.writeValueAsString(p);
		});

		//GET - get by ruolo1
		get("/Profile/byruolo1", (request, response) -> {
			String query, ruolo1; 
			ruolo1 = request.queryParams("ruolo1");

			query = String.format("SELECT * FROM profilo WHERE ruolo1 = '%s'", ruolo1);
			ResultSet rs = db.executeQuery(query);
			ArrayList<Profile> p = new ArrayList<Profile>();
			while(rs.next()) {
				p.add(new Profile(rs.getString("username"), rs.getString("nome"), 
						rs.getString("cognome"), rs.getString("provincia"), rs.getString("ruolo1"), rs.getString("ruolo2")));
			}
			return om.writeValueAsString(p);
		});

		//GET - get by ruolo2
		get("/Profile/byruolo2", (request, response) -> {
			String query, ruolo2; 
			ruolo2 = request.queryParams("ruolo2");

			query = String.format("SELECT * FROM profilo WHERE ruolo2 = '%s'", ruolo2);
			ResultSet rs = db.executeQuery(query);
			ArrayList<Profile> p = new ArrayList<Profile>();
			while(rs.next()) {
				p.add(new Profile(rs.getString("username"), rs.getString("nome"), 
						rs.getString("cognome"), rs.getString("provincia"), rs.getString("ruolo1"), rs.getString("ruolo2")));
			}
			return om.writeValueAsString(p);
		});

		/**
		 * Requests for Match
		 */
		//POST - Add new 
		post("/Match/add", (request, response) -> {
			java.sql.Date data = java.sql.Date.valueOf(request.queryParams("data"));
			java.sql.Time orario = java.sql.Time.valueOf(request.queryParams("orario"));
			int campo_id = Integer.valueOf(request.queryParams("campo_id"));
			String provincia = request.queryParams("provincia"); 
			String organizzatore = request.queryParams("organizzatore");

			Match m = new Match(data, orario, campo_id, provincia, organizzatore);
			String query = new String("insert into partita (giorno, orario_Inizio, campo_id, provincia, organizzatore) values"
					+ "('" + data + "', '" + orario + "', '" + campo_id + "', '" + provincia + "', '" + organizzatore + "')");
			db.executeUpdate(query);
			return om.writeValueAsString(m);
		});

		//GET- get all
		get("/Match", (request, response) -> {
			ArrayList<Match> m = null;
			String query = new String("SELECT * FROM partita");
			ResultSet rs = db.executeQuery(query);
			m = new ArrayList<Match>();
			while(rs.next()) {
				m.add(new Match(rs.getDate("giorno"), rs.getTime("orario_Inizio"), rs.getInt("campo_id"), 
						rs.getString("provincia"), rs.getString("organizzatore")));
			}
			return om.writeValueAsString(m);			
		});
		//GET - by key 
		get("/Match/bykey", (request, response) -> {
			java.sql.Date data = java.sql.Date.valueOf(request.queryParams("data"));
			java.sql.Time orario = java.sql.Time.valueOf(request.queryParams("orario"));
			int campo_id = Integer.valueOf(request.queryParams("campo_id"));
			String query = new String("SELECT * FROM partita where data = " + data + "and orario = " + orario +
					"and campo_id = " + campo_id);
			ResultSet rs = db.executeQuery(query);

			Match m = new Match(data, orario, campo_id, rs.getString("provincia"), rs.getString("organizzatore"));
			return om.writeValueAsString(m);
		});

		/**
		 * request for FIELD
		 */
		//GET- get all
		get("/Field", (request, response) -> {
			ArrayList<Field> f = new ArrayList<Field>();
			String query = new String("Select * from campo");
			ResultSet rs = db.executeQuery(query);

			while(rs.next()) {
				f.add(new Field(rs.getInt("id"), rs.getString("provincia"), rs.getString("nome_impianto"), rs.getString("nome_campo")));
			}
			String s = null;
			try {
			s = om.writeValueAsString(f);
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
			return s;
		});
		
		//GET -get by provincia 
		get("/Field/byprovincia", (request, response) -> {
			ArrayList<Field> f = new ArrayList<Field>();
			String provincia = request.queryParams("provincia");
			String query = String.format("Select * from campo where provincia = '%s'" , provincia);
			ResultSet rs = db.executeQuery(query);

			while(rs.next()) {
				f.add(new Field(rs.getInt("id"), provincia, rs.getString("nome_impianto"), rs.getString("nome_campo")));				
			}
			
			return om.writeValueAsString(f);	
		});

		get("/Field/province", (request, response) -> {
			ArrayList<String> province = new ArrayList<String>();
			String query = new String("select distinct provincia from campo");
			ResultSet rs = db.executeQuery(query);
			while(rs.next()) {
				province.add(new String(rs.getString("provincia")));
			}
			
			return province;
		});
		
		/**
		 * request for Request 
		 * 
		 */
		get("/Request", (request, response) -> {
			ArrayList<Request> r = new ArrayList<Request>();
			String query = new String("SELECT * FROM RICHIESTA");
			ResultSet rs = db.executeQuery(query);
			while(rs.next()) {
				r.add(new Request(rs.getInt("id_r"), new Match(rs.getDate("giorno"), rs.getTime("orario_i"), rs.getInt("id_campo"), rs.getString("provincia"), 
						rs.getString("organizzatore")), rs.getString("ruolo"), rs.getString("nome_impianto"), rs.getString("nome_campo")));
			}
			return om.writeValueAsString(r);
		});
		
		post("/Request/add", (request, response) -> {
			java.sql.Date giorno = java.sql.Date.valueOf(request.queryParams("giorno"));
			java.sql.Time orario_i = java.sql.Time.valueOf(request.queryParams("orario_i"));
			
			int id_campo = Integer.valueOf(request.queryParams("id_campo"));
			String provincia = request.queryParams("provincia"); 
			String organizzatore = request.queryParams("organizzatore");
			String ruolo = request.queryParams("ruolo");
			String nome_impianto = request.queryParams("nome_impianto");
			String nome_campo = request.queryParams("nome_campo");
			Request r = new Request(new Match(giorno, orario_i, id_campo, provincia, organizzatore), ruolo, nome_impianto, nome_campo);
			
			String query = new String("insert into richiesta (id_campo, giorno, orario_i, provincia, nome_impianto, organizzatore, ruolo, nome_campo) \r\n" + 
					"	values(" + id_campo + ", '" + giorno + "', '"+ orario_i + "', '" + provincia + "', '" + nome_impianto +
					"', '" + organizzatore + "', '" + ruolo + "', '" + nome_campo + "' ) ");
			
			db.executeUpdate(query);
			
			return om.writeValueAsString(r);
		});
		
		put("/Request/:id_r", (request, response) ->	{
			int id_r = Integer.valueOf(request.params("id_r"));
			int id_campo = Integer.valueOf(request.params("id_campo"));
			java.sql.Date giorno = java.sql.Date.valueOf(request.queryParams("giorno"));
			java.sql.Time orario_i = java.sql.Time.valueOf(request.queryParams("orario_i"));
			String accepter = request.queryParams("accepter");
			String query;
			
			query = String.format("SELECT * FROM richiesta WHERE id_r = %d", id_r);
			
			try
			{
				ResultSet rs = db.executeQuery(query);
				if (rs.next() == false) {
					response.status(404);
					return om.writeValueAsString("{status: failed}");
				}
			}
			catch (SQLException e) {
				throw new Exception();
			}
			
			Request r = new Request();
			
			query = ("update richiesta set accepter = '" + accepter + "' WHERE id_r=" + id_r + " and id_campo=" + id_campo + "and giorno='" + giorno + "' and orario_i=' " + orario_i + "'");
			db.executeUpdate(query);
			return om.writeValueAsString(r);
				});
	
	}
	
	public static void main(String[] args) throws Exception {
		new JdbcServer().run();
	}

}
