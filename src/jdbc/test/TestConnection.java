package jdbc.test;

import jdbc.conn.ConnectionFactory;

public class TestConnection {

    public static void main(String[] args) {
        ConnectionFactory conn = new ConnectionFactory();
        conn.getConexao();
    }
}
