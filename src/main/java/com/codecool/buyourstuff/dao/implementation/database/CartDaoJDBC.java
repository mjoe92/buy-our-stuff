package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.Cart;

import javax.sql.DataSource;
import java.sql.*;
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
        String sql = "CREATE TABLE IF NOT EXISTS public.cart(" +
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
    public Cart add(Cart cart) {
        String sql = "INSERT INTO cart (currency) VALUES (?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cart.getCurrency().getCurrencyCode());
            pst.executeUpdate();
            ResultSet resultSet = pst.getGeneratedKeys();
            resultSet.next();
            cart.setId(resultSet.getInt(1));
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
        return cart;
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
        String sql = "DELETE FROM cart;";
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(sql).executeUpdate();
        }  catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    @Override
    public List<Cart> getAll() {
        List<Cart> cartList;
        String sql = "SELECT id, currency FROM cart;";
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
