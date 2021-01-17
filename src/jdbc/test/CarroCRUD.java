package jdbc.test;

import jdbc.Classes.Carro;
import jdbc.Classes.Comprador;
import jdbc.db.CarroDAO;
import jdbc.db.CompradorDAO;

import java.util.List;
import java.util.Scanner;

public class CarroCRUD {

    private static Scanner sc = new Scanner(System.in);

    public static void executar(int op) {
        switch (op) {
            case 1:
                inserir();
                break;
            case 2:
                atualizar();
                break;
            case 3:
                listar();
                break;
            case 4:
                System.out.print("Digite o nome: ");
                buscarPorNome(sc.nextLine());
                break;
            case 5:
                deletar();
                break;
        }
    }


    private static void inserir() {
        Carro c = new Carro();
        System.out.print("Nome: ");
        c.setNome(sc.nextLine());
        System.out.print("Placa: ");
        c.setPlaca(sc.nextLine());
        System.out.println("Selecione um dos compradores abaixo");
        List<Comprador> compradorList = CompradorCrud.listar();
        c.setComprador(compradorList.get(Integer.parseInt(sc.nextLine())));
        CarroDAO.save(c);
    }

    private static void atualizar() {
        System.out.println("Selecione um dos carros abaixo");
        List<Carro> carroList = listar();
        Carro c = carroList.get(Integer.parseInt(sc.nextLine()));
        System.out.println("Novo nome ou enter para manter o mesmo");
        String nome = sc.nextLine();
        System.out.println("Nova placa ou enter para manter o mesmo");
        String placa = sc.nextLine();
        if (!nome.isEmpty()) {
            c.setNome(nome);
        }
        if (!nome.isEmpty()) {
            c.setPlaca(placa);
        }

        CarroDAO.update(c);
    }

    private static List<Carro> listar() {
        List<Carro> carroList = CarroDAO.selectAll();
        for (int i = 0; i < carroList.size(); i++) {
            Carro c = carroList.get(i);
            System.out.println("[" + i + "] " + c.getNome() + " " + c.getPlaca() + " " + c.getComprador().getCpf());
        }
        return carroList;
    }

    private static void buscarPorNome(String nome) {
        List<Carro> carroList = CarroDAO.searchByName(nome);
        for (int i = 0; i < carroList.size(); i++) {
            Carro c = carroList.get(i);
            System.out.println("[" + i + "] " + c.getNome() + " " + c.getPlaca() + " " + c.getComprador().getNome());
        }
    }

    public static void deletar() {
        System.out.println("Selecione um dos carros abaixo para deletar");
        List<Carro> carroList = listar();
        int index = Integer.parseInt(sc.nextLine());
        System.out.println("Tem certeza [s/n]");
        String op = sc.nextLine();
        if (op.startsWith("s")) {
            CarroDAO.delete(carroList.get(index));
        }
    }

}
