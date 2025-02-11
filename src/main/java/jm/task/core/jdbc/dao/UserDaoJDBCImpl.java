package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
         String createTableSQL = "CREATE TABLE IF NOT EXISTS Users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT, " +
                "PRIMARY KEY (id)" +
                ")";

        try (Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS Users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(dropTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String addSQL = "INSERT into Users (name, lastName, age) values (?, ?, ?)";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement (addSQL)) {

            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);

            int rows = pstmt.executeUpdate();
            System.out.println ("User с именем -  " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String delSQL = "Delete from Users where id = ?";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement (delSQL)) {

            pstmt.setLong (1, id);

            int cnt = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String getAllSQL = "SELECT * FROM Users";
         List<User> users = new ArrayList<> ();

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement (getAllSQL);
             ResultSet rs = pstmt.executeQuery ()) {

            while (rs.next()) {
                Long id = rs.getLong ("id") ;
                User user = new User(rs.getString ("name"),
                        rs.getString ("lastName"),
                        rs.getByte ("age"));
                user.setId(id);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }

    public void cleanUsersTable() {
        String cleanSQL = "TRUNCATE TABLE Users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(cleanSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
