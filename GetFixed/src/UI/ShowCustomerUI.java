package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.Customer;
import Model.Department;

public class ShowCustomerUI {
	private JPanel contentPane;
	private JPanel mainMenuPanel;
	private JPanel secondaryMenuPanel;
	private JButton btnShowCustomer;
	private JPanel contentPanel;
	private JTable table;
	CtrCustomer customerCtr = new CtrCustomer();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	Department department = new Department();

	ShowCustomerUI(JPanel contentPanel, JPanel secondaryMenuPanel,
			JButton btnShowCustomer) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		this.btnShowCustomer = btnShowCustomer;
	}

	void make() {
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null, null } }, new String[] { "ID", "Name",
				"Surname", "Telephone", "E-mail", "Discount(%)" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, String.class, String.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { false, true, true, true, true,
					true };

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

		JButton btnRemove = new JButton("Remove row");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				for (int index = 0; index < rows.length; index++) {
					model.removeRow(rows[index] - index);
				}
			}
		});
		btnRemove.setBounds(720, 11, 105, 23);
		contentPanel.add(btnRemove);

		model.removeRow(0);

		for (Object[] object : customerCtr.addAllCustomers()) {
			model.addRow(object);
		}

		JTextField txtSearch = new JTextField();
		txtSearch.setBounds(15, 11, 86, 25);
		contentPanel.add(txtSearch);
		txtSearch.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(111, 11, 89, 25);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				functionalityCtr.addClick();
				String search = txtSearch.getText();
				boolean flag = false;
				try {
					Integer.parseInt(search);
					flag = true;
				} catch (Exception e2) {
				}

				if (functionalityCtr.getClicks() <= 1) {
					int rowCount = model.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}

				if (flag) {
					model.addRow(customerCtr.addCustomerById(Integer
							.parseInt(search)));
				} else {
					model.addRow(customerCtr.addCustomerByName(search));
				}

				txtSearch.setText("");
			}
		});
		contentPanel.add(btnSearch);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] vals = table.getSelectedRows();
				for (int i : vals) {
					ArrayList<String> values = new ArrayList<>();
					for (int x = 0; x < table.getColumnCount(); x++) {
						values.add(table.getValueAt(i, x).toString());

					}
					customerCtr.updateCustomer(Integer.parseInt(values.get(0)),
							values.get(1), values.get(2), values.get(3),
							values.get(4), Integer.parseInt(values.get(5)));
				}
			}
		});
		btnUpdate.setBounds(639, 380, 89, 23);
		contentPanel.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int[] vals = table.getSelectedRows();
				boolean flag = true;
				for (int i : vals) {

					try {
						Customer customer = customerCtr.findById(Integer
								.parseInt(table.getValueAt(i, 0).toString()));
						int id = customer.getId();
						customerCtr.deleteCustomer(id);
					} catch (Exception e1) {
						e1.printStackTrace();
						flag = false;
					}

				}
				btnShowCustomer.doClick();
			}

		});
		btnDelete.setBounds(739, 380, 89, 23);
		contentPanel.add(btnDelete);

		contentPanel.invalidate();
		contentPanel.revalidate();
		contentPanel.repaint();
		contentPanel.setVisible(true);
	}
}
