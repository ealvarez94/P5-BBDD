/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.HibernateDAO;

import dao.DAOOrderFactory;
import dao.OrderDAO;
import java.sql.Connection;

/**
 *
 * @author joanl
 */
public class HibernateDaoOrderFactory implements DAOOrderFactory{

    @Override
    public OrderDAO createDAO(Connection con) {
        return new HibernateOrderDao();
    }
    
}
