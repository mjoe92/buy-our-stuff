package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {

    private DataSource dataSource;

    public SupplierDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS public.supplier(" +
                "id SERIAL NOT NULL, " +
                "name TEXT NOT NULL, " +
                "description TEXT NOT NULL);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public Supplier add(Supplier supplier) {
        Supplier supplierToAdd = findByName(supplier.getName());
        if (supplierToAdd == null) {
            String sql = "INSERT INTO supplier (name, description) VALUES (?,?);";
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, supplier.getName());
                pst.setString(2, supplier.getDescription());
                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                resultSet.next();
                supplierToAdd = supplier;
                supplierToAdd.setId(resultSet.getInt(1));
            } catch (SQLException sqle) {
                throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
            }
        }
        return supplierToAdd;
    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        String sql = "SELECT name, description FROM supplier WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description")
                );
                supplier.setId(id);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return supplier;
    }

    @Override
    public Supplier findByName(String name) {
        Supplier supplier = null;
        String sql = "SELECT id, name, description FROM supplier WHERE name = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                supplier = new Supplier(
                        name,
                        resultSet.getString("description")
                );
                supplier.setId(resultSet.getInt("id"));
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return supplier;
    }


    @Override
    public void remove(int id) {
        String sql = "DELETE FROM supplier WHERE id = ?;";
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
        String sql = "DELETE FROM supplier;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> supplierList = null;
        String sql = "SELECT id, name, description FROM supplier;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            supplierList = new ArrayList<>();
            Supplier supplier;
            while (resultSet.next()) {
                supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description")
                );
                supplier.setId(resultSet.getInt("id"));
                supplierList.add(supplier);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return supplierList;
    }
}
