package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.dao.ProductDao;
import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {

    private final DataSource dataSource;
    private final ProductCategoryDao productCategoryDao;
    private final SupplierDao supplierDao;

    public ProductDaoJDBC(DataSource dataSource, ProductCategoryDao productCategoryDao, SupplierDao supplierDao) {
        this.dataSource = dataSource;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        createTable();
//        addTableConstraint();
    }

    @Override
    public void createTable() {
         String sql = "CREATE SCHEMA IF NOT EXISTS public;";
         try (Connection connection = dataSource.getConnection()) {
             connection.prepareStatement(sql).execute();
             sql = "CREATE TABLE IF NOT EXISTS public.product(" +
                     "id SERIAL PRIMARY KEY, " +
                     "name TEXT NOT NULL, " +
                     "description TEXT NOT NULL, " +
                     "default_price DECIMAL, " +
                     "default_currency_code TEXT, " +
                     "product_category_id INTEGER, " +
                     "supplier_id INTEGER);";
             connection.prepareStatement(sql).execute();
         } catch (SQLException sqle) {
             throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
         }
    }

    public void addTableConstraint() {
        String sqlDrop1 = "ALTER TABLE ONLY product" +
                "DROP CONSTRAINT IF EXISTS product_category_product;";
        String sqlDrop2 = "ALTER TABLE ONLY product" +
                "DROP CONSTRAINT IF EXISTS supplier_product;";
        String sqlAdd1 = "ALTER TABLE product" +
                "ADD CONSTRAINT product_category_product FOREIGN KEY (product_category_id) REFERENCES product_category(id);";
        String sqlAdd2 = "ALTER TABLE product" +
                "ADD CONSTRAINT supplier_product FOREIGN KEY (supplier_id) REFERENCES supplier(id);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sqlDrop1);
            pst.execute();
            pst = connection.prepareStatement(sqlDrop2);
            pst.execute();
            pst = connection.prepareStatement(sqlAdd1);
            pst.execute();
            pst = connection.prepareStatement(sqlAdd2);
            pst.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public void add(Product product) {
        if (product.getProductCategory().getId() == 0) {
            ProductCategory categoryAddedToDatabase = productCategoryDao.add(product.getProductCategory());
            product.setProductCategory(categoryAddedToDatabase);
        }
        String sql = "INSERT INTO product (name, description, default_price, default_currency_code, " +
                "product_category_id, supplier_id) VALUES (?,?,?,?,?,?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, product.getName());
            pst.setString(2, product.getDescription());
            pst.setBigDecimal(3, product.getDefaultPrice());
            pst.setString(4, product.getDefaultCurrency().getCurrencyCode());
            pst.setInt(5, product.getProductCategory().getId());
            pst.setInt(6, product.getSupplier().getId());
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT id, name, description, default_price, default_currency_code, " +
                "product_category_id, supplier_id FROM product;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            Product product;
            while (resultSet.next()) {
                product = new Product(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("default_price"),
                        resultSet.getString("default_currency_code"),
                        productCategoryDao.find(resultSet.getInt("product_category_id")),
                        supplierDao.find(resultSet.getInt("supplier_id"))
                );
                product.setId(resultSet.getInt("id"));
                productList.add(product);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return productList;
    }

    @Override
    public Product find(int id) {
        Product product = null;
        String sql = "SELECT name, description, default_price, default_currency_code, " +
                "product_category_id, supplier_id FROM product WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                product = new Product(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("default_price"),
                        resultSet.getString("default_currency_code"),
                        productCategoryDao.find(resultSet.getInt("product_category_id")),
                        supplierDao.find(resultSet.getInt("supplier_id"))
                );
                product.setId(id);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return product;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM product WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM product;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> resultList = new ArrayList<>();
        String sql = "SELECT id, name, description, default_price, default_currency_code, " +
                "product_category_id, supplier_id FROM product WHERE supplier_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, supplier.getId());
            ResultSet resultSet = pst.executeQuery();
            Product product;
            while (resultSet.next()) {
                product = new Product(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("default_price"),
                        resultSet.getString("default_currency_code"),
                        productCategoryDao.find(resultSet.getInt("product_category_id")),
                        supplier
                );
                product.setId(resultSet.getInt("id"));
                resultList.add(product);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return resultList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> resultList = new ArrayList<>();
        String sql = "SELECT id, name, description, default_price, default_currency_code, " +
                "product_category_id, supplier_id FROM product WHERE product_category_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, productCategory.getId());
            ResultSet resultSet = pst.executeQuery();
            Product product;
            while (resultSet.next()) {
                product = new Product(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("default_price"),
                        resultSet.getString("default_currency_code"),
                        productCategory,
                        supplierDao.find(resultSet.getInt("supplier_id"))
                );
                product.setId(resultSet.getInt("id"));
                resultList.add(product);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        } catch (NullPointerException npe) {
            throw new DataNotFoundException(npe.getMessage());
        }
        return resultList;
    }

}
