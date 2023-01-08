package dao.MySqlDao;

import dao.DAOProductFactory;
import dao.ProductDAO;
import java.sql.Connection;


public class MySQLDaoProductFactory implements DAOProductFactory {

    @Override
    public ProductDAO createDAO(Connection con) {

        return new MySQLProductDAO(con);
    }
}
