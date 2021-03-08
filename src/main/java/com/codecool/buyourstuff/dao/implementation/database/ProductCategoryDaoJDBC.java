package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private DataSource dataSource;

    public ProductCategoryDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product_category(" +
                "id serial PRIMARY KEY, " +
                "name text NOT NULL, " +
                "description text, " +
                "department text);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public void add(ProductCategory category) {
        String sql = "INSERT INTO product_category (name, description, department) VALUES (?,?,?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, category.getName());
            pst.setString(2, category.getDescription());
            pst.setString(3, category.getDescription());
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
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
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
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
