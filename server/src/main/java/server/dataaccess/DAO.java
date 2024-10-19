package server.dataaccess;

import java.util.Collection;
import java.util.HashSet;

public abstract class DAO {
    protected static DB db = new DB();

    public DAO() {

    }

    public abstract void clear();
}
