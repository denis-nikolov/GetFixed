package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.Order;
import Model.Product;

public class CreateOrderUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	CtrProduct productCtr = new CtrProduct();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrOrder orderCtr = new CtrOrder();

	CreateOrderUI(JPanel contentPanel, JPanel secondaryMenuPanel) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
	}

	void make() {
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));

		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null } }, new String[] {
				"Barcode", "Name", "Price/pc", "Quantity", "Price" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Double.class, Integer.class, Double.class };

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
		scrollPane.setBounds(10, 52, 818 + functionalityCtr.getAddWidth(), 300);
		table.setFillsViewportHeight(true);
		contentPanel.add(scrollPane);
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		table.getModel().addTableModelListener(new TableModelListener() {
			Product product = new Product();

			public void tableChanged(TableModelEvent e) {

				if (e.getType() == (TableModelEvent.UPDATE)) {
					int intColumn = e.getColumn();
					int intRows = e.getFirstRow();
					updateData(intRows, intColumn);
				}
			}

			private void updateData(int intRows, int intColumn) {

				if (intColumn == 0) {
					int barcode = Integer.parseInt(table.getValueAt(intRows, 0).toString());

					product = productCtr.findByBarcode(barcode);
					if (productCtr.isAvailable(barcode)) {
						model.setValueAt(product.getName(), intRows, 1);
						model.setValueAt(product.getOrderingPrice(), intRows, 2);
						model.setValueAt(0, intRows, 3);
						model.setValueAt(0, intRows, 4);
					} else {
						JOptionPane.showMessageDialog(null, "No product with that barcode!", "Barcode error!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				if (intColumn == 3) {
					int amount = Integer.parseInt(table.getValueAt(intRows, 3).toString());

					double pricePerPiece = Double.parseDouble(table.getValueAt(intRows, 2).toString());
					double price = pricePerPiece * amount;
					model.setValueAt(price, intRows, 4);
				}
			}
		});

		JButton btnRemove = new JButton("Remove row");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				for (int index = 0; index < rows.length; index++) {
					model.removeRow(rows[index] - index);
				}
			}
		});
		btnRemove.setBounds(720 + functionalityCtr.getAddWidth(), 11, 105, 23);
		contentPanel.add(btnRemove);

		JRadioButton rdbtnAalborg = new JRadioButton("Aalborg");
		JRadioButton rdbtnAarhus = new JRadioButton("Aarhus");
		JRadioButton rdbtnOdense = new JRadioButton("Odense");
		JRadioButton rdbtnCopenhagen = new JRadioButton("Copenhagen");

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnAalborg);
		buttonGroup.add(rdbtnAarhus);
		buttonGroup.add(rdbtnOdense);
		buttonGroup.add(rdbtnCopenhagen);

		rdbtnAalborg.setBounds(22, 355, 109, 23);
		rdbtnAalborg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String department = rdbtnAalborg.getText();
				int departmentId = departmentCtr.findByName(department).getId();
				if (rdbtnAalborg.isSelected()) {
					functionalityCtr.setDepartmentId(departmentId);
				} else {
					functionalityCtr.setDepartmentId(0);
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
					functionalityCtr.setDepartmentId(departmentId);
				} else {
					functionalityCtr.setDepartmentId(0);
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
					functionalityCtr.setDepartmentId(departmentId);
				} else {
					functionalityCtr.setDepartmentId(0);
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
					functionalityCtr.setDepartmentId(departmentId);
				} else {
					functionalityCtr.setDepartmentId(0);
				}
			}
		});
		contentPanel.add(rdbtnCopenhagen);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.selectAll();
				int[] vals = table.getSelectedRows();
				for (int i = 0; i < vals.length; i++) {
					for (int x = 0; x < table.getColumnCount(); x++) {
						functionalityCtr.addValue(table.getValueAt(i, x).toString());
					}

					try {
						ArrayList<String> values = functionalityCtr.getValues();
						orderCtr.insertOrder(Integer.parseInt(values.get(0)), Integer.parseInt(values.get(3)),
								Double.parseDouble(values.get(4)), functionalityCtr.getDepartmentId());

					} catch (Exception e1) {
						e1.printStackTrace();
					}
					functionalityCtr.removeAllValues();
				}

			}
		});
		btnSubmit.setBounds(738 + functionalityCtr.getAddWidth(), 355, 89, 23);
		contentPanel.add(btnSubmit);

		contentPanel.invalidate();
		contentPanel.revalidate();
		contentPanel.repaint();
		contentPanel.setVisible(true);

	}
}
