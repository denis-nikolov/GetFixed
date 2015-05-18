package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.Department;

public class AddProductUI {
	private JPanel contentPane;
	private JPanel mainMenuPanel;
	private JPanel secondaryMenuPanel;
	private JPanel contentPanel;
	private JTable table;
	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	Department department = new Department();

	AddProductUI(JPanel contentPanel, JPanel secondaryMenuPanel) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
	}

	void make() {
		JRadioButton rdbtnAalborg = new JRadioButton("Aalborg");
		JRadioButton rdbtnAarhus = new JRadioButton("Aarhus");
		JRadioButton rdbtnOdense = new JRadioButton("Odense");
		JRadioButton rdbtnCopenhagen = new JRadioButton("Copenhagen");

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		rdbtnAalborg.setBounds(22, 11, 109, 23);
		rdbtnAalborg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				department = departmentCtr.findByLocation(rdbtnAalborg
						.getText());
				int departmentId = department.getId();
				if (rdbtnAalborg.isSelected()) {
					functionalityCtr.addId(departmentId);
				} else {
					functionalityCtr.removeId(departmentId);
				}
			}
		});
		contentPanel.add(rdbtnAalborg);

		rdbtnAarhus.setBounds(133, 11, 109, 23);
		rdbtnAarhus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				department = departmentCtr.findByLocation(rdbtnAarhus.getText());
				int departmentId = department.getId();
				if (rdbtnAarhus.isSelected()) {
					functionalityCtr.addId(departmentId);
				} else {
					functionalityCtr.removeId(departmentId);
				}
			}
		});
		contentPanel.add(rdbtnAarhus);

		rdbtnOdense.setBounds(244, 11, 109, 23);
		rdbtnOdense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				department = departmentCtr.findByLocation(rdbtnOdense.getText());
				int departmentId = department.getId();
				if (rdbtnOdense.isSelected()) {
					functionalityCtr.addId(departmentId);
				} else {
					functionalityCtr.removeId(departmentId);
				}
			}
		});
		contentPanel.add(rdbtnOdense);

		rdbtnCopenhagen.setBounds(355, 11, 109, 23);
		rdbtnCopenhagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				department = departmentCtr.findByLocation(rdbtnCopenhagen
						.getText());
				int departmentId = department.getId();
				if (rdbtnCopenhagen.isSelected()) {
					functionalityCtr.addId(departmentId);
				} else {
					functionalityCtr.removeId(departmentId);
				}
			}
		});
		contentPanel.add(rdbtnCopenhagen);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null, null, null, null, null, null, 0, null } },
				new String[] { "Barcode", "Name", "Selling price",
						"Leasing price", "Ordering price", "Price discount",
						"Amount discount", "Amount", "Min amount",
						"Max amount", "Ordered Amount", "Supplier ID" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					Float.class, Float.class, Float.class, Float.class,
					Integer.class, Integer.class, Integer.class, Integer.class,
					Integer.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { true, true, true, true, true,
					true, true, true, true, true, false, true };

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

				model.addRow(new Object[] { null, null, null, null, null, null,
						null, null, null, null, 0, null });
			}
		});
		btnAdd.setBounds(625, 11, 89, 23);
		contentPanel.add(btnAdd);

		JButton btnRemove = new JButton("Remove row");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				for (int i = 0; i < rows.length; i++) {
					model.removeRow(rows[i] - i);
				}
			}
		});
		btnRemove.setBounds(720, 11, 105, 23);
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

						for (Integer integer : functionalityCtr.getIds()) {
							productCtr.insertProduct(
									Integer.parseInt(values.get(0)),
									values.get(1),
									Double.parseDouble(values.get(2)),
									Double.parseDouble(values.get(3)),
									Double.parseDouble(values.get(4)),
									Double.parseDouble(values.get(5)),
									Integer.parseInt(values.get(6)),
									Integer.parseInt(values.get(7)),
									Integer.parseInt(values.get(8)),
									Integer.parseInt(values.get(9)),
									Integer.parseInt(values.get(10)), integer,
									Integer.parseInt(values.get(11)));
						}

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
