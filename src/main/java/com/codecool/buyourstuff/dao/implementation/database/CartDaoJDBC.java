package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.Cart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJDBC implements CartDao {

    private final DataSource dataSource;

    public CartDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS cart(" +
                "id SERIAL PRIMARY KEY, " +
                "currency TEXT NOT NULL);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public void add(Cart cart) {
        String sql = "INSERT INTO cart VALUES (?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, cart.getCurrency().getCurrencyCode());
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public Cart find(int id) {
        Cart cart = null;
        String sql = "SELECT currency FROM cart WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                cart = new Cart(resultSet.getString("currency"));
                cart.setId(id);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
            return cart;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM cart WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        }  catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE * FROM cart;";
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(sql).executeUpdate();
        }  catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public List<Cart> getAll() {
        List<Cart> cartList;
        String sql = "SELECT currency FROM cart;";
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            cartList = new ArrayList<>();
            Cart cart;
            while (resultSet.next()) {
                cart = new Cart(resultSet.getString("currency"));
                cart.setId(resultSet.getInt("id"));
                cartList.add(cart);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return cartList;
    }
}
