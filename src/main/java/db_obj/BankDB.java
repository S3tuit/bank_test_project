package db_obj;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

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

    public static boolean addTransactionToDB(Transaction transaction) {
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO transaction(user_id, transaction_amount, transaction_type, transaction_date)" +
                    "VALUES (?, ?, (select id from transaction_type where type_description=?), CURRENT_TIMESTAMP);"
            );
            ps.setInt(1, transaction.getUserId());
            ps.setBigDecimal(2, transaction.getTransactionAmount());
            ps.setString(3, transaction.getTransactionType());

            ps.executeUpdate();
            return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateCurrentBalance(User user) {
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE bank_user SET current_balance = ? WHERE id = ?;"
            );

            ps.setBigDecimal(1, user.getCurrentBalance());
            ps.setInt(2, user.getId());

            ps.executeUpdate();
            return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean transfer(User user, String transferredUsername, double amount) {
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            PreparedStatement userExists = con.prepareStatement(
                    "SELECT * FROM bank_user WHERE username = ?;"
            );

            userExists.setString(1, transferredUsername);
            ResultSet resultSet = userExists.executeQuery();

            if(resultSet.next()) {
                // perfrom transfer
                User transferredUser = new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("current_balance")
                );

                // create transaction
                Transaction transferTransaction = new Transaction(
                        user.getId(),
                        "Transfer",
                        BigDecimal.valueOf(-amount),
                        null
                );

                // this transaction will belong to the transferred user
                Transaction receivedTransaction = new Transaction(
                        transferredUser.getId(),
                        "Transfer",
                        new BigDecimal(amount),
                        null
                );

                // update transfer user
                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(amount)));
                updateCurrentBalance(transferredUser);

                // update user current balance
                user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(amount)));
                updateCurrentBalance(user);

                // add these transactions to the database
                addTransactionToDB(transferTransaction);
                addTransactionToDB(receivedTransaction);

                return true;

            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<Transaction> getPastTransactions(User user) {
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            PreparedStatement allTransactions = con.prepareStatement(
                    "select t.id, t.transaction_amount, t.transaction_date, tt.type_description " +
                            "from transaction t " +
                            "inner join transaction_type tt on t.transaction_type = tt.id " +
                            "where user_id = ?;"
            );

            allTransactions.setInt(1, user.getId());
            ResultSet rs = allTransactions.executeQuery();

            while(rs.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        rs.getString("type_description"),
                        rs.getBigDecimal("transaction_amount"),
                        rs.getDate("transaction_date")
                );

                pastTransactions.add(transaction);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return pastTransactions;
    }

}
