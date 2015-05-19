package UI;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Control.CtrFunctionality;
import Control.CtrSale;
import Model.PartSale;

class ShowItemsInSaleUI extends JFrame {
	private JTable table;
	private CtrFunctionality functionalityCtr;
	private CtrSale saleCtr = new CtrSale();

	public ShowItemsInSaleUI(CtrFunctionality functionalityCtr) {
		this.functionalityCtr = functionalityCtr;
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null, null } }, new String[] { "SaleID", "Barcode",
				"Name", "Price/Pc", "Amount", "Price" }) {
			Class[] columnTypes = new Class[] { Integer.class, Integer.class,
					String.class, Double.class, Integer.class, Double.class };

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

		for (String string : functionalityCtr.getSaleId()) {
			System.out.println(string);

			ArrayList<PartSale> partSaleList = saleCtr
					.findAllPartSalesBySaleId(Integer.parseInt(string));

			System.out.println(partSaleList.size());

			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();

			for (PartSale partSale : partSaleList) {
				try {
					model.addRow(new Object[] { partSale.getSale().getId(),
							partSale.getBarcode(), partSale.getName(),
							partSale.getPricePerPiece(), partSale.getAmount(),
							partSale.getPrice() });
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
