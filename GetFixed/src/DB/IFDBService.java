package DB;

import Model.*;

import java.util.ArrayList;

public interface IFDBService {
	public ArrayList<Service> findAllServices(boolean retriveAssociation);

	public Service searchServiceByName(String name, boolean retriveAssociation);

	public Service searchServiceByBarcode(int barcode, boolean retriveAssociation);

	public int insertService(Service ser) throws Exception;

	public int updateService(Service ser);

	public int deleteService(int barcode);

}