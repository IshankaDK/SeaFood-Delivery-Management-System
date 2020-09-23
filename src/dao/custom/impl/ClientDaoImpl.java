package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ClientDAO;
import entity.Client;
import entity.SuperEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ClientDaoImpl implements ClientDAO {
    @Override
    public boolean save(Client client) throws Exception {
        return CrudUtil.execute("INSERT INTO Client VALUES (?,?,?,?,?)",
                client.getId(),client.getName(),client.getAddress(),client.getContact(),client.getType());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Client WHERE clientId =?",s);
    }

    @Override
    public boolean update(Client client) throws Exception {
        return CrudUtil.execute("UPDATE Client SET clientName = ? , clientAddress = ? , clientContact = ? , clientType = ? WHERE clientId = ? ",
                client.getName(),client.getAddress(),client.getContact(),client.getType(),client.getId());
    }

    @Override
    public ArrayList<Client> getAll() throws Exception {
        ResultSet set =  CrudUtil.execute("SELECT * FROM Client");
        ArrayList<Client> clientArrayList = new ArrayList<>();
        while (set.next()){
            clientArrayList.add(new Client(
                    set.getString(1),set.getString(2),set.getString(3),
                    set.getString(4),set.getString(5)
            ));
        }
        return clientArrayList;
    }
}
