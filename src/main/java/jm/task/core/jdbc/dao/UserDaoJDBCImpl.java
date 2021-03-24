package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Util util = new Util();
    private static final Connection connection = util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS users " +
                    "(id INT AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "age TINYINT NOT NULL," +
                    " PRIMARY KEY(id));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users (name, lastname, age) VALUES(?,?,?)")){
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM users WHERE id=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
