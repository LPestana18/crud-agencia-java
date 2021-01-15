package jdbc.test;

import jdbc.Classes.Comprador;
import jdbc.db.CompradorDB;

import java.util.List;

public class TestConnection {

    public static void main(String[] args) {
//        deletar();
//        atualizar();
//        List<Comprador> listaComprador = selecionarTudo();
//        List<Comprador> listaComprador2 = buscarPorNome("ria");
//        System.out.println(listaComprador);
//        System.out.println(listaComprador2);
//        CompradorDB.selectMetaData();
        CompradorDB.checkDriverStatus();
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
        Comprador comprador = new Comprador(1, "000.000.000-00", "MARIA");
        CompradorDB.update(comprador);
    }

    public static List<Comprador> selecionarTudo() {
        return CompradorDB.selectAll();
    }

    public static List<Comprador> buscarPorNome(String nome) {
        return CompradorDB.searchByName(nome);
    }
}
