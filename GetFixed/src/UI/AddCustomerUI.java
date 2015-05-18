package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.Department;

public class AddCustomerUI {
	private JPanel contentPane;
	private JPanel mainMenuPanel;
	private JPanel secondaryMenuPanel;
	private JPanel contentPanel;
	private JTable table;
	CtrCustomer customerCtr = new CtrCustomer();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	Department department = new Department();

	AddCustomerUI(JPanel contentPanel, JPanel secondaryMenuPanel) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
	}

	void make() {
		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null } }, new String[] { "Name", "Surname",
				"Telephone", "E-mail", "Discount(%)" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { true, true, true, true, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		table.setBounds(10, 27, 588, 195);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 52, 818, 300);
		table.setFillsViewportHeight(true);
		contentPanel.add(scrollPane);
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				model.addRow(new Object[] { null, null, null, null, null });
			}
		});
		btnAdd.setBounds(635, 11, 89, 23);
		contentPanel.add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				for (int i = 0; i < rows.length; i++) {
					model.removeRow(rows[i] - i);
				}
			}
		});
		btnRemove.setBounds(739, 11, 89, 23);
		contentPanel.add(btnRemove);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				table.selectAll();
				int[] vals = table.getSelectedRows();
				for (int i = 0; i < vals.length; i++) {
					ArrayList<String> values = new ArrayList<>();
					for (int x = 0; x < table.getColumnCount(); x++) {
						values.add(table.getValueAt(i, x).toString());
					}

					try {
						customerCtr.insertCustomer(values.get(0),
								values.get(1), values.get(2), values.get(3),
								Integer.parseInt(values.get(4)));

					} catch (Exception e1) {
						e1.printStackTrace();
					}
					values.clear();
				}

			}
		});
		btnSubmit.setBounds(530, 355, 89, 23);
		contentPanel.add(btnSubmit);

		contentPanel.invalidate();
		contentPanel.revalidate();
		contentPanel.repaint();
		contentPanel.setVisible(true);
	}
}