package jdbc.db;

import jdbc.Classes.Comprador;
import jdbc.conn.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CompradorDB {

    public static void save(Comprador comprador) {
        String sql = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES ('"+ comprador.getCpf() +"', '"+ comprador.getNome()+"');";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            ConnectionFactory.close(conn, stmt);
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delete(Comprador comprador) {
        if (comprador == null || comprador.getId() == null) {
            System.out.println("Não foi possivle excluir o registro!");
            return;
        }
        String sql = "DELETE FROM comprador WHERE `id` = '"+ comprador.getId()+"'";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            ConnectionFactory.close(conn, stmt);
            System.out.println("Registro excluido com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void update(Comprador comprador) {
        if (comprador == null || comprador.getId() == null) {
            System.out.println("Não foi possivle atualizar o registro!");
            return;
        }
        String sql =
                "UPDATE `agencia`.`comprador` SET `cpf` = '"+comprador.getCpf()+"', `nome` = '"+comprador.getNome()+"' WHERE (`id` = '"+comprador.getId()+"');";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            ConnectionFactory.close(conn, stmt);
            System.out.println("Registro atualizado com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
