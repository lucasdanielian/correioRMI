import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

public class Server implements Correio {

	ArrayList<Usuario> lista = new ArrayList<Usuario>();



	public boolean cadastrarUsuario (Usuario u) throws RemoteException{
		try{
			this.lista.add(u);
			System.out.println("Usuario " + u.getUser() +" cadastrado");
			return true;
		} catch(Exception e){
			System.out.println("Problema detectado no servidor: " + e);

			return false;
		}
	}


	public Mensagem getMensagem (String userName, String senha) throws RemoteException{
		try{

			int i = autenticacao(userName,senha);
			if(i >= 0){
				Usuario userAtual = lista.get(i);

				if(getNMensagens(userName,senha) > 0){
					Mensagem aux = userAtual.getMensagem(0);
					userAtual.deletaMensagem(0);
					System.out.println("Retornando primeira mensagem para " + userName);
					return aux;
				}else{
					Date data = new Date();
					Mensagem erroM = new Mensagem("Servidor", "Quantidade de Mensagens", "Este usuario nao possui mensagens pendentes", data );

					return erroM;
				}
				
			}else{
				Date data = new Date();
				Mensagem erroAu = new Mensagem("Servidor", "Erro de Autenticação", "Usuario nao foi autenticado", data );

				return erroAu;
			}

		}catch(Exception e){
			System.out.println("Problema detectado no servidor: " + e);

			Date data = new Date();
			Mensagem erroServ = new Mensagem("Servidor", "Erro no servidor", "Algo de errado ocorreu no servidor, entre em contato com o administrador: " + e, data );

			return erroServ;
		}
	}

	public int autenticacao(String userName, String senha){

		int autenticado = -1;

		for (int i = 0;i < lista.size() ;i++ ) {
			if(lista.get(i).getUser().equals(userName)){
				if(lista.get(i).getSenha().equals(senha)){
					autenticado = i;
					return autenticado;
				}
			}
		}

		return autenticado;
	}

	public int getNMensagens (String userName, String senha) throws RemoteException{
		try{

			int i = autenticacao(userName,senha);
			if(i >= 0){
				Usuario userAtual = lista.get(i);
				System.out.println("Retornando quantidade de mensagens de " + userName);
				return userAtual.qtMensagens();

			}else{
				return -1;
			}

		}catch(Exception e){
			System.out.println("Problema detectado no servidor: " + e);

			return -2;
		}
	}

	public boolean sendMensagem (Mensagem m, String senha, String userNameDestinatario) throws RemoteException{
		
		try{
			int aut = autenticacao(m.getUserNameRemetente(), senha);

			if(aut >= 0){
				int id = buscaUser(userNameDestinatario);
				if(id >= 0){
					lista.get(id).adicionaMensagem(m);
					System.out.println("Mensagem adicionada na caixa de " + userNameDestinatario + " postada por " + m.getUserNameRemetente());
					return true;
				}else{
					return false;
				}
				
			}else{
				return false;
			}

		}catch(Exception e){
			System.out.println("Problema detectado no servidor: " + e);

			return false;
		}

	}

	public int buscaUser(String nome){
		for (int i = 0; i< lista.size() ;i++ ) {
			if(lista.get(i).getUser().equals(nome)){
				return i;
			}
		}

		return -1;
	}

	
	public static void main (String args[]) {
		
		try {
            System.setProperty("java.rmi.server.hostname","192.168.103.8");
			
			//Create and export a remote object
			Server obj = new Server();
			Correio stub = (Correio) UnicastRemoteObject.exportObject(obj,0);
			
			//Register the remote object with a Java RMI registry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Correio", stub);
			
			System.out.println("Server Ready");
		}
			catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

