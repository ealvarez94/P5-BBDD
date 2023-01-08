package dao.MySqlDao;

import dao.CustomerDAO;
import dao.DAOCustomerFactory;
import java.sql.Connection;

public class MySQLDaoCustomerFactory implements DAOCustomerFactory {

    @Override
    public CustomerDAO createDAO(Connection con) {
        
        return new MySQLCustomerDAO(con);
    }
    
}
