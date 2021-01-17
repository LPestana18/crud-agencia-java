package jdbc.db;

import jdbc.Classes.Comprador;
import jdbc.conn.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompradorDAO {

    public static void save(Comprador comprador) {
        String sql = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES (?, ?);";

        try (Connection conn = ConnectionFactory.getConexao()) {
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
        String sql = "DELETE FROM comprador WHERE `id` = '" + comprador.getId() + "'";
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
            System.out.println("Não foi possivel atualizar o registro!");
            return;
        }
        String sql =
                "UPDATE `agencia`.`comprador` SET `cpf` = '" + comprador.getCpf() + "', `nome` = '" + comprador.getNome() + "' WHERE (`id` = '" + comprador.getId() + "');";
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

    public static List<Comprador> selectAll() {
        String sql = "SELECT id, nome, cpf FROM comprador";
        Connection conn = ConnectionFactory.getConexao();
        List<Comprador> compradorList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                compradorList.add(new Comprador
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            ConnectionFactory.close(conn, stmt, rs);
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<Comprador> searchByName(String nome) {
        String sql = "SELECT id, nome, cpf FROM comprador WHERE nome like '%" + nome + "%'";
        Connection conn = ConnectionFactory.getConexao();
        List<Comprador> compradorList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                compradorList.add(new Comprador
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            ConnectionFactory.close(conn, stmt, rs);
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
