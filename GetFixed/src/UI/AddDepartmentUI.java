package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Control.CtrCustomer;
import Control.CtrDepartment;
import Control.CtrDepartment;
import Control.CtrFunctionality;
import Control.CtrOrder;
import Control.CtrProduct;
import Control.CtrSale;
import Control.CtrService;
import Model.Department;

public class AddDepartmentUI {
	private JTable table;
	private JPanel contentPanel;
	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrDepartment DepartmentCtr = new CtrDepartment();
	CtrCustomer customerCtr = new CtrCustomer();
	CtrService serviceCtr = new CtrService();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrSale saleCtr = new CtrSale();
	CtrOrder orderCtr = new CtrOrder();
	ArrayList<String> saleID = new ArrayList<>();
	protected Department department;

	AddDepartmentUI(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	void main() {
		contentPanel.removeAll();

		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null, null } }, new String[] { "ID","Name", "Address", "Location",
				"Telephone", "E-mail" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, String.class, String.class, String.class  };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { true, true, true, true, true, true };

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
				model.addRow(new Object[] { null, null, null, null, null, null });
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
				
					try {
						for (int x = 0; x < table.getColumnCount(); x++) {
							values.add(table.getValueAt(i, x).toString());
							
						}

						DepartmentCtr.insertDepartment(Integer.parseInt(values.get(0)),
								values.get(1), values.get(2), values.get(3),
								values.get(4),values.get(5));

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