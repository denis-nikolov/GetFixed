package DB;
import Model.*;

import java.util.ArrayList;

public interface IFDBLease {

	public ArrayList<Lease> getAllLease(boolean retriveAssociation);

    public Lease searchLeaseId( int id, boolean retriveAssociation);

    public int insertLease(Lease lease) throws Exception;
    
    public int endLease(Lease lease);
    
    public int deleteLease(int id);
}