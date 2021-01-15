package jdbc.conn;

import java.sql.*;

public class ConnectionFactory {
    // java.sql = Connection, Statement, Resultset
    // Driver Manager

    public static Connection getConexao() {
        String url = "jdbc:mysql://localhost:3306/agencia?useSSL=false";
        String user= "root";
        String password = "";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection, Statement stmt) {
        close(connection);
        try {
            if (stmt != null) {
                stmt.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection, Statement stmt, ResultSet rs) {
        close(connection, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
