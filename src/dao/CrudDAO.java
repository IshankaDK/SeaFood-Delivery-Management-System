package dao;

import entity.Client;
import entity.SuperEntity;

import java.util.ArrayList;

public interface CrudDAO<T extends SuperEntity,ID> extends SuperDAO {
    public boolean save(T t) throws Exception;
    public boolean delete(ID id) throws Exception;
    public boolean update(T t) throws Exception;
    public T get(ID id) throws Exception;
    public ArrayList<T> getAll() throws Exception;
}
