package client;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import domain.Field;
import domain.Profile;
import domain.Request;
import domain.Match;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class View extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	Client client;
	JMenuItem logout ;

	JTextField username; 
	JPasswordField pw;
	JButton login, signup;

	JButton profile, messages, create; 

	JComboBox<String> provincia;
	JTextField nome, cognome;
	JButton save, saveB; 

	JDatePickerImpl data;
	JComboBox<LocalTime> orario;
	JComboBox<String> provinces;
	JComboBox<String> impianto;
	JComboBox<String> campo;
	JComboBox<String> rules, rules1, rules2, rules1B, rules2B;
	JButton enter;
	JPanel p0, p1, p2, p3, p4, p5, p6, p7, p8;

	JButton gestisci, partecipa;
	DefaultTableModel tablemodel, tablemodel2;//, tablemodel3;
	JTable t1, t2, t3;
	JButton gioca, elimina;

	JTextField usernameB, nomeB, cognomeB, pwB;
	JComboBox<String> provinciaB;

	GridBagConstraints c;

	Profile p = null;
	ArrayList<Field> f = null;

	public View() {
		client = new Client();

		Dimension d = new Dimension(100, 15);
		//p0 AUTENTICATION
		p0 = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();

		username = new JTextField();
		username.setMinimumSize(d);
		username.setMaximumSize(d);
		pw = new JPasswordField();
		login = new JButton("LOG IN");
		login.addActionListener(this);
		signup = new JButton("SIGN UP");
		signup.addActionListener(this);

		c.gridx = 0; c.gridy = 0; 
		c.ipadx = 110; c.ipady = 10;
		p0.add(new JLabel("username"), c);
		c.gridx = 1; 
		p0.add(username, c);
		c.gridx = 0; c.gridy = 1; 
		p0.add(new JLabel("password"), c);
		c.gridx = 1;
		p0.add(pw, c);
		c.gridx = 0; c.gridy = 2;
		c.ipadx = 90; c.ipady = 5;
		p0.add(login, c);

		c.gridy = 10;
		p0.add(new JLabel("Nuovo utente?"), c);
		c.gridy = 11;
		p0.add(signup, c);

		//p1 FIRST PANEL 
		//INITIAL MENU
		p1 = new JPanel(new GridLayout(3, 1));
		profile = new JButton("PROFILO");
		messages = new JButton("MESSAGGI");
		create = new JButton("CREA PARTITA");

		profile.addActionListener(this);
		messages.addActionListener(this);
		create.addActionListener(this);

		p1.add(profile);
		p1.add(messages);
		p1.add(create); 

		//p2 PROFILE
		p2 = new JPanel(new GridBagLayout());
		String[] ruoli = new String[4];
		ruoli[0] = "Portiere";
		ruoli[1] = "Difensore";
		ruoli[2] = "Centrocampista";
		ruoli[3] = "Attaccante";
		rules1 = new JComboBox<String>(ruoli);
		rules2 = new JComboBox<String>(ruoli); 

		nome = new JTextField();
		nome.setMaximumSize(d);
		nome.setMinimumSize(d);

		cognome = new JTextField();
		cognome.setMaximumSize(d);
		cognome.setMinimumSize(d);

		String[] italianPr = {"AG", "AL", "AN", "AO", "AQ", "AR", "AP", "AT", "AV", "BA", "BT", "BL", "BN", 
				"BG", "BI", "BO", "BZ", "BS", "BR", "CA", "CL", "CB", "CI", "CE", "CT", "CZ", "CH", "CO", "CS",
				"CR", "KR", "CN", "EN", "FM", "FE", "FI", "FG", "FC", "FR", "GE", "GO", "GR", "IM", "IS", "SP", 
				"LT", "LE", "LC", "LI", "LO", "LU", "MC", "MN", "MS", "MT", "VS", "ME", "MI", "MO", "MB", 
				"NA", "NO", "NU", "OG", "OT", "OR", "PD", "PA", "PR", "PV", "PG", "PU", "PE", "PC", "PI", "PT", 
				"PN", "PZ", "PO", "RG", "RA", "RC", "RE", "RI", "RN", "RM", "RO", "SA", "SS", "SV", "SI", "SR", 
				"SO", "TA", "TE", "TR", "TO", "TP", "TN", "TV", "TS", "UD", "VA", "VE", "VB", "VC", "VR", "VV", "VI", "VT" };
		provincia = new JComboBox<String>(italianPr);
		provincia.setEditable(false);

		save = new JButton("SALVA");
		save.addActionListener(this);

		c.gridx = 0; c.gridy = 0;
		c.ipadx = 110; c.ipady = 10; 
		p2.add(new JLabel("Nome"), c);
		c.gridx = 2; 
		p2.add(nome, c); 

		c.gridy = 1; c.gridx = 0;
		p2.add(new JLabel("Cognome"), c);
		c.gridx = 2; 
		p2.add(cognome, c);

		c.gridy = 2; c.gridx = 0;
		p2.add(new JLabel("Provincia"), c);
		c.gridx = 2; 
		p2.add(provincia, c);

		c.gridy = 3; c.gridx = 0;
		p2.add(new JLabel("Primo ruolo"), c);
		c.gridx = 2; 
		p2.add(rules1 , c);

		c.gridy = 4; c.gridx = 0;
		p2.add(new JLabel("Secondo ruolo"), c);
		c.gridx = 2; 
		p2.add(rules2, c);

		c.gridy = 5; c.gridx = 0;
		p2.add(save, c);

		//p3 MATCH
		p3 = new JPanel(new GridBagLayout());

		data = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel()));

		LocalTime[] hours = new LocalTime[27];
		int j = 0;
		for(int i = 0; i < 27; i++ ) {
			if(i%2 == 0)
				j++;
			hours[i] = LocalTime.of(j + 9, (i%2)*30);
		}

		orario = new JComboBox<LocalTime>(hours);

		ArrayList<String> provProvvisorio = client.getProvince(); 

		String[] prov = provProvvisorio.toArray(new String[provProvvisorio.size()]);

		provinces = new JComboBox<String>(prov);
		provinces.addActionListener(this);

		impianto = new JComboBox<String>();
		impianto.addActionListener(this);
		campo = new JComboBox<String>();

		rules = new JComboBox<String>(ruoli);
		enter = new JButton("Invia richiesta");
		enter.addActionListener(this);


		c.gridx = 0; c.gridy = 0;
		c.ipadx = 110; c.ipady = 10; 
		p3.add(new JLabel("Data"), c);
		c.gridx = 2; 
		p3.add(data, c); 

		c.gridy = 1; c.gridx = 0;
		c.ipady = 5;
		p3.add(new JLabel("Orario"), c);
		c.gridx = 2;
		p3.add(orario, c);

		c.gridy = 3; c.gridx = 0;
		p3.add(new JLabel("Provincia"), c);
		c.gridx = 2;
		p3.add(provinces, c);

		c.gridy = 4; c.gridx = 0;
		p3.add(new JLabel("Impianto"), c);
		c.gridx = 2; 
		p3.add(impianto, c);

		c.gridy = 5; c.gridx = 0;
		p3.add(new JLabel("Campo"), c);
		c.gridx = 2; 
		p3.add(campo, c);

		c.gridy = 6; c.gridx = 0;
		p3.add(new JLabel("Che giocatore ti serve?"), c);
		c.gridx = 2; 
		p3.add(rules, c);

		c.gridy = 7;c.gridx = 0;
		p3.add(enter, c);

		//p4 NEW PROFILE
		p4 = new JPanel(new GridBagLayout());

		rules1B = new JComboBox<String>(ruoli);
		rules2B = new JComboBox<String>(ruoli); 

		nomeB = new JTextField();

		cognomeB = new JTextField();

		provinciaB = new JComboBox<String>(italianPr);

		usernameB = new JTextField();
		pwB = new JTextField();

		saveB = new JButton("SALVA");
		saveB.addActionListener(this);

		c.gridx = 0; c.gridy = 0;
		c.ipadx = 110; c.ipady = 10; 
		p4.add(new JLabel("Nome"), c);
		c.gridx = 2; 
		p4.add(nomeB, c); 

		c.gridy = 1; c.gridx = 0;
		p4.add(new JLabel("Cognome"), c);
		c.gridx = 2; 
		p4.add(cognomeB, c);

		c.gridy = 2; c.gridx = 0;
		p4.add(new JLabel("Provincia"), c);
		c.gridx = 2; 
		p4.add(provinciaB, c);

		c.gridy = 3; c.gridx = 0;
		p4.add(new JLabel("Primo ruolo"), c);
		c.gridx = 2; 
		p4.add(rules1B , c);

		c.gridy = 4; c.gridx = 0;
		p4.add(new JLabel("Secondo ruolo"), c);
		c.gridx = 2; 
		p4.add(rules2B, c);

		c.gridy= 5; c.gridx = 0;
		p4.add(new JLabel("Username"), c);
		c.gridx = 2; 
		p4.add(usernameB, c);

		c.gridy = 6; c.gridx = 0;
		p4.add(new JLabel("Password"), c);
		c.gridx = 2; 
		p4.add(pwB, c);

		c.gridy = 7; c.gridx = 0;
		p4.add(saveB, c);

		// P5 SELECTION
		p5 = new JPanel(new GridLayout(2,1));
		gestisci = new JButton("GESTISCI!");
		partecipa = new JButton("PARTECIPA!");

		gestisci.addActionListener(this);
		partecipa.addActionListener(this);

		p5.add(gestisci);
		p5.add(partecipa);

		//P6 MATCHES CREATED
		p6 = new JPanel(new GridLayout());

		tablemodel= new DefaultTableModel();
		t1 = new JTable(tablemodel);


		tablemodel.addColumn("PROVINCIA");
		tablemodel.addColumn("STRUTTURA");
		tablemodel.addColumn("CAMPO");
		tablemodel.addColumn("DATA");
		tablemodel.addColumn("ORA");
		tablemodel.addColumn("RUOLO");
		tablemodel.addColumn("GIOCATORE");

		t1.getTableHeader().setReorderingAllowed(false);
		t1.setEnabled(false);
	
		p6.add(new JScrollPane(t1));

		//P7 MATCHES TO PARTECIPATE
		p7 = new JPanel(new GridLayout());
		tablemodel2 = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return column == 6;
			};
		};

		t2 = new JTable (tablemodel2);
		tablemodel2.addColumn("STRUTTURA");
		tablemodel2.addColumn("CAMPO");
		tablemodel2.addColumn("DATA");
		tablemodel2.addColumn("ORA");
		tablemodel2.addColumn("RUOLO RICHIESTO");
		tablemodel2.addColumn("ORGANIZZATORE");
		tablemodel2.addColumn("GIOCHI?");

		t2.getTableHeader().setReorderingAllowed(false);

		t2.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
	
		p7.add(new JScrollPane(t2));

		
		//JMENU

		JMenuItem back = new JMenuItem("BACK");
		logout = new JMenuItem("log out");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(p0.isVisible()) {
					return;
				}
				else if(p4.isVisible()){
					p0.setVisible(true);
					p1.setVisible(false);
					p2.setVisible(false);
					p3.setVisible(false);
					p4.setVisible(false);
					p5.setVisible(false);
					p6.setVisible(false);
					p7.setVisible(false);
					setContentPane(p0);
				}
				else if(p2.isVisible() || p3.isVisible() || p5.isVisible()) { 
					p0.setVisible(false);
					p1.setVisible(true);
					p2.setVisible(false);
					p3.setVisible(false);
					p4.setVisible(false);
					p5.setVisible(false);
					p6.setVisible(false);
					p7.setVisible(false);
					setContentPane(p1);
				}
				else if(p6.isVisible() || p7.isVisible()) { 
					tablemodel.setRowCount(0);
					tablemodel2.setRowCount(0);
					p0.setVisible(false);
					p1.setVisible(false);
					p2.setVisible(false);
					p3.setVisible(false);
					p4.setVisible(false);
					p5.setVisible(true);
					p6.setVisible(false);
					p7.setVisible(false);
					setContentPane(p5);
				}

			}

		});
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(p6.isVisible() || p7.isVisible()) {
					tablemodel.setRowCount(0);
					tablemodel2.setRowCount(0);
				}
				p = null;
				pw.setText("");
				p0.setVisible(true);
				p1.setVisible(false);
				p2.setVisible(false);
				p3.setVisible(true);
				p4.setVisible(false);
				p5.setVisible(false);
				p6.setVisible(false);
				p7.setVisible(false);
				setContentPane(p0);	
			}
		});

		JMenuBar menu = new JMenuBar();
		menu.add(back);
		menu.add(logout);

		setContentPane(p0);
		setJMenuBar(menu);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(700, 430);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login) {
			//check
			String us = username.getText();
			try{
				p = client.getId(us);
			}
			catch(Exception exc) {
				System.out.println(exc.toString());
			}
			if (p == null) {
				JOptionPane.showMessageDialog(null, "Username o password sbagliato");
				return;
			}
			String pass = new String(pw.getPassword());
			if(p.password.equals(pass)) {
				logout.setVisible(true);
				p0.setVisible(false);
				p1.setVisible(true);
				setContentPane(p1);
				p2.setVisible(false);
				p3.setVisible(false);
				p4.setVisible(false);
				p5.setVisible(false);
				p6.setVisible(false);
				p7.setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "Username o password sbagliato");
			}
		}
		if(e.getSource() == signup) {

			p0.setVisible(false);
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(false);
			p4.setVisible(true);
			p5.setVisible(false);
			p6.setVisible(false);
			p7.setVisible(false);

			setContentPane(p4);

		}
		if(e.getSource() == profile) {
			nome.setEditable(false);
			cognome.setEditable(false);

			nome.setText(p.nome);
			cognome.setText(p.cognome);
			provincia.setSelectedItem(p.provincia);
			rules1.setSelectedItem(p.ruolo1);
			rules2.setSelectedItem(p.ruolo2);

			p0.setVisible(false);
			p1.setVisible(false);
			p2.setVisible(true);
			p3.setVisible(false);
			p4.setVisible(false);
			p5.setVisible(false);
			p6.setVisible(false);
			p7.setVisible(false);

			setContentPane(p2);
		}
		if(e.getSource() == save) {
			p.provincia = provincia.getSelectedItem().toString();
			p.ruolo1 = rules1.getSelectedItem().toString();
			p.ruolo2 = rules2.getSelectedItem().toString();
			client.updateProfile(p.username, p.nome, p.cognome, p.provincia, p.ruolo1, p.ruolo2);
		}
		if(e.getSource() == create) {
			provincia.setEditable(true);
			p0.setVisible(false);
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(true);
			p4.setVisible(false);
			p5.setVisible(false);
			p6.setVisible(false);
			p7.setVisible(false);

			setContentPane(p3);
		}
		if(e.getSource() == provinces) {
			f = null;
			try {
				f = client.getField(provinces.getSelectedItem().toString());
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}

			Vector<String> strutture = new Vector<String>();
			for(int i = 0; i < f.size(); ++i) {
				strutture.add(f.get(i).nome_impianto);
			}

			LinkedHashSet<String> struttureTmp = new LinkedHashSet<String>(strutture);

			strutture.removeAllElements();
			strutture = new Vector<String>(struttureTmp);
			impianto.setModel(new DefaultComboBoxModel<String>(strutture));
		}
		if(e.getSource() == impianto) {
			Vector<String> campi = new Vector<String>();
			for(int i = 0; i < f.size(); ++i) {
				if(f.get(i).nome_impianto.equals(impianto.getSelectedItem().toString()))
					campi.add(f.get(i).nome_campo);
			}

			campo.setModel(new DefaultComboBoxModel<String>(campi));

		}
		if(e.getSource() == enter) {
			java.util.Date dTmp = (java.util.Date) data.getModel().getValue();
			java.sql.Date d1 = null;
			try{
				d1 = new java.sql.Date(dTmp.getTime());
			}
			catch(Exception exc) {				
				JOptionPane.showMessageDialog(null, "Assicurati che tutti i campi siano pieni");
				exc.printStackTrace();
				return;
			}
			String sass = orario.getSelectedItem().toString().concat(":00");
			Time o1 = Time.valueOf(sass);

			String imp1 = impianto.getSelectedItem().toString();
			String camp1 = campo.getSelectedItem().toString();
			String p1 = provinces.getSelectedItem().toString();
			String org1 = p.username;
			int id_campo = 0;
			if(imp1.isEmpty() || camp1.isEmpty() || p1.isEmpty() || org1.isEmpty())
				JOptionPane.showMessageDialog(null, "Assicurati che tutti i campi siano pieni");
			else {
				for(int i = 0; i < f.size(); ++i) {
					if(f.get(i).nome_impianto.equals(imp1) && 
							f.get(i).nome_campo.equals(camp1)) {
						id_campo = f.get(i).id;
						break;
					}
				}
				try {
					client.postMatch(d1, o1, id_campo, p1, org1);
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
				String r1 = rules.getSelectedItem().toString();
				String strutt = impianto.getSelectedItem().toString();
				try {
					client.postRequest(new Match(d1, o1, id_campo, p1, org1), r1, strutt, camp1);
				}
				catch (Exception exc1) {
					System.out.println(exc1.toString());
					JOptionPane.showMessageDialog(null, "Errore in creazione della richiesta");
					return;
				}
				JOptionPane.showMessageDialog(null, "Richiesta invitata correttamente!");
			}
		}
		if(e.getSource() == saveB) {
			String n, c, p, r1, r2, u, pas;
			n = ToMaiusc.conv(nomeB.getText());
			c = ToMaiusc.conv(cognomeB.getText());
			p = provinciaB.getSelectedItem().toString();
			r1 = (String) rules1B.getSelectedItem();
			r2 = (String) rules2B.getSelectedItem();
			u = usernameB.getText();
			pas = pwB.getText();
			if(n.isEmpty() || c.isEmpty() || p.isEmpty() || u.isEmpty() || pas.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Assicurati che tutti i campi siano pieni");
			}
			else {
				try {
					client.postProfile(u, n, c, p, r1, r2, pas);
				}
				catch(Exception exc) {
					JOptionPane.showMessageDialog(null, "Username già esistente");
					exc.printStackTrace();
					return;
				}
				p = null;
				pwB.setText("");
				p0.setVisible(true);
				p1.setVisible(false);
				p2.setVisible(false);
				p3.setVisible(true);
				p4.setVisible(false);
				p5.setVisible(false);
				p6.setVisible(false);
				p7.setVisible(false);
				setContentPane(p0);
			}			
		}
		if (e.getSource() == messages)
		{
			p0.setVisible(false);
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(false);
			p4.setVisible(false);
			p5.setVisible(true);
			p6.setVisible(false);
			p7.setVisible(false);
			setContentPane(p5);
		}
		if (e.getSource() == gestisci)
		{					
			ArrayList<Request> m1 = client.getRequest();
			String nomeCampo = null;
			String nomeImpianto = null;

			for (int i=0; i < m1.size(); i++)
			{
				long millis=System.currentTimeMillis();
				java.sql.Date date=new java.sql.Date(millis);
				if (m1.get(i).m.data.after(date)){
					if (m1.get(i).m.organizzatore.equals(p.username))
					{
						ArrayList<Field> fields = null;
						try {
							fields = client.getField(m1.get(i).m.provincia);
						} catch (JsonMappingException e1) {
							e1.printStackTrace();
						} catch (JsonProcessingException e1) {
							e1.printStackTrace();
						}

						for (int j=0; j < fields.size(); j++){

							if (m1.get(i).m.campo_id == fields.get(j).id)
							{
								nomeCampo = new String(fields.get(j).nome_campo);
								nomeImpianto = new String(fields.get(j).nome_impianto);
							}
						}
						if (m1.get(i).accepter == null || m1.get(i).accepter.isEmpty()) {
							tablemodel.addRow(new Object[]{
									m1.get(i).m.provincia,
									nomeImpianto,
									nomeCampo,
									m1.get(i).m.data,
									m1.get(i).m.orario,
									m1.get(i).ruolo,
									"Ancora nessuno"
							});
						}
						else {
							tablemodel.addRow(new Object[]{
									m1.get(i).m.provincia,
									nomeImpianto,
									nomeCampo,
									m1.get(i).m.data,
									m1.get(i).m.orario, 
									m1.get(i).ruolo,
									m1.get(i).accepter
							});
						}
					}
				}
			}

			if (tablemodel.getRowCount() == 0)
				JOptionPane.showMessageDialog(null, "Alzati dal divano e crea almeno una partita!");


			p0.setVisible(false);
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(false);
			p4.setVisible(false);
			p5.setVisible(false);
			p6.setVisible(true);

			setContentPane(p6);
			p7.setVisible(false);
			//p8.setVisible(false);
		}

		if (e.getSource() == partecipa)
		{
			ArrayList<Request> r1 = client.getRequest();
			ArrayList<Request> goodR = new ArrayList<Request>();
			long millis;
			for(int i = 0; i < r1.size(); i++)
			{
				millis = System.currentTimeMillis();
				java.sql.Date date=new java.sql.Date(millis);
				if (r1.get(i).m.data.after(date)){
					if (r1.get(i).m.provincia.equals(p.provincia) && ((r1.get(i).ruolo.equals(p.ruolo1) || r1.get(i).ruolo.equals(p.ruolo2)))
							&& !r1.get(i).m.organizzatore.equals(p.username))
					{
						goodR.add(r1.get(i));
						t2.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), this.p, goodR));;
						if(r1.get(i).accepter == null || r1.get(i).accepter.isEmpty()) {
									
							tablemodel2.addRow(new Object[] {
									r1.get(i).nome_impianto,
									r1.get(i).nome_campo,
									r1.get(i).m.data,
									r1.get(i).m.orario,
									r1.get(i).ruolo,
									r1.get(i).m.organizzatore,
									"PARTECIPA"
							});
							
						}
						else if(r1.get(i).accepter.equals(p.username)) {
							
							tablemodel2.addRow(new Object[] {
									r1.get(i).nome_impianto,
									r1.get(i).nome_campo,
									r1.get(i).m.data,
									r1.get(i).m.orario,
									r1.get(i).ruolo,
									r1.get(i).m.organizzatore,
									"CANCELLATI"
							});						
						}
					}
				}
			}
			if (tablemodel2.getRowCount() == 0)
				JOptionPane.showMessageDialog(null, "Ups.. non ci sono partite a cui puoi partecipare!");


			p0.setVisible(false);
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(false);
			p4.setVisible(false);
			p5.setVisible(false);
			p6.setVisible(false);
			p7.setVisible(true);
			setContentPane(p7);
		}
	}
}