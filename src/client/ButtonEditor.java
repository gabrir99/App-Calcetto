package client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import domain.Profile;
import domain.Request;

public class ButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;

	protected Client c;
	protected ArrayList<Request> r = new ArrayList<Request>();
	protected Profile p;
	protected JButton button;
	protected String label;
	protected boolean isPushed;
	protected int row;
	protected JTable jt;

	public ButtonEditor(JCheckBox cbox, Profile p, ArrayList<Request> re) {
		super(cbox);
		this.p = p;
		this.r = re;
		this.button = new JButton();

		button.setOpaque(true);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.jt = table;
		this.row = row;
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	public Object getCellEditorValue() {
		if (isPushed) {
			c = new Client();
			Request tmp = r.get(jt.getSelectedRow());
			
			if(label.equals("PARTECIPA")) {
				try {
					
					c.updateRequest(tmp.id, tmp.m.campo_id, tmp.m.data, tmp.m.orario, p.username);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(label.equals("CANCELLATI")) {
				try {
					c.updateRequest(tmp.id, tmp.m.campo_id, tmp.m.data, tmp.m.orario, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		isPushed = false;
		return new String(label);
	}

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}