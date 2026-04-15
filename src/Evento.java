//importando a classe do java que trabalha com datas

import java.time.LocalDateTime;

public class Evento {
    //"Staic final" = esse array pertence à classe, não a cada objeto
    // Ou seja, é compartilhado por todos os eventos, não muda
    //é como uma lista de opções fixas num formulário
    public static final String[] Categorias = {
            "Festa", "Show", "Esporte", "Teatro", "Gastronomia", "Outro"
    };

    //Atributos de cada evento
    String nome;
    String endereco;
    String categoria;
    LocalDateTime horario;
    String descricao;

    //Construtor de evento
    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    //Método que calcula e retorna o status do evento
    //Ele compara o horário do evento com o horário de agora
    public String verificarStaus() {
        LocalDateTime agora = LocalDateTime.now(); //pega data e horario de agora
        if (agora.isBefore(horario)) {
            // se a data de agora for antes do horario colocado
            //Ele ainda não começou
            return "Próximo evento";
        } else if (agora.isBefore((horario.plusHours(2)))) {
            //Passou do horário mas ainda não deu duas horas que começou
            // .plusHours(2) horario do evento + 2 horas
            return "Ocorrendo agora";
        } else {
            //Já passou das 2 horas desde o início
            return "Já ocorreu";
        }
    }

    //Método que imprime todas as informações no console
    public void Menu(){
        System.out.println("======MENU DO EVENTO ======");
        System.out.println("Nome do evento: " + nome);
        System.out.println("Endereço: " + endereco);
        System.out.println("Categoria do evento: " + categoria);
        System.out.println("Horário: " + horario);
        System.out.println("Descrição: " + descricao);
        System.out.println("Status do evento: " + verificarStaus());
        System.out.println("=============================");

    }

}
