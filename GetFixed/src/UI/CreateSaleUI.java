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
import UI.MainUI.Table;

public class CreateSaleUI {
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
	
	CreateSaleUI(JPanel contentPanel,JPanel secondaryMenuPanel){
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
		
	}
	void make(){
			table = new JTable();

			table.setFont(new Font("Tahoma", Font.PLAIN, 15));

			table.setModel(new DefaultTableModel(new Object[][] { {
					null, null, null, null, null, } },
					new String[] { "Barcode", "Name", "Price/pc",
							"Quantity", "Total" }) {
				Class[] columnTypes = new Class[] { Integer.class,
						String.class, Double.class, Integer.class,
						Double.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

				boolean[] canEdit = new boolean[] { true, false,
						false, true, false };

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

			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					model.addRow(new Object[] { null, null, null,
							null, null });
				}
			});
			btnAdd.setBounds(625, 11, 89, 23);
			contentPanel.add(btnAdd);

			JLabel lblTotalPrice = new JLabel("Total price:");
			lblTotalPrice.setHorizontalAlignment(SwingConstants.TRAILING);
			lblTotalPrice.setBounds(556, 355, 72, 23);
			contentPanel.add(lblTotalPrice);

			JTextField textFieldPrice = new JTextField();
			textFieldPrice.setBounds(638, 355, 86, 23);
			textFieldPrice.setEditable(false);
			contentPanel.add(textFieldPrice);
			textFieldPrice.setText("0.0");

			JLabel lblCustomerId = new JLabel("Customer ID:");
			lblCustomerId.setBounds(15, 11, 111, 14);
			contentPanel.add(lblCustomerId);

			JTextField textFieldCustomer = new JTextField();
			textFieldCustomer.setBounds(101, 8, 102, 20);
			textFieldCustomer.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
					if (e.getSource() == textFieldCustomer) {
						textFieldCustomer.selectAll();
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (e.getSource() == textFieldCustomer) {
						String id = textFieldCustomer.getText();
						if (customerCtr.findById(Integer.parseInt(id)) == null) {
							JOptionPane.showMessageDialog(null,
									"There is no customer with ID "+ id + " !", "ID Error",
									JOptionPane.ERROR_MESSAGE);
							textFieldCustomer.setText("0");
						}
					}
				}
			});
			contentPanel.add(textFieldCustomer);
			textFieldCustomer.setColumns(10);

			JButton btnRemove = new JButton("Remove");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int[] rows = table.getSelectedRows();
					for (int i = 0; i < rows.length; i++) {
						if (table.getValueAt(table.getSelectedRow(), 4) != null) {

							double total = Double.parseDouble(textFieldPrice.getText());

							double sum = Double.parseDouble(table.getValueAt(table.getSelectedRow(),4).toString());
                            int customerId = Integer.parseInt(textFieldCustomer.getText());
							double discount = saleCtr.calculateDiscount(customerId, sum);

							total -= saleCtr.calculateTotalPrice(sum, discount);
							textFieldPrice.setText(Double.toString(total));
						}
						model.removeRow(rows[i] - i);
					}
				}
			});
			btnRemove.setBounds(720, 11, 105, 23);
			contentPanel.add(btnRemove);

			JRadioButton rdbtnNotAMember = new JRadioButton("Not a member");
			rdbtnNotAMember.setBounds(225, 7, 109, 23);
			contentPanel.add(rdbtnNotAMember);
			rdbtnNotAMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (textFieldCustomer.isEditable()) {
						textFieldCustomer.setText("0");
						textFieldCustomer.setEditable(false);

					} else {
						textFieldCustomer.setEditable(true);
						textFieldCustomer.setText("");
					}
				}
			});

			table.getModel().addTableModelListener(
					new TableModelListener() {
						Product product = new Product();
						Service service = new Service();

						public void tableChanged(TableModelEvent e) {

							if (e.getType() == (TableModelEvent.UPDATE)) {
								int intColumn = e.getColumn();
								int intRows = e.getFirstRow();
								updateData(intRows, intColumn);
							}
						}

						private void updateData(int intRows, int intColumn) {

							if (intColumn == 0) {
								String text = textFieldCustomer.getText();
								if (text.length() == 0 || text == null) {
									JOptionPane.showMessageDialog(null,"You have not entered a customer!",
													"Customer Error",
													JOptionPane.ERROR_MESSAGE);
								}
								int barcode = Integer.parseInt(table.getValueAt(intRows, 0).toString());

								if (functionalityCtr.isProduct(barcode)) {
									product = productCtr.findByBarcode(barcode);
									if (productCtr.isAvailable(barcode)) {
										model.setValueAt(product.getName(),intRows, 1);
										model.setValueAt(product.getSellingPrice(),intRows, 2);
									} else {
										JOptionPane.showMessageDialog(null,"No product with that barcode!",
														"Barcode error!",
														JOptionPane.ERROR_MESSAGE);
									}
								} else {
									service = serviceCtr.findByBarcode(barcode);
									if (serviceCtr.isAvailable(barcode)) {
										model.setValueAt(service.getName(),intRows, 1);
										model.setValueAt(service.getPrice(),intRows, 2);
									} else {
										JOptionPane.showMessageDialog(null,"No service with that barcode!",
														"Barcode error!",
														JOptionPane.ERROR_MESSAGE);
									}
								}
							}
							if (intColumn == 3) {
								int amount = Integer.parseInt(table.getValueAt(intRows, 3).toString());
								if (functionalityCtr.isProduct()) {
									if (productCtr.isAmountAvailable(amount, product)) {
										if (productCtr.isDiscount(amount, product)) {
											model.setValueAt(product.getPriceForDiscount(),intRows, 2);
										} else {
											model.setValueAt(product.getSellingPrice(),intRows, 2);
										}
									} else {
										product = productCtr.findByBarcode(Integer.parseInt(table.getValueAt(intRows,0).toString()));
										JOptionPane.showMessageDialog(null,
														"The typed amount is not available! \n You have "
										                 + product.getAmount()+ " from "+ product.getName()+ "!",
														"Amount error!",
														JOptionPane.ERROR_MESSAGE);
										table.setValueAt(0,intRows, 3);
									}
								}
							    double pricePerPiece = Double.parseDouble(table.getValueAt(intRows, 2).toString());
								double price = saleCtr.calculatePrice(pricePerPiece, amount);
								model.setValueAt(price, intRows, 4);

								double subtotal = 0;
								for (int rows = 0; rows < table.getRowCount(); rows++) {
									double pricee = Double.parseDouble(model.getValueAt(rows, 4).toString());
									subtotal += pricee;
								}

								int customerId = Integer.parseInt(textFieldCustomer.getText());
								double discount = saleCtr.calculateDiscount(customerId,subtotal);

								double total = saleCtr.calculateTotalPrice(subtotal, discount);
								textFieldPrice.setText(Double.toString(total));
							}
						}
					});

			JButton btnSubmit = new JButton("Submit");
			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int saleId = 0;
					try {
						if (textFieldCustomer.getText().equals("")) {
							JOptionPane
									.showMessageDialog(
											null,
											"You have not entered a customer!",
											"Customer error!",
											JOptionPane.ERROR_MESSAGE);
						} else {
							saleId = saleCtr.insertSale(
									Integer.parseInt(textFieldCustomer
											.getText().toString()),
									Double.parseDouble(textFieldPrice
											.getText().toString()));
							// ic.insertInvoice(saleId);
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}

					table.selectAll();
					int[] vals = table.getSelectedRows();
					for (int i = 0; i < vals.length; i++) {
						for (int x = 0; x < table.getColumnCount(); x++) {
							functionalityCtr.addValue(table
									.getValueAt(i, x).toString());
						}

						try {
							ArrayList<String> values = functionalityCtr
									.getValues();
							saleCtr.insertPartSale(
									saleId,
									Integer.parseInt(values.get(0)),
									values.get(1),
									Double.parseDouble(values
											.get(2)),
									Integer.parseInt(values.get(3)),
									Double.parseDouble(values
											.get(4)));

						} catch (Exception e1) {
							e1.printStackTrace();
						}
						functionalityCtr.removeAllValues();
					}

				}
			});
			btnSubmit.setBounds(738, 355, 89, 23);
			contentPanel.add(btnSubmit);

			contentPanel.invalidate();
			contentPanel.revalidate();
			contentPanel.repaint();
			contentPanel.setVisible(true);

		

	

			
	}
}
