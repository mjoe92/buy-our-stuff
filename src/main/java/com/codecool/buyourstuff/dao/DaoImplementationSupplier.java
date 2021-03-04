package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.dao.implementation.database.*;
import com.codecool.buyourstuff.dao.implementation.mem.*;

public class DaoImplementationSupplier {

    private final ProductDao productDao;
    private final ProductCategoryDao productCategoryDao;
    private final SupplierDao supplierDao;
    private final CartDao cartDao;
    private final LineItemDao lineItemDao;
    private final UserDao userDao;

    private DaoImplementationSupplier(ProductDao productDao,
                                      ProductCategoryDao productCategoryDao,
                                      SupplierDao supplierDao,
                                      CartDao cartDao,
                                      LineItemDao lineItemDao,
                                      UserDao userDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        this.cartDao = cartDao;
        this.lineItemDao = lineItemDao;
        this.userDao = userDao;
    }

    public static DaoImplementationSupplier getInstance(DaoType daoType) {
        printImplementation(daoType);
        switch (daoType) {
            case FILE: //TODO: return the DAO implementations that work with files
            case DATABASE:
                DatabaseConnectionDao databaseConnectionDao = new DatabaseConnectionDaoJDBC();
                return new DaoImplementationSupplier(
                        new ProductDaoJDBC(databaseConnectionDao.getConnection()),
                        new ProductCategoryDaoJDBC(databaseConnectionDao.getConnection()),
                        new SupplierDaoJDBC(databaseConnectionDao.getConnection()),
                        new CartDaoJDBC(databaseConnectionDao.getConnection()),
                        new LineItemDaoJDBC(databaseConnectionDao.getConnection()),
                        new UserDaoJDBC(databaseConnectionDao.getConnection())
                );
            case MEMORY:
            default:
                return new DaoImplementationSupplier(
                    new ProductDaoMem(),
                    new ProductCategoryDaoMem(),
                    new SupplierDaoMem(),
                    new CartDaoMem(),
                    new LineItemDaoMem(),
                    new UserDaoMem()
                );
        }
    }

    private static void printImplementation(DaoType daoType) {
        System.out.printf("--- Using %s DAO implementations ---\n", daoType);
    }

    public ProductDao getProductDao() {
        return productDao;
    }
    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }
    public SupplierDao getSupplierDao() {
        return supplierDao;
    }
    public CartDao getCartDao() {
        return cartDao;
    }
    public LineItemDao getLineItemDao() {
        return lineItemDao;
    }
    public UserDao getUserDao() {
        return userDao;
    }
}
