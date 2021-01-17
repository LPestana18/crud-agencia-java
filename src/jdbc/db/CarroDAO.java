package jdbc.db;

import jdbc.Classes.Carro;
import jdbc.Classes.Comprador;
import jdbc.conn.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public static void save(Carro carro) {
        String sql = "INSERT INTO `agencia`.`carro` (`nome`, `placa`, compradorid) VALUES (?, ?, ?);";

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, carro.getNome());
            ps.setString(2, carro.getPlaca());
            ps.setInt(3, carro.getComprador().getId());
            ps.executeUpdate();
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delete(Carro carro) {
        if (carro == null || carro.getId() == null) {
            System.out.println("Não foi possivle excluir o registro!");
            return;
        }
        String sql = "DELETE FROM carro WHERE `id` = ?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carro.getId());
            ps.executeUpdate();
            System.out.println("Registro excluido com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void update(Carro carro) {
        if (carro == null || carro.getId() == null) {
            System.out.println("Não foi possivel atualizar o registro!");
            return;
        }
        String sql =
                "UPDATE `agencia`.`carro` SET `placa` = ?, `nome` = ? WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carro.getPlaca());
            ps.setString(2, carro.getNome());
            ps.setInt(3, carro.getId());
            ps.executeUpdate();
            System.out.println("Registro atualizado com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<Carro> selectAll() {
        String sql = "SELECT id, nome, placa, compradorid FROM carro";
        List<Carro> carroList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Comprador c = CompradorDAO.searchById(rs.getInt("compradorid"));
                carroList.add(new Carro
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("placa"), c));
            }
            return carroList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<Carro> searchByName(String nome) {
        String sql = "SELECT id, nome, placa, compradorid FROM carro WHERE nome like ?";
        List<Carro> carroList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comprador c = CompradorDAO.searchById(rs.getInt("compradorid"));

                carroList.add(new Carro
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("placa"), c));
            }
            ConnectionFactory.close(conn, ps, rs);
            return carroList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
