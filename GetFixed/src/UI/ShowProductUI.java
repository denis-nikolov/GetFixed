package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.Department;
import Model.Product;

public class ShowProductUI {
	private JPanel contentPane;
	private JPanel mainMenuPanel;
	private JPanel secondaryMenuPanel;
	private JPanel contentPanel;
	private JTable table;
	private JButton btnShowProducts;
	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	Department department = new Department();

	ShowProductUI(JPanel contentPanel, JPanel secondaryMenuPanel, JButton btnShowProducts) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		this.btnShowProducts = btnShowProducts;
	}

	void make() {
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null, null, null,
				null, null, null, null } }, new String[] { "Barcode", "Name", "Selling price", "Leasing price",
				"Ordering price", "Price discount", "Amount discount", "Amount", "Min amount", "Max amount",
				"Ordered Amount", "Department", "Supplier ID" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Float.class, Float.class, Float.class,
					Float.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
					String.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { false, true, true, true, true, true, true, false, true, true, false,
					false, true };

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

		for (Object[] object : productCtr.addAllProducts()) {
			model.addRow(object);
		}

		JRadioButton rdbtnAalborg = new JRadioButton("Aalborg");
		rdbtnAalborg.setBounds(222, 11, 109, 23);
		rdbtnAalborg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				department = departmentCtr.findByName(rdbtnAalborg.getText());
				int departmentId = department.getId();

				if (rdbtnAalborg.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : productCtr.addAllProductsForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						ArrayList<String> values = new ArrayList<>();
						for (int columns = 0; columns < table.getColumnCount(); columns++) {
							values.add(table.getValueAt(rows, columns).toString());
						}
						String departmentName = values.get(11);
						values.clear();
						if (departmentName.equals(rdbtnAalborg.getText())) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnAalborg);

		JRadioButton rdbtnAarhus = new JRadioButton("Aarhus");
		rdbtnAarhus.setBounds(333, 11, 109, 23);
		rdbtnAarhus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				department = departmentCtr.findByName(rdbtnAarhus.getText());
				int departmentId = department.getId();

				if (rdbtnAarhus.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : productCtr.addAllProductsForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						ArrayList<String> values = new ArrayList<>();
						for (int columns = 0; columns < table.getColumnCount(); columns++) {
							values.add(table.getValueAt(rows, columns).toString());
						}
						String departmentName = values.get(11);
						values.clear();
						if (departmentName.equals(rdbtnAarhus.getText())) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnAarhus);

		JRadioButton rdbtnOdense = new JRadioButton("Odense");
		rdbtnOdense.setBounds(444, 11, 109, 23);
		rdbtnOdense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				department = departmentCtr.findByName(rdbtnOdense.getText());
				int departmentId = department.getId();

				if (rdbtnOdense.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : productCtr.addAllProductsForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						ArrayList<String> values = new ArrayList<>();
						for (int columns = 0; columns < table.getColumnCount(); columns++) {
							values.add(table.getValueAt(rows, columns).toString());
						}
						String departmentName = values.get(11);
						values.clear();
						if (departmentName.equals(rdbtnOdense.getText())) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnOdense);

		JRadioButton rdbtnCopenhagen = new JRadioButton("Copenhagen");
		rdbtnCopenhagen.setBounds(555, 11, 109, 23);
		rdbtnCopenhagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				department = departmentCtr.findByName(rdbtnCopenhagen.getText());
				int departmentId = department.getId();

				if (rdbtnCopenhagen.isSelected()) {
					functionalityCtr.addId(departmentId);
					functionalityCtr.addClick();

					if (functionalityCtr.getClicks() <= 1) {
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
					}

					for (Object[] object : productCtr.addAllProductsForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						ArrayList<String> values = new ArrayList<>();
						for (int columns = 0; columns < table.getColumnCount(); columns++) {
							values.add(table.getValueAt(rows, columns).toString());
						}
						String departmentName = values.get(11);
						values.clear();
						if (departmentName.equals(rdbtnCopenhagen.getText())) {
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

				if (functionalityCtr.getClicks() <= 1) {
					int rowCount = model.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}

				if (flag) {
					model.addRow(productCtr.addProductByBarcode(Integer.parseInt(search)));
				} else {
					model.addRow(productCtr.addProductByName(search));
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
					productCtr.updateProduct(Integer.parseInt(values.get(0)), values.get(1),
							Double.parseDouble(values.get(2)), Double.parseDouble(values.get(3)),
							Double.parseDouble(values.get(4)), Double.parseDouble(values.get(5)),
							Integer.parseInt(values.get(6)), Integer.parseInt(values.get(7)),
							Integer.parseInt(values.get(8)), Integer.parseInt(values.get(9)),
							Integer.parseInt(values.get(10)), Integer.parseInt(values.get(12)));
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
						Product product = productCtr.findByBarcodeAndDepartmentLocation(
								Integer.parseInt(table.getValueAt(i, 0).toString()), table.getValueAt(i, 11).toString());
						int barcode = product.getBarcode();
						int departmentId = product.getDepartment().getId();
						productCtr.deleteProduct(barcode, departmentId);
					} catch (Exception e1) {
						e1.printStackTrace();
						flag = false;
					}

				}
				btnShowProducts.doClick();
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