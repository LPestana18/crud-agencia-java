package jdbc.test;

import jdbc.Classes.Comprador;
import jdbc.db.CompradorDB;

public class TestConnection {

    public static void main(String[] args) {
        Comprador comprador = new Comprador("111.111.222-34", "Aline");
        CompradorDB compradorDB = new CompradorDB();
        compradorDB.save(comprador);
    }
}
