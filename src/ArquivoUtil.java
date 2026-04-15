import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class ArquivoUtil {
    //Método para salvar todos os eventos em um arquivo "events.data
    //Static significa que é um método dessa classe
    //Recebe a lista de evento como parâmetro
    //Ele roda o FileWriter para escrever as informações fornecidas
    //dentro do arquivo events.data
    public static void salvarEventos(ArrayList<Evento> eventos) {
        try {
            //FileWriter abre ou cria o arquivo para escrita
            FileWriter writer = new FileWriter("events.data");
            for (Evento e : eventos) {
                //Escreve uma linha por evento, separando os campos com ";"
                writer.write(
                        e.nome + "\n" +
                                e.endereco + "\n" +
                                e.categoria + "\n" +
                                e.horario + "\n" +
                                e.descricao + "\n"
                );
            }
            writer.close(); //Ele smpre fecha o arquivo depois de usar
        } catch (IOException e) {
            //IOException acontece quando tem algum problema com o arquivo
            //Problemas como: sem permissão, disco cheio, etc
            System.out.println("Erro ao salvar o arquivo");
        }
    }

    //Método para carregar os eventos do arquivo quando o programa abre
    //Retorna uma ArrayList com todos os eventos lidos
    public static ArrayList<Evento> carregarEventos() {
        ArrayList<Evento> eventos = new ArrayList<>(); // Começa com a lista vazia

        try {
            //BufferedReader lê o arquivo linha por linha de forma eficiente
            BufferedReader reader = new BufferedReader(new FileReader("events.data"));
            String linha; // Variável que vai receber cada linha lida

            //Lê uma linha e guarda em linha
            //Quando não tiver mais linhas readLine() retorna null e o while para
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue; //Pula linha em branco
                String[] p = linha.split("\n");
                if (p.length < 5) continue; //se a linha não tiver 5 partes, algo está errado então ela pula

                //Cria o objeto Evento com os dados lidos
                //LocalDateTime.parse() converte o texto
                Evento e = new Evento(
                        p[0], // nome
                        p[1], //endereco
                        p[2], //categoria
                        LocalDateTime.parse(p[3]), //horario (texto > LocalDateTime)
                        p[4] // descricao
                );
                eventos.add(e); //adiciona o evento reconstruído na lista
            }
            reader.close(); // fecha o arquivo
        } catch (IOException e) {
            // Se o arquivo não existir (primeira vez que abre o programa), cai aqui
            // Não é um erro grave — a lista simplesmente começa vazia
            System.out.println("Nenhum evento salvo ainda. Começando do zero.");
        }
        return eventos; // retorna a lista (com eventos ou vazia)
    }
}