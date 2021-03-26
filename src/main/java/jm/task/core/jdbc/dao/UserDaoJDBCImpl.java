package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    private final Connection connection = util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute("CREATE TABLE IF NOT EXISTS users " +
                    "(id INT AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "age TINYINT NOT NULL," +
                    " PRIMARY KEY(id));");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users (name, lastname, age) VALUES(?,?,?)")){
            connection.setAutoCommit(false);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.rollback();
        } catch (SQLException x){
            x.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM users WHERE id=?")) {
            connection.setAutoCommit(false);
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            connection.rollback();
        } catch (SQLException x){
            x.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            connection.setAutoCommit(false);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
                connection.commit();

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        try {
            connection.rollback();
        } catch (SQLException x){
            x.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute("TRUNCATE TABLE users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.rollback();
        } catch (SQLException x){
            x.printStackTrace();
        }

    }
}
