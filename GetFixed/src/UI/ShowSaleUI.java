package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
import Model.Product;
import Model.Sale;
import Model.Service;


public class ShowSaleUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrSupplier supplierCtr = new CtrSupplier();
	CtrEmployee employeeCtr = new CtrEmployee();
	CtrCustomer customerCtr = new CtrCustomer();
	CtrService serviceCtr = new CtrService();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrSale saleCtr = new CtrSale();
	CtrOrder orderCtr = new CtrOrder();
	JButton btnShowSale = new JButton();
	
	
	ShowSaleUI(JPanel contentPanel,JPanel secondaryMenuPanel, JButton btnShowSale){
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		this.btnShowSale = btnShowSale;
	}
	void make(){

	contentPanel.removeAll();

	table = new JTable();
	table.setFont(new Font("Tahoma", Font.PLAIN, 15));

	table.setModel(new DefaultTableModel(new Object[][] { {
			null, null, null, null } }, new String[] {
			"ID", "Date", "Customer", "Price" }) {
		Class[] columnTypes = new Class[] { Integer.class,
				String.class, String.class, Double.class };

		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}

		boolean[] canEdit = new boolean[] { false, false,
				false, false };

		public boolean isCellEditable(int rowIndex,
				int columnIndex) {
			return canEdit[columnIndex];
		}
	});
	table.setBounds(10, 27, 588, 195);
	JScrollPane scrollPane = new JScrollPane(table);
	scrollPane.setBounds(10, 52, 818, 300);
	table.setFillsViewportHeight(true);
	contentPanel.add(scrollPane);
	DefaultTableModel model = (DefaultTableModel) table
			.getModel();

	ArrayList<Sale> saleList = saleCtr.findAllSales();
	model.removeRow(0);

	for (Sale sale : saleList) {

		model.addRow(new Object[] { sale.getId(),
				sale.getDate(), sale.getCustomer().getId(),
				sale.getTotalPrice() });
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

			Sale sale = new Sale();

			if (functionalityCtr.getClicks() <= 1) {
				int rowCount = model.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
					model.removeRow(i);
				}
			}

			if (flag) {
				sale = saleCtr.findById(Integer
						.parseInt(search));
			}
			model.addRow(new Object[] { sale.getId(),
					sale.getDate(),
					sale.getCustomer().getId(),
					sale.getTotalPrice() });
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
				functionalityCtr.getSaleId().add(table.getValueAt(i, 0)
						.toString());
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
					saleCtr.deletePartSale(Integer
							.parseInt(table
									.getValueAt(i, 0)
									.toString()));
					// ic.deleteInvoice(Integer.parseInt(table
					// .getValueAt(i, 0).toString()));
					saleCtr.deleteSale(Integer
							.parseInt(table
									.getValueAt(i, 0)
									.toString()));
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

