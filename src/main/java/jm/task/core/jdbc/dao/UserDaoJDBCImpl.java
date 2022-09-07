package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.lang.model.element.Name;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection conn = Util.getConnection();
    private Statement statement;
    private PreparedStatement preparedStatement;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Users " +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Name VARCHAR(50), " +
                    "LastName VARCHAR(50), " +
                    "Age INT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try {
            statement = conn.createStatement();
            statement.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (Name, LastName, Age) Values (?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE ID = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setAge(resultSet.getByte("Age"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                listUsers.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try {
            statement = conn.createStatement();
            statement.execute("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
