package dao.MySqlDao;

import dao.DAOOrderFactory;
import dao.OrderDAO;
import java.sql.Connection;

public class MySQLDaoOrderFactory implements DAOOrderFactory{

    @Override
    public OrderDAO createDAO(Connection con) {
        
        return new MySQLOrderDAO(con);
    }
    
}
