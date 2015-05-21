package DB;
import Model.*;

import java.util.ArrayList;

public interface IFDBPartSale {

	public ArrayList<PartSale> getAllPartSaleBySaleId(int saleId, boolean retriveAssociation);

    public int insertPartSale(PartSale part) throws Exception;
    
    public int deletePartSale(int saleId);
}