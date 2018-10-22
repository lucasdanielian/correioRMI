import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class Client {

  public Client() {}
	
	public static void main (String args[]) {
		
		String host = (args.length < 1) ? null : args[0];
		
		try {
						
			Registry registry = LocateRegistry.getRegistry("192.168.103.8");
			Correio stub = (Correio) registry.lookup("Correio");

			Scanner ler = new Scanner(System.in);

			exibeMenu();

			int escolha = 1;
			
			while(escolha != 5){
				escolha = ler.nextInt();
				switch(escolha){
					case 1:
						System.out.println("Digite o nome do novo usuario:");
						String nome = ler.next();
						System.out.println("");
						System.out.println("Digite a senha do novo usuario:");
						String senha = ler.next();
						System.out.println("");
						Usuario novo = new Usuario(nome, senha);
						boolean cadastrou = stub.cadastrarUsuario(novo);
						if(cadastrou){
							System.out.println("Usuario cadastrado com sucesso");
							System.out.println("");
						}else{
							System.out.println("Nao foi possível cadastrar o usuario");
							System.out.println("");
						}
						break;
					case 2:
						System.out.println("Digite o nome do seu usuario:");
						String nomeRemetente = ler.next();
						System.out.println("");
						System.out.println("Digite a senha do seu usuario:");
						String senhaRemetente = ler.next();
						System.out.println("");
						System.out.println("Digite o titulo da mensagem:");
						String titulo = ler.next();
						System.out.println("");
						System.out.println("Digite o conteudo da mensagem:");
						Scanner ler2 = new Scanner(System.in);
						String texto = ler2.nextLine();
						System.out.println("");
						Date data = new Date();
						Mensagem nova = new Mensagem(nomeRemetente, titulo, texto, data );
						System.out.println("Digite o nome do usuario de destino");
						String nomeDestinatario = ler.next();
						System.out.println("");
						
						boolean enviou = stub.sendMensagem(nova, senhaRemetente, nomeDestinatario);

						if(enviou){
							System.out.println("Mensagem cadastrada com sucesso");
							System.out.println("");
						}
						else{
							System.out.println("Nao foi possivel cadastrar a mensagem");
							System.out.println("");
						}
						break;
					case 3:
						System.out.println("Digite o nome do seu usuario:");
						String nomee = ler.next();
						System.out.println("");
						System.out.println("Digite a senha do seu usuario:");
						String senhaa = ler.next();
						System.out.println("");

						Mensagem primeira = stub.getMensagem(nomee,senhaa);

						System.out.println("Rementente: " + primeira.getUserNameRemetente());
						System.out.println("Hora de Envio: " + primeira.getData());
						System.out.println("Titulo: " + primeira.getTitulo());
						System.out.println("Texto: " + primeira.getTexto());

						break;
					case 4:
						System.out.println("Digite o nome do seu usuario:");
						String nom = ler.next();
						System.out.println("");
						System.out.println("Digite a senha do seu usuario:");
						String sen = ler.next();
						System.out.println("");

						int res = stub.getNMensagens(nom,sen);

						if(res > -1){
							System.out.println(res + " mensagens na caixa");
						}else{
							if(res == -1){
								System.out.println("Usuario nao autenticado");
							}else{
								System.out.println("Problema no servidor");
							}
						}

						break;
					case 5:
						System.out.println("Tchau!");
						System.out.println("");
						break;
				}

				if(escolha != 5){
					exibeMenu();
				}
			}
			

			


		}
		catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void exibeMenu(){
		System.out.println("Selecione uma das opcoes abaixo:");
		System.out.println("1- Cadastrar novo usuario no sistema.");
		System.out.println("2- Enviar mensagem.");
		System.out.println("3- Recuperar mensagem.");
		System.out.println("4- Quantidade de mensagens");
		System.out.println("5- Sair");
	}
}

