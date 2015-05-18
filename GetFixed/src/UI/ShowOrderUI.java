package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import Model.Order;


public class ShowOrderUI {
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
	ArrayList<String> saleID = new ArrayList<>();
	JButton btnShowSale = new JButton();
	
	ShowOrderUI(JPanel contentPanel,JPanel secondaryMenuPanel){
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
	}
	
	
	
	
	void make(){
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));

		table.setModel(new DefaultTableModel(new Object[][] { {
				null, null, null, null, null, null, null } },
				new String[] { "ID", "Date", "Product Barcode",
						"Product Name", "Price/pc", "Quantity",
						"Price" }) {
			Class[] columnTypes = new Class[] { Integer.class,
					String.class, Integer.class, String.class,
					Double.class, Integer.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { false, false,
					false, false, false, false, false };

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

		ArrayList<Order> orderList = orderCtr.findAllOrders();
		model.removeRow(0);

		for (Order order : orderList) {

			model.addRow(new Object[] { order.getId(),
					order.getDate(),
					order.getProduct().getBarcode(),
					order.getProduct().getName(),
					order.getProduct().getOrderingPrice(),
					order.getAmount(), order.getPrice() });
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

				Order order = new Order();

				if (functionalityCtr.getClicks() <= 1) {
					int rowCount = model.getRowCount();
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}

				if (flag) {
					order = orderCtr.findById(Integer
							.parseInt(search));
				}
				model.addRow(new Object[] { order.getId(),
						order.getDate(),
						order.getProduct().getBarcode(),
						order.getProduct().getName(),
						order.getProduct().getOrderingPrice(),
						order.getAmount(), order.getPrice() });
				txtSearch.setText("");
			}
		});
		contentPanel.add(btnSearch);

		contentPanel.invalidate();
		contentPanel.revalidate();
		contentPanel.repaint();
		contentPanel.setVisible(true);	

	}
//});
//
//invalidate();
//revalidate();
//repaint();
//setVisible(true);
//
//		
//	}
}
