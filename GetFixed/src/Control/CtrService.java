package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrService {
	CtrDepartment departmentCtr = new CtrDepartment();

	/** Creates a new instance of CtrService */
	public CtrService() {

	}

	public ArrayList<Service> findAllServices() {
		IFDBService dbService = new DBService();
		ArrayList<Service> allService = new ArrayList<Service>();
		allService = dbService.findAllServices(false);
		return allService;
	}

	public Service findByName(String name) {
		IFDBService dbService = new DBService();
		return dbService.searchServiceByName(name, true);
	}

	public Service findByBarcode(int barcode) {
		IFDBService dbService = new DBService();
		return dbService.searchServiceByBarcode(barcode, true);
	}
	
	public boolean isAvailable(int barcode){
		if(findByBarcode(barcode) != null){
			return true;
		}else{
			return false;
		}
	}

	public int updateService(int barcode, String name, double price) {
		IFDBService dbService = new DBService();
		Service service = new Service();

		service.setBarcode(barcode);
		service.setName(name);
		service.setPrice(price);
		return dbService.updateService(service);

	}

	public void insertService(String name, double price) throws Exception {
		Service service = new Service();
		service.setName(name);
		service.setPrice(price);
		try {
			DBConnection.startTransaction();
			DBService dbService = new DBService();
			dbService.insertService(service);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Service not inserted");
		}
	}

	public void deleteService(int barcode) throws Exception {

		try {
			DBConnection.startTransaction();
			DBService dbService = new DBService();
			dbService.deleteService(barcode);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Service not deleted");
		}
	}

	

	public ArrayList<Object[]> addAllServices() {
		ArrayList<Service> serviceList = findAllServices();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Service service : serviceList) {
			object = new Object[] { service.getBarcode(), service.getName(),
					service.getPrice()};
			objectArray.add(object);
		}
		return objectArray;
	}

	public Object[] addServiceByBarcode(int barcode) {
		Service service = findByBarcode(barcode);
		Object[] object = null;
		object = new Object[] { service.getBarcode(), service.getName(),
				service.getPrice()};

		return object;
	}

	public Object[] addServiceByName(String name) {
		Service service = findByName(name);
		Object[] object = null;
		object = new Object[] { service.getBarcode(), service.getName(),
				service.getPrice()};

		return object;
	}
}
