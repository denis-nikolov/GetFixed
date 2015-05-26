package UI;

import java.awt.Font;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.*;

public class EndLeaseUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	CtrProduct productCtr = new CtrProduct();
	CtrCustomer customerCtr = new CtrCustomer();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrLease leaseCtr = new CtrLease();
	JButton btnEndLease = new JButton();

	EndLeaseUI(JPanel contentPanel, JPanel secondaryMenuPanel, JButton btnEndLease) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		this.btnEndLease = btnEndLease;
	}

	void make() {
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));

		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null } }, new String[] { "ID",
				"Date", "Customer", "Period", "Returned", "Department", "Price" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, Integer.class,
					Boolean.class, String.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

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
		btnRemove.setBounds(590 + functionalityCtr.getAddWidth(), 11, 105, 25);
		contentPanel.add(btnRemove);
		
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

					for (Object[] object : leaseCtr.addAllLeasesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 5).toString();
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

					for (Object[] object : leaseCtr.addAllLeasesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 5).toString();
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

					for (Object[] object : leaseCtr.addAllLeasesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 5).toString();
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

					for (Object[] object : leaseCtr.addAllLeasesForDepartment(departmentId)) {
						model.addRow(object);
					}
				} else {
					String departmentName = "";
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						departmentName = model.getValueAt(rows, 5).toString();
						if (departmentName.equals(department)) {
							model.removeRow(rows);
							rows--;
						}
					}
				}
			}
		});
		contentPanel.add(rdbtnCopenhagen);

		model.removeRow(0);

		for (Object[] object : leaseCtr.addAllUnreturnedLeases()) {
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
					model.addRow(leaseCtr.addLeaseById(Integer.parseInt(search)));
				}
				txtSearch.setText("");
			}
		});
		contentPanel.add(btnSearch);

		JButton btnShowProducts = new JButton("Show items");
		btnShowProducts.setBounds(705 + functionalityCtr.getAddWidth(), 11, 120, 25);
		btnShowProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int index = 0; index < functionalityCtr.getLeaseId().size(); index++) {
					functionalityCtr.getLeaseId().remove(index);
				}

				int[] vals = table.getSelectedRows();
				for (int i : vals) {
					functionalityCtr.getLeaseId().add(table.getValueAt(i, 0).toString());
				}

				ShowItemsInLeaseUI frame = new ShowItemsInLeaseUI(functionalityCtr);
				frame.pack();
				frame.setBounds(200, 200, 500, 250);
				frame.setTitle("Products in leases");
				frame.setVisible(true);

			}
		});
		contentPanel.add(btnShowProducts);

		JButton btnReturnLease = new JButton("Return Lease");
		btnReturnLease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] vals = table.getSelectedRows();
				for (int i : vals) {
					int id = Integer.parseInt(model.getValueAt(i, 0).toString());
					try {
						leaseCtr.endLease(id);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
					if (leaseCtr.onTime(id)) {
						JOptionPane.showMessageDialog(null, "Lease was returned on time!", "Lease confirmation",
								JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Lease was returned after the deadline", "Time violation",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				btnEndLease.doClick();
			}

		});
		btnReturnLease.setBounds(717 + functionalityCtr.getAddWidth(), 355, 110, 23);
		contentPanel.add(btnReturnLease);

		contentPanel.invalidate();
		contentPanel.revalidate();
		contentPanel.repaint();
		contentPanel.setVisible(true);
	}
}
