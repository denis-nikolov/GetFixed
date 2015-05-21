package Control;

import Model.*;
import DB.*;

import java.text.*;
import java.util.*;

public class CtrLease {

	CtrCustomer customerCtr = new CtrCustomer();
	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();

	/** Creates a new instance of CtrLease */
	public CtrLease() {

	}

	public ArrayList<Lease> findAllLeases() {
		IFDBLease dbLease = new DBLease();
		ArrayList<Lease> allLease = new ArrayList<Lease>();
		allLease = dbLease.getAllLease(false);
		return allLease;
	}

	public ArrayList<Lease> findAllLeasesByDepartmentId(int departmentId) {
		IFDBLease dbLease = new DBLease();
		ArrayList<Lease> allLease = new ArrayList<Lease>();
		allLease = dbLease.findAllLeasesByDepartmentId(departmentId, false);
		return allLease;
	}

	public Lease findById(int id) {
		IFDBLease dbLease = new DBLease();
		return dbLease.searchLeaseId(id, true);
	}

	public int insertLease(int customerId, int period, int departmentId, double price) throws Exception {
		Lease lease = new Lease();
		lease.setCustomer(customerCtr.findById(customerId));
		lease.setPeriod(period);
		lease.setDepartment(departmentCtr.findById(departmentId));
		lease.setPrice(price);

		try {
			DBConnection.startTransaction();
			DBLease dbLease = new DBLease();
			int id = dbLease.insertLease(lease);
			DBConnection.commitTransaction();
			return id;
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Lease not inserted");
		}
	}

	public double calculatePrice(double pricePerPiece, int amount) {
		double price = 0;
		price = pricePerPiece * amount;
		return price;
	}

	public double calculateDiscount(int customerId, double subTotal) {
		double discount = 0;
		int percent = customerCtr.findById(customerId).getDiscount();
		discount = (subTotal * percent) / 100;
		return discount;
	}

	public double calculateTotalPrice(double subTotal, double discount, int period) {
		double total = 0;
		total = (subTotal - discount) * period;
		return total;
	}

	public int endLease(int id) {
		IFDBLease dbLease = new DBLease();
		Lease lease = findById(id);

		ArrayList<PartLease> part = findAllPartLeasesByLeaseId(id);
		System.out.println("FindAll: " + findAllPartLeasesByLeaseId(id));
		System.out.println("Size: " + part.size());
		for (PartLease partLease : part) {
			productCtr.returnProduct(partLease.getBarcode(), partLease.getAmount(), lease.getDepartment().getId());
		}

		lease.setReturned(true);
		return dbLease.endLease(lease);
	}

	public boolean onTime(int id) {
		Lease lease = findById(id);
		try {
			String dateOfLease = lease.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(dateOfLease));

			calendar.add(Calendar.DATE, lease.getPeriod());
			dateOfLease = sdf.format(calendar.getTime());

			Date currentDate = new Date();
			Date date = sdf.parse(dateOfLease);

			if (date.before(currentDate)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void deleteLease(int id) throws Exception {
		System.out.println("Lease ID: " + id);
		
		Lease lease = findById(id);
		
		System.out.println("Returned: " + lease.isReturned());
		
		if (!lease.isReturned()) {
			ArrayList<PartLease> part = findAllPartLeasesByLeaseId(id);
			System.out.println("FindAll: " + findAllPartLeasesByLeaseId(id));
			System.out.println("Size: " + part.size());
			for (PartLease partLease : part) {
				System.out.println("Barcode: " + partLease.getBarcode() + " Amount: " + partLease.getAmount()
						+ " DepartmentId: " + lease.getDepartment().getId());
				productCtr.returnProduct(partLease.getBarcode(), partLease.getAmount(), lease.getDepartment().getId());
			}
		}
		try {
			DBConnection.startTransaction();
			DBLease dbLease = new DBLease();
			dbLease.deleteLease(id);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Lease not deleted");
		}
	}

	public ArrayList<PartLease> findAllPartLeasesByLeaseId(int leaseId) {
		IFDBPartLease dbPartLease = new DBPartLease();
		ArrayList<PartLease> allPartLease = new ArrayList<PartLease>();
		allPartLease = dbPartLease.getAllPartLeaseByLeaseId(leaseId, false);
		return allPartLease;
	}

	public void insertPartLease(int leaseId, int barcode, String name, double pricePerPiece, int amount, double price)
			throws Exception {
		PartLease partLease = new PartLease();
		Lease lease = findById(leaseId);
		int departmentId = lease.getDepartment().getId();
		System.out.println(departmentId);
		partLease.setLease(findById(leaseId));
		partLease.setBarcode(barcode);
		partLease.setName(name);
		partLease.setPricePerPiece(pricePerPiece);
		partLease.setAmount(amount);
		partLease.setPrice(price);

		try {
			DBConnection.startTransaction();
			DBPartLease dbPartLease = new DBPartLease();
			dbPartLease.insertPartLease(partLease);
			productCtr.recalculateProAmount(barcode, amount, departmentId);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("PartLease not inserted");
		}
	}

	public void deletePartLease(int leaseId) throws Exception {
		try {
			DBConnection.startTransaction();
			DBPartLease dbPartLease = new DBPartLease();
			dbPartLease.deletePartLease(leaseId);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("PartLease not deleted");
		}
	}

	public ArrayList<Object[]> addAllLeases() {
		ArrayList<Lease> leaseList = findAllLeases();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Lease lease : leaseList) {
			object = new Object[] { lease.getId(), lease.getDate(), lease.getCustomer().getId(), lease.getPeriod(),
					lease.isReturned(), lease.getDepartment().getName(), lease.getPrice() };
			objectArray.add(object);
		}
		return objectArray;
	}

	public Object[] addLeaseById(int id) {
		Lease lease = findById(id);
		Object[] object = null;
		object = new Object[] { lease.getId(), lease.getDate(), lease.getCustomer().getId(), lease.getPeriod(),
				lease.isReturned(), lease.getDepartment().getName(), lease.getPrice() };

		return object;
	}

	public ArrayList<Object[]> addAllUnreturnedLeases() {
		ArrayList<Lease> leaseList = findAllLeases();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Lease lease : leaseList) {
			if (!lease.isReturned()) {
				object = new Object[] { lease.getId(), lease.getDate(), lease.getCustomer().getId(), lease.getPeriod(),
						lease.isReturned(), lease.getDepartment().getName(), lease.getPrice() };
				objectArray.add(object);
			}
		}
		return objectArray;
	}

	public ArrayList<Object[]> addAllLeasesForDepartment(int departmentId) {
		ArrayList<Lease> leaseList = findAllLeasesByDepartmentId(departmentId);
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Lease lease : leaseList) {
			object = new Object[] { lease.getId(), lease.getDate(), lease.getCustomer().getId(), lease.getPeriod(),
					lease.isReturned(), lease.getDepartment().getName(), lease.getPrice() };
			objectArray.add(object);
		}
		return objectArray;
	}
}
