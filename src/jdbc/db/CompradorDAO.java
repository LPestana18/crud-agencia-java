package jdbc.db;

import jdbc.Classes.Comprador;
import jdbc.conn.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompradorDAO {

    public static void save(Comprador comprador) {
        String sql = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES (?, ?);";

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, comprador.getCpf());
            ps.setString(2, comprador.getNome());
            ps.executeUpdate();
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
        String sql = "DELETE FROM comprador WHERE `id` = ?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, comprador.getId());
            ps.executeUpdate();
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
                "UPDATE `agencia`.`comprador` SET `cpf` = ?, `nome` = ? WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, comprador.getCpf());
            ps.setString(2, comprador.getNome());
            ps.setInt(3, comprador.getId());
            ps.executeUpdate();
            System.out.println("Registro atualizado com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<Comprador> selectAll() {
        String sql = "SELECT id, cpf, nome FROM comprador";
        List<Comprador> compradorList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                compradorList.add(new Comprador
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<Comprador> searchByName(String nome) {
        String sql = "SELECT id, nome, cpf FROM comprador WHERE nome like ?";
        List<Comprador> compradorList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                compradorList.add(new Comprador
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            ConnectionFactory.close(conn, ps, rs);
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Comprador searchById(Integer id) {
        String sql = "SELECT id, nome, cpf FROM comprador WHERE id = ?";
        Comprador comprador = null;

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                comprador =
                        new Comprador(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"));
            }
            ConnectionFactory.close(conn, ps, rs);
            return comprador;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
