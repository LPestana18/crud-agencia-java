package jdbc.test;

import java.util.Scanner;

public class TestCrud {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int op;
        while (true) {
            menu();
            op = Integer.parseInt(sc.nextLine());
            if (op == 0) {
                System.out.println("Saindo do sistema");
                break;
            }
            if(op == 1) {
                menuComprador();
                op = Integer.parseInt(sc.nextLine());
                CompradorCrud.executar(op);
            }
            if(op == 2) {
                menuCarro();
                op = Integer.parseInt(sc.nextLine());
                CarroCRUD.executar(op);
            }
        }
    }

    private static void menu() {
        System.out.println("Selecione uma opção: ");
        System.out.println("1. comprador");
        System.out.println("2. carro");
        System.out.println("0. Sair");
    }

    private static void menuComprador() {
        System.out.println("Digite a opção para começar: ");
        System.out.println("1. Inserir comprador");
        System.out.println("2. Atualizar Comprador");
        System.out.println("3. Listar todos os compradores");
        System.out.println("4. Buscar comprador por nome");
        System.out.println("5. Deletar");
        System.out.println("9. Voltar");
    }

    private static void menuCarro() {
        System.out.println("Digite a opção para começar: ");
        System.out.println("1. Inserir carro");
        System.out.println("2. Atualizar carro");
        System.out.println("3. Listar todos os carro");
        System.out.println("4. Buscar carro por nome");
        System.out.println("5. Deletar");
        System.out.println("9. Voltar");
    }
}
