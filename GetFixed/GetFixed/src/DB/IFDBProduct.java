package DB;

import Model.*;

import java.util.ArrayList;

public interface IFDBProduct {
	public ArrayList<Product> findAllProducts(boolean retriveAssociation);
	
	public ArrayList<Product> findAllProductsByDepartmentId(int departmentId, boolean retriveAssociation);

	public Product searchProductByName(String name, boolean retriveAssociation);

	public Product searchProductByBarcode(int id, boolean retriveAssociation);
	
	public Product searchProductByBarcodeAndDepartmentId(int barcode, int deparmentId, boolean retriveAssociation);
	
	public int recalculateProductAmount(Product pro);

	public int insertProduct(Product pro) throws Exception;

	public int updateProduct(Product pro);

	public int deleteProduct(int id, int departmentId);

}
