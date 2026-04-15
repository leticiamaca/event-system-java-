/*Olá querido professor tudo bem? Eu sou a Letícia, venho aqui por meio desse comentário sinalizar
que estou utilizando o auxílio da IA, nunca mexi com Java na vida e, sinceramente os pdf's não ajudaram muito.
Meu objetivo com essa atividade é aprender e colocar o máximo de comentários possíveis explicando
para mim mesma e para vocês, como funciona esse código :)
*/


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Carrega eventos salvos do arquivo assim que o programa abre
        //Se o arquivo não existir, retorna uma lista vazia
        ArrayList<Evento> eventos = ArquivoUtil.carregarEventos();

        //Scanner é o "ouvido" do programa, ele lê o que o usuário digita
        Scanner scanner = new Scanner(System.in);

        //Menu de cadastro de usuário
        System.out.println("======Bem Vindo! Faça seu cadastro para ver os eventos!=====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine(); // nextLine() lê uma linha inteira de texto

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        //cria o objeto usuário com os dados digitados
        Usuario usuario = new Usuario(nome, email, cidade, telefone);
        System.out.println("Olá, " + usuario.nome + "Seja bem-vindo(a)");

        //Loop principal
        //rodando = true matém o programa aberto
        //Só vira false quando o usuário escolhe a opção 0 de sair
        boolean rodando = true;

        while (rodando) {
            // Exibe o menu de opções
            System.out.println("\n===== Menu =====");
            System.out.println("1 - Cadastrar evento");
            System.out.println("2 - Listar eventos");
            System.out.println("3 - Confirmar presença em evento");
            System.out.println("4 - Ver meus eventos confirmados");
            System.out.println("5 - Cancelar presença em evento");
            System.out.println("6 - Ver meus dados");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt(); //Lê o número digitado
            scanner.nextLine(); //limpa o "Enter" que sobrou do buffer, impede que ele leia uma informação vazia

            //Switch
            switch (opcao) {
                case 1 -> cadastrarEvento(scanner, eventos);
                case 2 -> listarEventos(eventos);
                case 3 -> confirmarPresenca(scanner, eventos, usuario);
                case 4 -> listarConfirmados(usuario);
                case 5 -> cancelarPresenca(scanner, usuario);
                case 6 -> usuario.exibirInformacoes();
                case 0 -> {
                    ArquivoUtil.salvarEventos(eventos); // salva antes de sair
                    rodando = false; // para o while
                    System.out.println("Eventos salvos. Até logo!");
                }
                default -> System.out.println("Opção inválida! Digite um número do menu.");
            }
        }
    }

    //Métodos auxiliares
    //São chamados lá de cima pelo switch, isso deixa o main mais organizado

    // Método para cadastrar um novo evento
    // Recebe o scanner (para ler o teclado) e a lista de eventos
    static void cadastrarEvento(Scanner scanner, ArrayList<Evento> eventos) {
        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        // Mostra as categorias disponíveis numeradas
        System.out.println("Categorias disponíveis:");
        for (int i = 0; i < Evento.Categorias.length; i++) {
            // i vai de 0 até o tamanho do array menos 1
            // mostramos i+1 pra o usuário ver 1, 2, 3... em vez de 0, 1, 2...
            System.out.println((i + 1) + " - " + Evento.Categorias[i]);
        }
        System.out.print("Escolha o número da categoria: ");
        int catOpcao = scanner.nextInt();
        scanner.nextLine();
        // catOpcao-1 porque o array começa em 0, mas mostramos a partir de 1
        String categoria = Evento.Categorias[catOpcao - 1];

        // Lê o horário e valida o formato
        // DateTimeFormatter define o padrão esperado: dia/mês/ano hora:minuto
        LocalDateTime horario = null;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Fica repetindo até o usuário digitar um formato válido
        while (horario == null) {
            System.out.print("Horário (dd/MM/yyyy HH:mm): ");
            String entrada = scanner.nextLine();
            try {
                // .parse() tenta converter o texto para LocalDateTime
                horario = LocalDateTime.parse(entrada, fmt);
            } catch (DateTimeParseException e) {
                // Se o formato estiver errado (ex: digitou "10-06-2025"), cai aqui
                System.out.println("Formato inválido! Use: dd/MM/yyyy HH:mm  (ex: 10/06/2025 20:00)");
            }
        }

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        // Cria o evento e adiciona na lista
        eventos.add(new Evento(nome, endereco, categoria, horario, descricao));
        System.out.println("Evento cadastrado com sucesso!");
    }
    // Método para listar todos os eventos ordenados por horário
    static void listarEventos(ArrayList<Evento> eventos) {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return; // sai do método imediatamente
        }

        // .sort() ordena a lista
        // (a, b) -> a.horario.compareTo(b.horario) compara dois eventos pelo horário
        // o resultado: lista do evento mais próximo para o mais distante
        eventos.sort((a, b) -> a.horario.compareTo(b.horario));

        System.out.println("\n===== Eventos =====");
        for (int i = 0; i < eventos.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]"); // mostra o número do evento
            eventos.get(i).Menu();
        }
    }

    // Método para confirmar presença — lista os eventos e pede o número escolhido
    static void confirmarPresenca(Scanner scanner, ArrayList<Evento> eventos, Usuario usuario) {
        listarEventos(eventos); // mostra a lista primeiro
        if (eventos.isEmpty()) return; // se não tiver eventos, sai

        System.out.print("Digite o número do evento: ");
        int idx = scanner.nextInt() - 1; // -1 porque a lista interna começa em 0
        scanner.nextLine();

        // Verifica se o número digitado é válido
        if (idx >= 0 && idx < eventos.size()) {
            usuario.confirmarPresenca(eventos.get(idx));
        } else {
            System.out.println("Número inválido.");
        }
    }

    // Método para listar apenas os eventos que o usuário confirmou
    static void listarConfirmados(Usuario usuario) {
        if (usuario.eventosConfirmados.isEmpty()) {
            System.out.println("Você não confirmou presença em nenhum evento.");
            return;
        }
        System.out.println("\n===== Meus Eventos Confirmados =====");
        for (int i = 0; i < usuario.eventosConfirmados.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]");
            usuario.eventosConfirmados.get(i).Menu();
        }
    }

    // Método para cancelar presença em um evento confirmado
    static void cancelarPresenca(Scanner scanner, Usuario usuario) {
        listarConfirmados(usuario); // mostra os confirmados primeiro
        if (usuario.eventosConfirmados.isEmpty()) return;

        System.out.print("Digite o número do evento para cancelar: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idx >= 0 && idx < usuario.eventosConfirmados.size()) {
            usuario.cancelarPresenca(usuario.eventosConfirmados.get(idx));
        } else {
            System.out.println("Número inválido.");
        }
    }
}


