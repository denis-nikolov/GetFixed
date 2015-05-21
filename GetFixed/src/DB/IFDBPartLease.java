package DB;
import Model.*;

import java.util.ArrayList;

public interface IFDBPartLease {

	public ArrayList<PartLease> getAllPartLeaseByLeaseId(int saleId, boolean retriveAssociation);

    public int insertPartLease(PartLease part) throws Exception;
    
    public int deletePartLease(int saleId);
}