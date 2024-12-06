package db_obj;

import java.math.BigDecimal;
import java.sql.*;

public class BankDB {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bank_test";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static User validateLogin(String username, String password) {
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            PreparedStatement ps = con.prepareStatement("select * from bank_user where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // if result found then the credentials are correct
            if(rs.next()) {

                //valid user
                int userId = rs.getInt("id");
                BigDecimal currentBalance = rs.getBigDecimal("current_balance");

                return new User(userId, username, password, currentBalance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // not a valid user
        return null;
    }

    public static boolean registerUser(String username, String password) {
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            if(!isUsernamePresent(username)) {

                PreparedStatement ps = con.prepareStatement("insert into bank_user (username, password, current_balance) " +
                                                            "values (?, ?, 0);");
                ps.setString(1, username);
                ps.setString(2, password);

                ps.executeUpdate();
                return true;

            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // check if username already present in db
    // true - user exists
    // false - user doesn't exist
    private static boolean isUsernamePresent(String username) {
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            PreparedStatement ps = con.prepareStatement("select * from bank_user where username=?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            // username already present
            if(rs.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        // username not present
        return false;
    }
}
