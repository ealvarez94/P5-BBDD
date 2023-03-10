/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.HibernateDAO;

import dao.CustomerDAO;
import dao.IDaoManager;
import dao.OrderDAO;
import dao.ProductDAO;

/**
 *
 * @author joanl
 */
public class HibernateDaoManager implements IDaoManager{

    private ProductDAO productDao = null;
    private CustomerDAO customerDao = null;
    private OrderDAO orderDao = null;

    public HibernateDaoManager() {
        productDao = new HibernateProductDao();
        customerDao = new HibernateCustomerDao();
        orderDao = new HibernateOrderDao();
    }
    
    @Override
    public ProductDAO getProductDAO() {
       return productDao;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return orderDao;
    }

    @Override
    public CustomerDAO getCustomerDAO() {
        return customerDao;
    }
    
}
