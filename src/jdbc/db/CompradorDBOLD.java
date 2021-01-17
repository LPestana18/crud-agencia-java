package jdbc.db;

import jdbc.Classes.Comprador;
import jdbc.Classes.MyRowSetListener;
import jdbc.conn.ConnectionFactory;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompradorDBOLD {

    public static void save(Comprador comprador) {
        String sql = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES ('" + comprador.getCpf() + "', '" + comprador.getNome() + "');";
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

    public static void saveTransaction() throws SQLException {
        String sql = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES ('TESTE1', 'TESTE1');";
        String sql2 = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES ('TESTE2', 'TESTE2');";
        String sql3 = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES ('TESTE3', 'TESTE3');";
        Connection conn = ConnectionFactory.getConexao();
        Savepoint savepoint = null;
        try {
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            savepoint = conn.setSavepoint("One");
            stmt.executeUpdate(sql2);
            if (true)
                throw new SQLException();
            stmt.executeUpdate(sql3);
            conn.commit();
            ConnectionFactory.close(conn, stmt);
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            conn.rollback(savepoint);
            conn.commit();
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

    public static void updatePS(Comprador comprador) {
        if (comprador == null || comprador.getId() == null) {
            System.out.println("Não foi possivel atualizar o registro!");
            return;
        }
        String sql = "UPDATE `agencia`.`comprador` SET `cpf`= ?, `nome`= ? WHERE `id`= ? ";
        Connection conn = ConnectionFactory.getConexao();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, comprador.getCpf());
            ps.setString(2, comprador.getNome());
            ps.setInt(3, comprador.getId());
            ps.executeUpdate();
            ConnectionFactory.close(conn, ps);
            System.out.println("Registro atualizado com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateRowSet(Comprador comprador) {
        if (comprador == null || comprador.getId() == null) {
            System.out.println("Não foi possivel atualizar o registro!");
            return;
        }
//        String sql = "UPDATE `agencia`.`comprador` SET `cpf`= ?, `nome`= ? WHERE `id`= ? ";
        String sql = "SELECT * FROM comprador where id = ?";
        JdbcRowSet jdbcRowSet = ConnectionFactory.getRowSetConnection();
        jdbcRowSet.addRowSetListener(new MyRowSetListener());
        try {
            jdbcRowSet.setCommand(sql);
//            jdbcRowSet.setString(1, comprador.getCpf());
//            jdbcRowSet.setString(2, comprador.getNome());
            jdbcRowSet.setInt(1, comprador.getId());
            jdbcRowSet.execute();
            jdbcRowSet.next();
            jdbcRowSet.updateString("nome", "LUCAS");
            jdbcRowSet.updateRow();
            ConnectionFactory.close(jdbcRowSet);
            System.out.println("Registro atualizado com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateRowSetCached(Comprador comprador) {
        if (comprador == null || comprador.getId() == null) {
            System.out.println("Não foi possivel atualizar o registro!");
            return;
        }
        String sql = "SELECT * FROM comprador where id = ?";
        CachedRowSet crs = ConnectionFactory.getRowSetConnectionCached();
        try {
            crs.setCommand(sql);
            crs.setInt(1, comprador.getId());
            crs.execute();
            crs.next();
            crs.updateString("nome", "Lucas");
            crs.updateRow();
            crs.acceptChanges();
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

    public static List<Comprador> searchPS(String nome) {
        String sql = "SELECT id, nome, cpf FROM comprador WHERE nome like ?";
        Connection conn = ConnectionFactory.getConexao();
        List<Comprador> compradorList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

    public static List<Comprador> searchByNameRowSet (String nome) {
        String sql = "SELECT id, nome, cpf FROM comprador WHERE nome like ?";
        JdbcRowSet jdbcRowSet = ConnectionFactory.getRowSetConnection();
        jdbcRowSet.addRowSetListener(new MyRowSetListener());
        List<Comprador> compradorList = new ArrayList<>();
        try {
            jdbcRowSet.setCommand(sql);
            jdbcRowSet.setString(1, "%" + nome + "%");
            jdbcRowSet.execute();
            while (jdbcRowSet.next()) {
                compradorList.add(new Comprador
                        (jdbcRowSet.getInt("id"), jdbcRowSet.getString("nome"), jdbcRowSet.getString("cpf")));
            }
            ConnectionFactory.close(jdbcRowSet);
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<Comprador> searchByNameCallableStatement(String nome) {
        String sql = "CALL `agencia`.SP_GetCompradoresPorNome( ? )";
        Connection conn = ConnectionFactory.getConexao();
        List<Comprador> compradorList = new ArrayList<>();
        try {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, "%" + nome + "%");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                compradorList.add(new Comprador
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            ConnectionFactory.close(conn, cs, rs);
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void selectMetaData() {
        String sql = "SELECT * FROM comprador";
        Connection conn = ConnectionFactory.getConexao();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            rs.next();
            int qtdColunas = rsmd.getColumnCount();
            System.out.println("Quantidade de colunas: " + qtdColunas);

            for (int i = 1; i <= qtdColunas; i++) {
                System.out.println("tabela: " + rsmd.getTableName(i));
                System.out.println("Nome coluna: " + rsmd.getColumnName(i));
                System.out.println("Tamanho coluna: " + rsmd.getColumnDisplaySize(i));
            }

            ConnectionFactory.close(conn, stmt, rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void checkDriverStatus() {
        Connection conn = ConnectionFactory.getConexao();

        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            if (dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
                System.out.println("Supporta TYPE_FORWARD_ONLY");
                if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    System.out.println("e também suporta CONCUR_UPDATABLE");
                }
            }

            if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
                System.out.println("Supporta TYPE_SCROLL_INSENSITIVE");
                if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    System.out.println("e também suporta CONCUR_UPDATABLE");
                }
            }

            if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
                System.out.println("Supporta TYPE_SCROLL_SENSITIVE");
                if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    System.out.println("e também suporta CONCUR_UPDATABLE");
                }
            }

            ConnectionFactory.close(conn);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testTypeScroll() {
        String sql = "SELECT id, nome, cpf FROM comprador";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.last()) {
                System.out.println("Ultima linha: " + new Comprador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome")));
                System.out.println("Número da ultima linha: " + rs.getRow());
            }
            System.out.println("Retornou para a primeira linha: " + rs.first());
            System.out.println("Primeira linha: " + rs.getRow());
            rs.absolute(4);
            System.out.println("Linha 4 " + new Comprador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome")));

            rs.relative(-1);
            System.out.println("Linha 3 " + new Comprador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome")));

            rs.relative(-2);
            System.out.println("Linha " + new Comprador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome")));

            System.out.println(rs.isLast());
            System.out.println(rs.isFirst());

            rs.afterLast();
            System.out.println("----------------");
            while (rs.previous()) {
                System.out.println(" " + new Comprador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome")));
            }
            ConnectionFactory.close(conn, stmt, rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateNomesToLowerCase(){
        String sql = "SELECT id, nome, cpf FROM comprador";
        Connection conn = ConnectionFactory.getConexao();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(dbmd.updatesAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));
            System.out.println(dbmd.insertsAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));
            System.out.println(dbmd.deletesAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));

            if (rs.next()) {
                rs.updateString("nome", rs.getString("nome").toUpperCase());
                rs.cancelRowUpdates();
                rs.updateRow();
//                if (rs.rowUpdated()) {
//                    System.out.println("Linha atualizada!");
//                }
            }

//            rs.absolute(2);
//            String nome = rs.getString("nome");
//            rs.moveToInsertRow();
//            rs.updateString("nome", nome.toUpperCase());
//            rs.updateString("cpf", "999.999.999-99");
//            rs.insertRow();
//            rs.moveToCurrentRow();
//            System.out.println(rs.getString("nome") + " row" + rs.getRow());

            rs.absolute(10);
            rs.deleteRow();

//            rs.beforeFirst();
//            while (rs.next()) {
//                System.out.println(rs.getString("nome"));
//            }

            ConnectionFactory.close(conn, stmt, rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
