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
import javax.swing.table.DefaultTableModel;

import Control.CtrCustomer;
import Control.CtrDepartment;
import Control.CtrEmployee;
import Control.CtrFunctionality;
import Control.CtrOrder;
import Control.CtrProduct;
import Control.CtrSale;
import Control.CtrService;
import Control.CtrSupplier;
import Model.Department;

public class AddEmployeeUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrEmployee employeeCtr = new CtrEmployee();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	protected Department department;
	
	AddEmployeeUI(JPanel contentPanel,JPanel secondaryMenuPanel){
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		
	}
	
	
	void main(){
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		JRadioButton rdbtnAalborg = new JRadioButton("Aalborg");
		JRadioButton rdbtnAarhus = new JRadioButton("Aarhus");
		JRadioButton rdbtnOdense = new JRadioButton("Odense");
		JRadioButton rdbtnCopenhagen = new JRadioButton(
				"Copenhagen");

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		rdbtnAalborg.setBounds(22, 11, 109, 23);
		rdbtnAalborg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				department = departmentCtr
						.findByName(rdbtnAalborg.getText());
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
				department = departmentCtr
						.findByName(rdbtnAarhus.getText());
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
				department = departmentCtr
						.findByName(rdbtnOdense.getText());
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
				department = departmentCtr
						.findByName(rdbtnCopenhagen
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
		table.setModel(new DefaultTableModel(new Object[][] { {
				null, null, null } }, new String[] { "Name",
				"Surname", "Address", "Telephone", "E-mail",
				"Password" }) {
			Class[] columnTypes = new Class[] { String.class,
					String.class, String.class, String.class,
					String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { true, true,
					true, true, true, true };

			public boolean isCellEditable(int rowIndex,
					int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		table.setBounds(10, 27, 588, 195);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 52, 818 + functionalityCtr.getAddWidth(), 300);
		table.setFillsViewportHeight(true);
		contentPanel.add(scrollPane);
		DefaultTableModel model = (DefaultTableModel) table
				.getModel();

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.addRow(new Object[] { null, null, null,
						null, null, null });
			}
		});
		btnAdd.setBounds(635 + functionalityCtr.getAddWidth(), 11, 89, 23);
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
		btnRemove.setBounds(739 + functionalityCtr.getAddWidth(), 11, 89, 23);
		contentPanel.add(btnRemove);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				table.selectAll();
				int[] vals = table.getSelectedRows();
				for (int i = 0; i < vals.length; i++) {
					ArrayList<String> values = new ArrayList<>();
					for (int x = 0; x < table.getColumnCount(); x++) {
						values.add(table.getValueAt(i, x)
								.toString());
					}

					try {

						for (Integer integer : functionalityCtr
								.getIds()) {
							employeeCtr.insertEmployee(
									values.get(0),
									values.get(1),
									values.get(2),
									values.get(3),
									values.get(4),
									values.get(5), integer);
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
					values.clear();
				}

			}
		});
		btnSubmit.setBounds(739 + functionalityCtr.getAddWidth(), 355, 89, 23);
		contentPanel.add(btnSubmit);

		contentPanel.invalidate();
		contentPanel.revalidate();
		contentPanel.repaint();
		contentPanel.setVisible(true);

	}
}
