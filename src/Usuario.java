//importando a biblioteca ArrayList da API java.util

import java.util.ArrayList;

//Classe que irá representar o usuário no sistema
public class Usuario {
    //Atributos = "informações" que cada usuário tem
    String nome;
    String email;
    String cidade;
    String telefone;

    //Nessa ArrayList, iremos guardar os eventos que o usuário confirmou presença
    //Irá começar vazio (new ArrayList<>()) porque o usuário não confirmou ainda
    ArrayList<Evento> eventosConfirmados = new ArrayList<>();

    // Construtor, ele é chamado quando queremos criar(construir) um usuário novo
    // o this significa "o atributo desta classe" isso é para não confundir com os parametros passados
    //Uma função construtora sempre usa o this
    public Usuario(String nome, String email, String cidade, String telefone) {
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
        this.telefone = telefone;
    }

    //Agora depois de criar o usuário com as suas devidas informações iremos criar
    //o método para confirmar uma presença em um evento
    // Essa função recebe o objeto Evento como parametro, o nome não importa
    //Poderia ser batata, mas temos que lembrar que esse objeto irá ser anexado na ArrayList e precisa
    // colocar os devidos nomes corretamente
    //O Evento representa o objeto e o "e" o apelido desse objeto
    //Eveto -> tipo e -> apelido,
    //Evento e
    //"Vou receber um Evento, e vou chamá-lo de e aqui dentro."
    public void confirmarPresenca(Evento e) {
    //.contains() verifica se o evento Já está na lista
        // e a verificação com "!" significa "se não contém"
        //método de imprimir informações no console
        if(!eventosConfirmados.contains(e)){
            eventosConfirmados.add(e);
            System.out.println("Presença confirmada no evento: " + e.nome);
        }else {
            System.out.println(("Você já confirmou presença nesse evento."));
        }

    }

    //Método para cancelar presença
    // .remove() tenta remover o evento da lista
    // Ele retorna TRUE se conseguiu remover, e FALSE se o item não estava lá
    public  void cancelarPresenca(Evento e){
       if(eventosConfirmados.remove(e)){
           System.out.println("Presença cancelada");
       }else{
           System.out.println("Você ainda não está inscrito nesse evento");
       }
    }

    //Método que imprime as informações do usuário no console
    public void exibirInformacoes(){
        System.out.println("Nome: " + nome);
        System.out.println("Email " + email);
        System.out.println("Cidade " + cidade);
        System.out.println("Telefone " + telefone);
    }

}
