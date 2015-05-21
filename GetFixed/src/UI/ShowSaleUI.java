package UI;

import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.*;

public class ShowSaleUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrSale saleCtr = new CtrSale();
	JButton btnShowSale = new JButton();

	ShowSaleUI(JPanel contentPanel, JPanel secondaryMenuPanel, JButton btnShowSale) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		this.btnShowSale = btnShowSale;
	}

	void make() {
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));

		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null } }, new String[] { "ID",
				"Date", "Customer", "Department", "Price" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { false, false, false, false, false };

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

		model.removeRow(0);

		for (Object[] object : saleCtr.addAllSales()) {
			model.addRow(object);
		}

		JRadioButton rdbtnAalborg = new JRadioButton("Aalborg");
		JRadioButton rdbtnAarhus = new JRadioButton("Aarhus");
		JRadioButton rdbtnOdense = new JRadioButton("Odense");
		JRadioButton rdbtnCopenhagen = new JRadioButton("Copenhagen");

		rdbtnAalborg.setBounds(22, 355, 109, 23);
		rdbtnAalborg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String department = rdbtnAalborg.getText();
				int departmentId = departmentCtr.findByName(department).getId();

				if (rdbtnAalborg.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : saleCtr.addAllSalesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 3).toString();
						if (departmentName.equals(department)) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnAalborg);

		rdbtnAarhus.setBounds(133, 355, 109, 23);
		rdbtnAarhus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String department = rdbtnAarhus.getText();
				int departmentId = departmentCtr.findByName(department).getId();

				if (rdbtnAarhus.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : saleCtr.addAllSalesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 3).toString();
						if (departmentName.equals(department)) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnAarhus);

		rdbtnOdense.setBounds(244, 355, 109, 23);
		rdbtnOdense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String department = rdbtnOdense.getText();
				int departmentId = departmentCtr.findByName(department).getId();

				if (rdbtnOdense.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : saleCtr.addAllSalesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 3).toString();
						if (departmentName.equals(department)) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnOdense);

		rdbtnCopenhagen.setBounds(355, 355, 109, 23);
		rdbtnCopenhagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String department = rdbtnCopenhagen.getText();
				int departmentId = departmentCtr.findByName(department).getId();

				if (rdbtnCopenhagen.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : saleCtr.addAllSalesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 3).toString();
						if (departmentName.equals(department)) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnCopenhagen);

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

				Sale sale = new Sale();

				if (functionalityCtr.getClicks() <= 1) {
					int rowCount = model.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}

				if (flag) {
					sale = saleCtr.findById(Integer.parseInt(search));
				}
				model.addRow(new Object[] { sale.getId(), sale.getDate(), sale.getCustomer().getId(),
						sale.getDepartment().getName(), sale.getTotalPrice() });
				txtSearch.setText("");
			}
		});
		contentPanel.add(btnSearch);

		JButton btnShowProducts = new JButton("Show items");
		btnShowProducts.setBounds(705, 11, 120, 25);
		btnShowProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int index = 0; index < functionalityCtr.getSaleId().size(); index++) {
					functionalityCtr.getSaleId().remove(index);
				}

				int[] vals = table.getSelectedRows();
				for (int i : vals) {
					functionalityCtr.getSaleId().add(table.getValueAt(i, 0).toString());
				}

				ShowItemsInSaleUI frame = new ShowItemsInSaleUI(functionalityCtr);
				frame.pack();
				frame.setBounds(200, 200, 500, 250);
				frame.setTitle("Products in sales");
				frame.setVisible(true);

			}
		});
		contentPanel.add(btnShowProducts);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int[] vals = table.getSelectedRows();
				boolean flag = true;
				for (int i : vals) {

					try {
						saleCtr.deletePartSale(Integer.parseInt(table.getValueAt(i, 0).toString()));
						// ic.deleteInvoice(Integer.parseInt(table
						// .getValueAt(i, 0).toString()));
						saleCtr.deleteSale(Integer.parseInt(table.getValueAt(i, 0).toString()));
					} catch (Exception e1) {
						e1.printStackTrace();
						flag = false;
					}

				}

				btnShowSale.doClick();

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
