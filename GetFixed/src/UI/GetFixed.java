package UI;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.*;

import javax.swing.table.*;

import java.util.*;

import Control.*;
import Model.*;

public class GetFixed extends JFrame {

	private JPanel contentPane;
	private JPanel mainMenuPanel;
	private JPanel secondaryMenuPanel;
	private JPanel contentPanel;
	private JTable table;
	private JTextField txtSearch;
	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrSupplier supplierCtr = new CtrSupplier();
	CtrEmployee employeeCtr = new CtrEmployee();
	CtrCustomer customerCtr = new CtrCustomer();
	CtrService serviceCtr = new CtrService();
	CtrFunctionality functionalityCtr = new CtrFunctionality();
	CtrSale saleCtr = new CtrSale();
	CtrOrder orderCtr = new CtrOrder();
	Department department = new Department();
	ArrayList<String> saleID = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetFixed frame = new GetFixed();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GetFixed() {
		setResizable(false);
		setTitle("GetFixed");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 864, 570);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		mainMenuPanel = new JPanel();
		mainMenuPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		mainMenuPanel.setBounds(10, 11, 838, 36);
		contentPane.add(mainMenuPanel);
		mainMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		contentPanel = new JPanel();
		contentPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		contentPanel.setBounds(10, 105, 838, 414);
		contentPane.add(contentPanel);
		contentPanel.setLayout(null);

		secondaryMenuPanel = new JPanel();
		secondaryMenuPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		secondaryMenuPanel.setBounds(10, 58, 838, 36);
		contentPane.add(secondaryMenuPanel);
		secondaryMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////SALES/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnSales = new JButton("Sales");
		mainMenuPanel.add(btnSales);
		btnSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondaryMenuPanel.removeAll();
				contentPanel.removeAll();

