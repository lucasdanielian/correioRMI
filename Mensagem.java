import java.util.Date;
import java.io.Serializable;


public class Mensagem implements Serializable {

	private String userNameRemetente;
    private String titulo;
    private String texto;
    private Date data;

    public Mensagem(String nome, String titulo, String texto, Date data){
    	this.userNameRemetente = nome;
    	this.titulo = titulo;
    	this.texto = texto;
    	this.data = data;

    }

    public String getUserNameRemetente(){
    	return userNameRemetente;
    }

    public String getTitulo(){
    	return titulo;
    }

    public String getTexto(){
    	return texto;
    }

    public Date getData(){
    	return data;
    }
}