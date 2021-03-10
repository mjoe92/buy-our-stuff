package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private final DataSource dataSource;

    public ProductCategoryDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product_category(" +
                "id SERIAL PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "description TEXT NOT NULL, " +
                "department TEXT NOT NULL);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public ProductCategory add(ProductCategory category) {
        ProductCategory categoryToAdd = findByName(category.getName());
        if (categoryToAdd == null) {
            String sql = "INSERT INTO product_category (name, description, department) VALUES (?,?,?);";
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, category.getName());
                pst.setString(2, category.getDescription());
                pst.setString(3, category.getDepartment());
                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                resultSet.next();
                categoryToAdd = category;
                categoryToAdd.setId(resultSet.getInt(1));
            } catch (SQLException sqle) {
                throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
            }
        }
        return categoryToAdd;
    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory productCategory = null;
        String sql = "SELECT name, description, department FROM product_category WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                productCategory = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"));
                productCategory.setId(id);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return productCategory;
    }

    @Override
    public ProductCategory findByName(String name) {
        ProductCategory productCategory = null;
        String sql = "SELECT id, name, description, department FROM product_category WHERE name = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                productCategory = new ProductCategory(
                        name,
                        resultSet.getString("description"),
                        resultSet.getString("department"));
                productCategory.setId(resultSet.getInt("id"));
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return productCategory;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM product_category WHERE id = ?;";
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
        String sql = "DELETE FROM product_category;";
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> productCategories = new ArrayList<>();
        String sql = "SELECT id, name, description, department FROM product_category;";
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                ProductCategory productCategory = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"));
                productCategory.setId(resultSet.getInt("id"));
                productCategories.add(productCategory);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return productCategories;
    }

}
