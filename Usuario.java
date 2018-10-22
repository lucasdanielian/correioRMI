import java.util.LinkedList;
import java.io.Serializable;

public class Usuario implements Serializable {

	private String userName;
    private String senha;
    private LinkedList<Mensagem> listaDeMensagens;

    public Mensagem getMensagem(int i){
    	return this.listaDeMensagens.get(i);
    }

    public Usuario(String nome, String senha){
    	this.userName = nome;
    	this.senha =senha;
    	listaDeMensagens = new LinkedList<Mensagem>();
    }

    public String getUser(){
    	return userName;
    }

    public String getSenha(){
    	return senha;
    }

    public void adicionaMensagem(Mensagem m){
    	listaDeMensagens.add(m);
    }

    public void deletaMensagem(int pos){
    	listaDeMensagens.remove(pos);
    }

    public int qtMensagens(){
        return listaDeMensagens.size();
    }

}