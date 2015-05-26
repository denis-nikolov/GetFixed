package UI;

import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Control.*;
import Model.*;

public class StartLeaseUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	CtrProduct productCtr = new CtrProduct();
	CtrCustomer customerCtr = new CtrCustomer();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrLease leaseCtr = new CtrLease();

	StartLeaseUI(JPanel contentPanel, JPanel secondaryMenuPanel) {
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;

	}

	void make() {
		functionalityCtr.removeAllIds();
		functionalityCtr.removeAllClicks();

		contentPanel.removeAll();

		table = new JTable();

		table.setFont(new Font("Tahoma", Font.PLAIN, 15));

		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, } }, new String[] {
				"Barcode", "Name", "Price/pc", "Quantity", "Total" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Double.class, Integer.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] canEdit = new boolean[] { true, false, false, true, false };

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

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.addRow(new Object[] { null, null, null, null, null });
			}
		});
		btnAdd.setBounds(625 + functionalityCtr.getAddWidth(), 8, 89, 23);
		contentPanel.add(btnAdd);

		JLabel lblTotalPrice = new JLabel("Total price:");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTotalPrice.setBounds(556 + functionalityCtr.getAddWidth(), 355, 72, 23);
		contentPanel.add(lblTotalPrice);

		JTextField textFieldPrice = new JTextField();
		textFieldPrice.setBounds(638 + functionalityCtr.getAddWidth(), 355, 86, 23);
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
						JOptionPane.showMessageDialog(null, "There is no customer with ID " + id + " !", "ID Error",
								JOptionPane.ERROR_MESSAGE);
						textFieldCustomer.setText("0");
					}
				}
			}
		});
		contentPanel.add(textFieldCustomer);
		textFieldCustomer.setColumns(10);

		JLabel lblPeriod = new JLabel("Period:");
		lblPeriod.setBounds(400, 11, 111, 14);
		contentPanel.add(lblPeriod);

		JTextField textFieldPeriod = new JTextField();
		textFieldPeriod.setBounds(450, 8, 102, 20);
		contentPanel.add(textFieldPeriod);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				for (int i = 0; i < rows.length; i++) {
					if (table.getValueAt(table.getSelectedRow(), 4) != null) {

						double total = Double.parseDouble(textFieldPrice.getText());

						double sum = Double.parseDouble(table.getValueAt(table.getSelectedRow(), 4).toString());
						int customerId = Integer.parseInt(textFieldCustomer.getText());
						double discount = leaseCtr.calculateDiscount(customerId, sum);
						int period = Integer.parseInt(textFieldPeriod.getText());

						total -= leaseCtr.calculateTotalPrice(sum, discount, period);
						textFieldPrice.setText(Double.toString(total));
					}
					model.removeRow(rows[i] - i);
				}
			}
		});
		btnRemove.setBounds(720 + functionalityCtr.getAddWidth(), 8, 105, 23);
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
					String text = textFieldCustomer.getText();
					if (text.length() == 0 || text == null) {
						JOptionPane.showMessageDialog(null, "You have not entered a customer!", "Customer Error",
								JOptionPane.ERROR_MESSAGE);
					}
					int barcode = Integer.parseInt(table.getValueAt(intRows, 0).toString());

					product = productCtr.findByBarcode(barcode);
					if (productCtr.isAvailable(barcode)) {
						if (productCtr.isLeasable(product)) {
							model.setValueAt(product.getName(), intRows, 1);
							model.setValueAt(product.getSellingPrice(), intRows, 2);
							model.setValueAt(0, intRows, 3);
							model.setValueAt(0, intRows, 4);
						} else {
							JOptionPane.showMessageDialog(null, "This product can not be leased!", "Lease error!",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "No product with that barcode!", "Barcode error!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				if (intColumn == 3) {
					int amount = Integer.parseInt(table.getValueAt(intRows, 3).toString());
					if (productCtr.isAmountAvailable(amount, product)) {
						model.setValueAt(product.getLeasingPrice(), intRows, 2);
					} else {
						product = productCtr.findByBarcode(Integer.parseInt(table.getValueAt(intRows, 0).toString()));
						JOptionPane.showMessageDialog(null,
								"The typed amount is not available! \n You have " + product.getAmount() + " from "
										+ product.getName() + "!", "Amount error!", JOptionPane.ERROR_MESSAGE);
						table.setValueAt(0, intRows, 3);
					}

					double pricePerPiece = Double.parseDouble(table.getValueAt(intRows, 2).toString());
					double price = leaseCtr.calculatePrice(pricePerPiece, amount);
					model.setValueAt(price, intRows, 4);

					double subtotal = 0;
					for (int rows = 0; rows < table.getRowCount(); rows++) {
						double pricee = Double.parseDouble(model.getValueAt(rows, 4).toString());
						subtotal += pricee;
					}

					int customerId = Integer.parseInt(textFieldCustomer.getText());
					int period = Integer.parseInt(textFieldPeriod.getText());
					double discount = leaseCtr.calculateDiscount(customerId, subtotal);

					double total = leaseCtr.calculateTotalPrice(subtotal, discount, period);
					textFieldPrice.setText(Double.toString(total));
				}
			}
		});

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int leaseId = 0;
				try {
					if (textFieldCustomer.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "You have not entered a customer!", "Customer error!",
								JOptionPane.ERROR_MESSAGE);
					} else {
						leaseId = leaseCtr.insertLease(Integer.parseInt(textFieldCustomer.getText().toString()),
								Integer.parseInt(textFieldPeriod.getText().toString()),
								functionalityCtr.getDepartmentId(),
								Double.parseDouble(textFieldPrice.getText().toString()));
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				table.selectAll();
				int[] vals = table.getSelectedRows();
				String products = "";
				products += "\n#####################\n";
				products += "Lease ID: " + leaseId + "\nCustomer ID: " + textFieldCustomer.getText();
                products += "\nLease date: " + leaseCtr.findById(leaseId).getDate();
                products += "\nLease period: " + leaseCtr.findById(leaseId).getPeriod();
				products += "\n#####################\n";
				for (int i = 0; i < vals.length; i++) {
					for (int x = 0; x < table.getColumnCount(); x++) {
						functionalityCtr.addValue(table.getValueAt(i, x).toString());
					}

					try {
						ArrayList<String> values = functionalityCtr.getValues();
						leaseCtr.insertPartLease(leaseId, Integer.parseInt(values.get(0)), values.get(1),
								Double.parseDouble(values.get(2)), Integer.parseInt(values.get(3)),
								Double.parseDouble(values.get(4)));
						
						products += "Barcode: " + (values.get(0)) + "     Name: " + values.get(1) + "     Price/pc: "
								+ Double.parseDouble(values.get(2)) + "     Quantity: "
								+ Integer.parseInt(values.get(3)) + "     Total: " + Double.parseDouble(values.get(4))
								+ "\n";

					} catch (Exception e1) {
						e1.printStackTrace();
					}
					functionalityCtr.removeAllValues();
				}
				products += "\n\n";
				products += "Personal discount: "
						+ customerCtr.findById(Integer.parseInt(textFieldCustomer.getText())).getDiscount() + "% \n";
				products += "TOTAL: " + textFieldPrice.getText() + " DKK";
				Lease tryLease = leaseCtr.findById(leaseId);
				if (tryLease != null) {
					JOptionPane.showMessageDialog(null, "Lease submitted!\n" + products, "Lease confirmation!",
							JOptionPane.INFORMATION_MESSAGE);
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
