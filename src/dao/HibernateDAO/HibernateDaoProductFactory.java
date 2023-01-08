/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.HibernateDAO;

import dao.DAOProductFactory;
import dao.ProductDAO;
import java.sql.Connection;

/**
 *
 * @author joanl
 */
public class HibernateDaoProductFactory implements DAOProductFactory{

    @Override
    public ProductDAO createDAO(Connection con) {
        return new HibernateProductDao();
    }
    
}
