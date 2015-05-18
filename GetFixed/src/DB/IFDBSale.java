package DB;
import Model.*;

import java.util.ArrayList;

public interface IFDBSale {

	public ArrayList<Sale> getAllSale(boolean retriveAssociation);

    public Sale searchSaleId( int id, boolean retriveAssociation);

    public int insertSale(Sale sale) throws Exception;

    public int deleteSale(int id);
    
}