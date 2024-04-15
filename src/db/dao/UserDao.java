package db.dao;

import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    private static final String SQL_GET_BY_EMAIL = "SELECT * FROM \"user\" WHERE email = ?";
    private static final String SQL_GET_BY_USERNAME = "SELECT * FROM \"user\" WHERE username = ?";
    private static final String SQL_CREATE_USER = "INSERT INTO \"user\" (email, username, password, name, surname) VALUES (?, ?, ?, ?, ?)";

    public boolean existsByEmail(String email) {
        DbConnection dbConnection = new DbConnection();
        try (Connection connection = dbConnection.createConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_EMAIL)) {
                preparedStatement.setString(1, email);
                return preparedStatement.executeQuery().next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByUsername(String username) {
        DbConnection dbConnection = new DbConnection();
        try (Connection connection = dbConnection.createConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_USERNAME)) {
                preparedStatement.setString(1, username);
                return preparedStatement.executeQuery().next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(String email, String username, String hashedPassword, String name, String surname) {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.createConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, surname);
            boolean userNotCreated = preparedStatement.executeUpdate() == 0;
            if (userNotCreated) {
                throw new SQLException();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
