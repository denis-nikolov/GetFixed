package DB;

import Model.*;

import java.util.ArrayList;

public interface IFDBSupplier {

	public ArrayList<Supplier> getAllSupplier(boolean retriveAssociation);

	public Supplier searchSupplierByName(String name, boolean retriveAssociation);

	public Supplier searchSupplierById(int id, boolean retriveAssociation);

	public int insertSupplier(Supplier sup) throws Exception;

	public int updateSupplier(Supplier sup);

	public int deleteSupplier(int id);
}