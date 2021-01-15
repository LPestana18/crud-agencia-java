package jdbc.db;

import jdbc.Classes.Comprador;
import jdbc.conn.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CompradorDB {

    public void save(Comprador comprador) {
        String sql = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES ('"+ comprador.getCpf() +"', '"+ comprador.getNome()+"');";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            System.out.println(stmt.executeUpdate(sql));
            ConnectionFactory.close(conn, stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