				JButton btnMakeASale = new JButton("Create sale");
				secondaryMenuPanel.add(btnMakeASale);
				btnMakeASale.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();
						CreateSaleUI createSaleUi = new CreateSaleUI(
								contentPanel, secondaryMenuPanel);
						createSaleUi.make();
					}
				});

				JButton btnShowSale = new JButton("Show sale");
				secondaryMenuPanel.add(btnShowSale);
				btnShowSale.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();
						ShowSaleUI showSaleUi = new ShowSaleUI(contentPanel,
								secondaryMenuPanel, btnShowSale);
						showSaleUi.make();

					}
				});
				invalidate();
				revalidate();
				repaint();
				setVisible(true);

			}
		});
		mainMenuPanel.add(btnSales);

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////LEASES/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnLeases = new JButton("Leases");
		btnLeases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				secondaryMenuPanel.removeAll();

				JButton btnStartLease = new JButton("Start lease");
				secondaryMenuPanel.add(btnStartLease);

				JButton btnShowLease = new JButton("Show lease");
				secondaryMenuPanel.add(btnShowLease);

				JButton btnEndLease = new JButton("End lease");
				secondaryMenuPanel.add(btnEndLease);

				invalidate();
				revalidate();
				repaint();
				setVisible(true);

			}
		});
		mainMenuPanel.add(btnLeases);

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////ORDERS/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnOrders = new JButton("Orders");
		btnOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondaryMenuPanel.removeAll();

				JButton btnCreateOrder = new JButton("Create order");
				secondaryMenuPanel.add(btnCreateOrder);

				JButton btnShowOrder = new JButton("Show order");
				secondaryMenuPanel.add(btnShowOrder);
				btnShowOrder.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();

						ShowOrderUI showOrderUi = new ShowOrderUI(contentPanel,
								secondaryMenuPanel);
						showOrderUi.make();
					}
				});
				invalidate();
				revalidate();
				repaint();
				setVisible(true);
			}
		});
		mainMenuPanel.add(btnOrders);

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////PRODUCTS/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnProducts = new JButton("Products");
		btnProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondaryMenuPanel.removeAll();

				JButton btnAddProduct = new JButton("Add product");
				secondaryMenuPanel.add(btnAddProduct);
				btnAddProduct.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						contentPanel.removeAll();

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
										.findByLocation(rdbtnAalborg.getText());
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
										.findByLocation(rdbtnAarhus.getText());
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
										.findByLocation(rdbtnOdense.getText());
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
										.findByLocation(rdbtnCopenhagen
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
								null, null, null, null, null, null, null, null,
								null, null, 0, null } }, new String[] {
								"Barcode", "Name", "Selling price",
								"Leasing price", "Ordering price",
								"Price discount", "Amount discount", "Amount",
								"Min amount", "Max amount", "Ordered Amount",
								"Supplier ID" }) {
							Class[] columnTypes = new Class[] { Integer.class,
									String.class, Float.class, Float.class,
									Float.class, Float.class, Integer.class,
									Integer.class, Integer.class,
									Integer.class, Integer.class, Integer.class };

							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}

							boolean[] canEdit = new boolean[] { true, true,
									true, true, true, true, true, true, true,
									true, false, true };

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
										null, null, null, null, null, null,
										null, 0, null });
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
										values.add(table.getValueAt(i, x)
												.toString());
									}

									try {

										for (Integer integer : functionalityCtr
												.getIds()) {
											productCtr.insertProduct(Integer
													.parseInt(values.get(0)),
													values.get(1), Double
															.parseDouble(values
																	.get(2)),
													Double.parseDouble(values
															.get(3)), Double
															.parseDouble(values
																	.get(4)),
													Double.parseDouble(values
															.get(5)), Integer
															.parseInt(values
																	.get(6)),
													Integer.parseInt(values
															.get(7)), Integer
															.parseInt(values
																	.get(8)),
													Integer.parseInt(values
															.get(9)), Integer
															.parseInt(values
																	.get(10)),
													integer, Integer
															.parseInt(values
																	.get(11)));
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

						invalidate();
						revalidate();
						repaint();
						setVisible(true);

					}
				});

				JButton btnShowProducts = new JButton("Show product");
				secondaryMenuPanel.add(btnShowProducts);
				btnShowProducts.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();

						functionalityCtr.removeAllIds();
						functionalityCtr.removeAllClicks();

						table = new JTable();
						table.setFont(new Font("Tahoma", Font.PLAIN, 15));
						table.setModel(new DefaultTableModel(new Object[][] { {
								null, null, null, null, null, null, null, null,
								null, null, null, null, null } }, new String[] {
								"Barcode", "Name", "Selling price",
								"Leasing price", "Ordering price",
								"Price discount", "Amount discount", "Amount",
								"Min amount", "Max amount", "Ordered Amount",
								"Department", "Supplier ID" }) {
							Class[] columnTypes = new Class[] { Integer.class,
									String.class, Float.class, Float.class,
									Float.class, Float.class, Integer.class,
									Integer.class, Integer.class,
									Integer.class, Integer.class, String.class,
									Integer.class };

							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}

							boolean[] canEdit = new boolean[] { false, true,
									true, true, true, true, true, false, true,
									true, false, false, true };

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

						JButton btnRemove = new JButton("Remove row");
						btnRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int[] rows = table.getSelectedRows();
								for (int index = 0; index < rows.length; index++) {
									model.removeRow(rows[index] - index);
								}
							}
						});
						btnRemove.setBounds(720, 11, 105, 23);
						contentPanel.add(btnRemove);

						model.removeRow(0);

						for (Object[] object : productCtr.addAllProducts()) {
							model.addRow(object);
						}

						JRadioButton rdbtnAalborg = new JRadioButton("Aalborg");
						rdbtnAalborg.setBounds(222, 11, 109, 23);
						rdbtnAalborg.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								department = departmentCtr
										.findByLocation(rdbtnAalborg.getText());
								int departmentId = department.getId();

								if (rdbtnAalborg.isSelected()) {
									functionalityCtr.addId(departmentId);
									functionalityCtr.addClick();

									if (functionalityCtr.getClicks() <= 1) {
										model.getDataVector()
												.removeAllElements();
										model.fireTableDataChanged();
									}

									for (Object[] object : productCtr
											.addAllProductsForDepartment(departmentId)) {
										model.addRow(object);
									}
								} else {
									functionalityCtr.removeId(departmentId);
									for (int rows = 0; rows < table
											.getRowCount(); rows++) {
										ArrayList<String> values = new ArrayList<>();
										for (int columns = 0; columns < table
												.getColumnCount(); columns++) {
											values.add(table.getValueAt(rows,
													columns).toString());
										}
										String departmentName = values.get(11);
										values.clear();
										if (departmentName.equals(rdbtnAalborg
												.getText())) {
											model.removeRow(rows);
											rows--;
										}
									}
								}
							}
						});
						contentPanel.add(rdbtnAalborg);

						JRadioButton rdbtnAarhus = new JRadioButton("Aarhus");
						rdbtnAarhus.setBounds(333, 11, 109, 23);
						rdbtnAarhus.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								department = departmentCtr
										.findByLocation(rdbtnAarhus.getText());
								int departmentId = department.getId();

								if (rdbtnAarhus.isSelected()) {
									functionalityCtr.addId(departmentId);
									functionalityCtr.addClick();

									if (functionalityCtr.getClicks() <= 1) {
										model.getDataVector()
												.removeAllElements();
										model.fireTableDataChanged();
									}

									for (Object[] object : productCtr
											.addAllProductsForDepartment(departmentId)) {
										model.addRow(object);
									}
								} else {
									functionalityCtr.removeId(departmentId);
									for (int rows = 0; rows < table
											.getRowCount(); rows++) {
										ArrayList<String> values = new ArrayList<>();
										for (int columns = 0; columns < table
												.getColumnCount(); columns++) {
											values.add(table.getValueAt(rows,
													columns).toString());
										}
										String departmentName = values.get(11);
										values.clear();
										if (departmentName.equals(rdbtnAarhus
												.getText())) {
											model.removeRow(rows);
											rows--;
										}
									}
								}
							}
						});
						contentPanel.add(rdbtnAarhus);

						JRadioButton rdbtnOdense = new JRadioButton("Odense");
						rdbtnOdense.setBounds(444, 11, 109, 23);
						rdbtnOdense.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								department = departmentCtr
										.findByLocation(rdbtnOdense.getText());
								int departmentId = department.getId();

								if (rdbtnOdense.isSelected()) {
									functionalityCtr.addId(departmentId);
									functionalityCtr.addClick();

									if (functionalityCtr.getClicks() <= 1) {
										model.getDataVector()
												.removeAllElements();
										model.fireTableDataChanged();
									}

									for (Object[] object : productCtr
											.addAllProductsForDepartment(departmentId)) {
										model.addRow(object);
									}
								} else {
									for (int rows = 0; rows < table
											.getRowCount(); rows++) {
										ArrayList<String> values = new ArrayList<>();
										for (int columns = 0; columns < table
												.getColumnCount(); columns++) {
											values.add(table.getValueAt(rows,
													columns).toString());
										}
										String departmentName = values.get(11);
										values.clear();
										if (departmentName.equals(rdbtnOdense
												.getText())) {
											model.removeRow(rows);
											rows--;
										}
									}
								}
							}
						});
						contentPanel.add(rdbtnOdense);

						JRadioButton rdbtnCopenhagen = new JRadioButton(
								"Copenhagen");
						rdbtnCopenhagen.setBounds(555, 11, 109, 23);
						rdbtnCopenhagen.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								department = departmentCtr
										.findByLocation(rdbtnCopenhagen
												.getText());
								int departmentId = department.getId();

								if (rdbtnCopenhagen.isSelected()) {
									functionalityCtr.addId(departmentId);
									functionalityCtr.addClick();

									if (functionalityCtr.getClicks() <= 1) {
										model.getDataVector()
												.removeAllElements();
										model.fireTableDataChanged();
									}

									for (Object[] object : productCtr
											.addAllProductsForDepartment(departmentId)) {
										model.addRow(object);
									}
								} else {
									for (int rows = 0; rows < table
											.getRowCount(); rows++) {
										ArrayList<String> values = new ArrayList<>();
										for (int columns = 0; columns < table
												.getColumnCount(); columns++) {
											values.add(table.getValueAt(rows,
													columns).toString());
										}
										String departmentName = values.get(11);
										values.clear();
										if (departmentName
												.equals(rdbtnCopenhagen
														.getText())) {
											model.removeRow(rows);
											rows--;
										}
									}
								}
							}
						});
						contentPanel.add(rdbtnCopenhagen);

						txtSearch = new JTextField();
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
									model.addRow(productCtr
											.addProductByBarcode(Integer
													.parseInt(search)));
								} else {
									model.addRow(productCtr
											.addProductByName(search));
								}

								txtSearch.setText("");
							}
						});
						contentPanel.add(btnSearch);

						JButton btnUpdate = new JButton("Update");
						btnUpdate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int[] vals = table.getSelectedRows();
								for (int i : vals) {
									ArrayList<String> values = new ArrayList<>();
									for (int x = 0; x < table.getColumnCount(); x++) {
										values.add(table.getValueAt(i, x)
												.toString());

									}
									productCtr.updateProduct(
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
											Integer.parseInt(values.get(10)),
											Integer.parseInt(values.get(12)));
								}
							}
						});
						btnUpdate.setBounds(639, 380, 89, 23);
						contentPanel.add(btnUpdate);

						JButton btnDelete = new JButton("Delete");
						btnDelete.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								int[] vals = table.getSelectedRows();
								boolean flag = true;
								for (int i : vals) {

									try {
										Product product = productCtr
												.findByBarcodeAndDepartmentLocation(
														Integer.parseInt(table
																.getValueAt(i,
																		0)
																.toString()),
														table.getValueAt(i, 11)
																.toString());
										int barcode = product.getBarcode();
										int departmentId = product
												.getDepartment().getId();
										productCtr.deleteProduct(barcode,
												departmentId);
									} catch (Exception e1) {
										e1.printStackTrace();
										flag = false;
									}

								}
								btnShowProducts.doClick();
							}

						});
						btnDelete.setBounds(739, 380, 89, 23);
						contentPanel.add(btnDelete);

						invalidate();
						revalidate();
						repaint();
						setVisible(true);

					}
				});

				invalidate();
				revalidate();
				repaint();
				setVisible(true);

			}
		});
		mainMenuPanel.add(btnProducts);

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////SERVICES/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnServices = new JButton("Services");
		btnServices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				secondaryMenuPanel.removeAll();

				JButton btnAddService = new JButton("Add service");
				secondaryMenuPanel.add(btnAddService);
				btnAddService.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();
						AddServiceUI addServiceUI = new AddServiceUI(contentPanel, secondaryMenuPanel);
						addServiceUI.make();
					}
				});

				JButton btnShowService = new JButton("Show service");
				secondaryMenuPanel.add(btnShowService);
				btnShowService.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();
						ShowServiceUI showServiceUI = new ShowServiceUI(contentPanel, secondaryMenuPanel, btnShowService);
						showServiceUI.make();
					}
				});
				invalidate();
				revalidate();
				repaint();
				setVisible(true);
			}
		});
		mainMenuPanel.add(btnServices);

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////CUSTOMERS/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnCustomers = new JButton("Customers");
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				secondaryMenuPanel.removeAll();

				JButton btnAddCustomer = new JButton("Add customer");
				secondaryMenuPanel.add(btnAddCustomer);
				btnAddCustomer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						contentPanel.removeAll();

						functionalityCtr.removeAllIds();
						functionalityCtr.removeAllClicks();

						table = new JTable();
						table.setFont(new Font("Tahoma", Font.PLAIN, 15));
						table.setModel(new DefaultTableModel(new Object[][] { {
								null, null, null, null, null } }, new String[] {
								"Name", "Surname", "Telephone", "E-mail",
								"Discount(%)" }) {
							Class[] columnTypes = new Class[] { String.class,
									String.class, String.class, String.class,
									Integer.class };

							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}

							boolean[] canEdit = new boolean[] { true, true,
									true, true, true };

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
								//
								model.addRow(new Object[] { null, null, null,
										null, null });
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
									for (int x = 0; x < table.getColumnCount(); x++) {
										values.add(table.getValueAt(i, x)
												.toString());
									}

									try {
										customerCtr.insertCustomer(
												values.get(0), values.get(1),
												values.get(2), values.get(3),
												Integer.parseInt(values.get(4)));

									} catch (Exception e1) {
										e1.printStackTrace();
									}
									values.clear();
								}

							}
						});
						btnSubmit.setBounds(739, 380, 89, 23);
						contentPanel.add(btnSubmit);

						invalidate();
						revalidate();
						repaint();
						setVisible(true);

					}
				});

				JButton btnShowCustomer = new JButton("Show customer");
				secondaryMenuPanel.add(btnShowCustomer);
				btnShowCustomer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						contentPanel.removeAll();

						functionalityCtr.removeAllIds();
						functionalityCtr.removeAllClicks();

						table = new JTable();
						table.setFont(new Font("Tahoma", Font.PLAIN, 15));
						table.setModel(new DefaultTableModel(new Object[][] { {
								null, null, null, null, null, null } },
								new String[] { "ID", "Name", "Surname",
										"Telephone", "E-mail", "Discount(%)" }) {
							Class[] columnTypes = new Class[] { Integer.class,
									String.class, String.class, String.class,
									String.class, Integer.class };

							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}

							boolean[] canEdit = new boolean[] { false, true,
									true, true, true, true };

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

						JButton btnRemove = new JButton("Remove row");
						btnRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int[] rows = table.getSelectedRows();
								for (int index = 0; index < rows.length; index++) {
									model.removeRow(rows[index] - index);
								}
							}
						});
						btnRemove.setBounds(720, 11, 105, 23);
						contentPanel.add(btnRemove);

						model.removeRow(0);

						for (Object[] object : customerCtr.addAllCustomers()) {
							model.addRow(object);
						}

						txtSearch = new JTextField();
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
									model.addRow(customerCtr
											.addCustomerById(Integer
													.parseInt(search)));
								} else {
									model.addRow(customerCtr
											.addCustomerByName(search));
								}

								txtSearch.setText("");
							}
						});
						contentPanel.add(btnSearch);

						JButton btnUpdate = new JButton("Update");
						btnUpdate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int[] vals = table.getSelectedRows();
								for (int i : vals) {
									ArrayList<String> values = new ArrayList<>();
									for (int x = 0; x < table.getColumnCount(); x++) {
										values.add(table.getValueAt(i, x)
												.toString());

									}
									customerCtr.updateCustomer(
											Integer.parseInt(values.get(0)),
											values.get(1), values.get(2),
											values.get(3), values.get(4),
											Integer.parseInt(values.get(5)));
								}
							}
						});
						btnUpdate.setBounds(639, 380, 89, 23);
						contentPanel.add(btnUpdate);

						JButton btnDelete = new JButton("Delete");
						btnDelete.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								int[] vals = table.getSelectedRows();
								boolean flag = true;
								for (int i : vals) {

									try {
										Customer customer = customerCtr
												.findById(Integer
														.parseInt(table
																.getValueAt(i,
																		0)
																.toString()));
										int id = customer.getId();
										customerCtr.deleteCustomer(id);
									} catch (Exception e1) {
										e1.printStackTrace();
										flag = false;
									}

								}
								btnShowCustomer.doClick();
							}

						});
						btnDelete.setBounds(739, 380, 89, 23);
						contentPanel.add(btnDelete);

						invalidate();
						revalidate();
						repaint();
						setVisible(true);

					}
				});

				invalidate();
				revalidate();
				repaint();
				setVisible(true);
			}
		});
		mainMenuPanel.add(btnCustomers);

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////EMOLOYEES/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnEmployees = new JButton("Employees ");
		btnEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				secondaryMenuPanel.removeAll();

				JButton btnAddEmployee = new JButton("Add employee");
				secondaryMenuPanel.add(btnAddEmployee);
				btnAddEmployee.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();
						
						AddEmployeeUI addEmployeeUI = new AddEmployeeUI(contentPanel, secondaryMenuPanel);
						addEmployeeUI.main();
					}
				});

				JButton btnShowEmployee = new JButton("Show employee");
				secondaryMenuPanel.add(btnShowEmployee);
				btnShowEmployee.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPanel.removeAll();
						ShowEmployeeUI showEmployeeUI = new ShowEmployeeUI(contentPanel, secondaryMenuPanel, btnAddEmployee);
						showEmployeeUI.make();
						
						
					}
				});

				invalidate();
				revalidate();
				repaint();
				setVisible(true);
			}
		});
		mainMenuPanel.add(btnEmployees);

		// ////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////
		// /////////////////STATISTICS/////////////////////////////////
		// ///////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////

		JButton btnStatistics = new JButton("Statictics");
		mainMenuPanel.add(btnStatistics);

		JButton btnLogout = new JButton("Logout");
		mainMenuPanel.add(btnLogout);

	}

	class Table extends JFrame {
		private JTable table;

		public Table() {
			table = new JTable();
			table.setFont(new Font("Tahoma", Font.PLAIN, 15));
			table.setModel(new DefaultTableModel(new Object[][] { { null, null,
					null, null, null, null } }, new String[] { "SaleID",
					"Barcode", "Name", "Price/Pc", "Amount", "Price" }) {
				Class[] columnTypes = new Class[] { Integer.class,
						Integer.class, String.class, Double.class,
						Integer.class, Double.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

				boolean[] canEdit = new boolean[] { false, false, false, false,
						false, false };

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(10, 52, 310, 150);
			table.setFillsViewportHeight(true);
			add(scrollPane);
			createPopupMenu();
			DefaultTableModel model = (DefaultTableModel) table.getModel();

			model.removeRow(0);

			for (String string : saleID) {
				ArrayList<PartSale> partSaleList = saleCtr
						.findAllPartSalesBySaleId(Integer.parseInt(string));

				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();

				for (PartSale partSale : partSaleList) {
					try {
						model.addRow(new Object[] { partSale.getSale().getId(),
								partSale.getBarcode(), partSale.getName(),
								partSale.getPricePerPiece(),
								partSale.getAmount(), partSale.getPrice() });
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		private void createPopupMenu() {
			JPopupMenu popup = new JPopupMenu();
			JMenuItem myMenuItem1 = new JMenuItem("cccccccccccccccccccccc");
			JMenuItem myMenuItem2 = new JMenuItem("bbbbbbbbbbbbbbbbbbbbbb");
			popup.add(myMenuItem1);
			popup.add(myMenuItem2);
			MouseListener popupListener = new PopupListener(popup);
			table.addMouseListener(popupListener);
		}

		private class PopupListener extends MouseAdapter {

			private JPopupMenu popup;

			PopupListener(JPopupMenu popupMenu) {
				popup = popupMenu;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					maybeShowPopup(e);
				}
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}
}
