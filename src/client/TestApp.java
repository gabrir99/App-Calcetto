package client;

import javax.swing.SwingUtilities;

public class TestApp {

	public static void main(String[] args) {
		
		/*Client c = new Client();
		java.sql.Date data = new Date(System.currentTimeMillis());
		java.sql.Time orario = new Time(System.currentTimeMillis());
		Match m = new Match(data, orario, 5, "MO", "ruio99");
		try {
			c.postRequest(m, "Portiere", "Saliceta San Giuliano", "campo 5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new View();
			}
		});
	}
}
