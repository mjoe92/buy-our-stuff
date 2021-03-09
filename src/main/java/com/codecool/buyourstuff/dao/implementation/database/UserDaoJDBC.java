package com.codecool.buyourstuff.dao.implementation.database;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJDBC implements UserDao {

    private final DataSource dataSource;
    private final CartDao cartDao;

    public UserDaoJDBC(DataSource dataSource, CartDao cartDao) {
        this.dataSource = dataSource;
        this.cartDao = cartDao;
        createTable();
        addTableConstraint();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "id SERIAL PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "cart_id INTEGER);";
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(sql).execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " " + sql + ": " + sqle.getSQLState());
        }
    }

    public void addTableConstraint() {
        String sqlDrop = "ALTER TABLE ONLY user" +
                "DROP CONSTRAINT IF EXISTS user_cart;";
        String sqlAdd = "ALTER TABLE user" +
                "ADD CONSTRAINT user_cart FOREIGN KEY (cart_id) REFERENCES cart(id);";
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
    public void add(User user) {
        String sql = "INSERT INTO user (name, password, cart_id) VALUES (?,?,?);";
        try (Connection connection = dataSource.getConnection()) {
            Cart cart = new Cart();
            cart = cartDao.add(cart);

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setInt(3, cart.getId());
            pst.executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public User find(String userName, String password) {
        User user = null;
        String sql = "SELECT id, name, cart_id FROM user WHERE name = ? AND password = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                user = new User(userName, password);
                user.setId(resultSet.getInt("id"));
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
        return user;
    }

    @Override
    public void clear() {
        String sql = "DELETE * FROM user;";
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
    }

    @Override
    public boolean isNameAvailable(String username) {
        String sql = "SELECT id FROM user WHERE name = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet resultSet = pst.executeQuery();
            if (!resultSet.next()) {
                return true;
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getClass().getSimpleName() + " Product constraints: " + sqle.getSQLState());
        }
        return false;
    }

}
