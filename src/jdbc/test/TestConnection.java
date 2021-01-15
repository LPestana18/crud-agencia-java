package jdbc.test;

import jdbc.Classes.Comprador;
import jdbc.db.CompradorDB;

public class TestConnection {

    public static void main(String[] args) {
//        deletar();
        atualizar();
    }

    private static void inserir() {
        Comprador comprador = new Comprador("111.111.222-34", "Aline");
        CompradorDB.save(comprador);
    }

    public static void deletar() {
        Comprador comprador = new Comprador();
        comprador.setId(2);
        CompradorDB.delete(comprador);
    }

    public static void atualizar() {
        Comprador comprador = new Comprador(1,"000.000.000-00", "MARIA");
        CompradorDB.update(comprador);
    }
}
