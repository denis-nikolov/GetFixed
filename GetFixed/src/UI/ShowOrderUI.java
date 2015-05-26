package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.Order;

public class ShowOrderUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	CtrProduct productCtr = new CtrProduct();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrOrder orderCtr = new CtrOrder();
	JButton btnShowOrder = new JButton();

	ShowOrderUI(JPanel contentPanel, JPanel secondaryMenuPanel, JButton btnShowOrder) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		this.btnShowOrder = btnShowOrder;
	}

	void make() {
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));

		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null } }, new String[] { "ID",
						"Date", "Product Barcode", "Product Name", "Price/pc", "Quantity", "Price", "Received",
						"Department" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, String.class, Double.class,
					Integer.class, Double.class, Boolean.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false };

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

		model.removeRow(0);

		for (Object[] object : orderCtr.addAllOrders()) {
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

					for (Object[] object : orderCtr.addAllOrdersForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 8).toString();
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

					for (Object[] object : orderCtr.addAllOrdersForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 8).toString();
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

					for (Object[] object : orderCtr.addAllOrdersForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 8).toString();
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

					for (Object[] object : orderCtr.addAllOrdersForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 8).toString();
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

				if (functionalityCtr.getClicks() <= 1) {
					int rowCount = model.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}

				if (flag) {
					model.addRow(orderCtr.addOrderById(Integer.parseInt(search)));
				}
				txtSearch.setText("");
			}
		});
		contentPanel.add(btnSearch);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int[] vals = table.getSelectedRows();
				boolean flag = true;
				for (int i : vals) {

					try {
						int id = Integer.parseInt(table.getValueAt(i, 0).toString());
						orderCtr.deleteOrder(id);
					} catch (Exception e1) {
						e1.printStackTrace();
						flag = false;
					}

				}

				btnShowOrder.doClick();

			}

		});
		btnDelete.setBounds(739 + functionalityCtr.getAddWidth(), 355, 89, 23);
		contentPanel.add(btnDelete);


		contentPanel.invalidate();
		contentPanel.revalidate();
		contentPanel.repaint();
		contentPanel.setVisible(true);

	}
}
