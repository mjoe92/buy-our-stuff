package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.dao.ProductDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.LineItem;
import com.codecool.buyourstuff.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoJDBC implements LineItemDao {

    private final DataSource dataSource;
    private final ProductDao productDao;

    public LineItemDaoJDBC(DataSource dataSource, ProductDao productDao) {
        this.dataSource = dataSource;
        this.productDao = productDao;
        createTable();
        addTableConstraint();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS line_item(" +
                "id SERIAL PRIMARY KEY, " +
                "product_id INTEGER NOT NULL, " +
                "cart_id INTEGER NOT NULL, " +
                "quantity INTEGER NOT NULL);";
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(sql).execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    public void addTableConstraint() {
        String sqlDrop = "ALTER TABLE ONLY line_item" +
                "DROP CONSTRAINT IF EXISTS line_item_product;";
        String sqlAdd = "ALTER TABLE line_item" +
                "ADD CONSTRAINT line_item_product FOREIGN KEY (product_id) REFERENCES product(id);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sqlDrop);
            pst.execute();
            pst = connection.prepareStatement(sqlAdd);
            pst.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public void add(LineItem lineItem) {
        String sql = " INSERT INTO line_item (product_id, cart_id, quantity) VALUES (?,?,?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, lineItem.getProduct().getId());
            pst.setInt(2, lineItem.getCartId());
            pst.setInt(3, lineItem.getQuantity());
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public void remove(LineItem lineItem) {
        String sql = "DELETE FROM line_item WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, lineItem.getId());
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE * FROM line_item;";
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public void update(LineItem lineItem, int quantity) {
        String sql = "UPDATE line_item SET quantity = ? WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setInt(2, lineItem.getId());
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public LineItem find(int id) {
        LineItem lineItem = null;
        String sql = "SELECT product_id, cart_id, quantity FROM line_item WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                Product product = productDao.find(resultSet.getInt("product_id"));
                lineItem = new LineItem(
                        product,
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("quantity")
                );
                lineItem.setId(id);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
        return lineItem;
    }

    @Override
    public List<LineItem> getBy(Cart cart) {
        List<LineItem> lineItemList;
        String sql = "SELECT product_id, cart_id, quantity FROM lineItem WHERE cart_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, cart.getId());
            ResultSet resultSet = pst.executeQuery();
            lineItemList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productDao.find(resultSet.getInt("product_id"));
                LineItem lineItem = new LineItem(
                        product,
                        cart.getId(),
                        resultSet.getInt("quantity")
                );
                lineItem.setId(resultSet.getInt("id"));
                lineItemList.add(lineItem);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
        return lineItemList;
    }

}
