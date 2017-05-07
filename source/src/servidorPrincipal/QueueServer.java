package servidorPrincipal;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import base.json.JsonObjectIO;
import base.servidor.TipoServidor;

public class QueueServer {
	
	private List<String> basicHosts = new ArrayList<>();
	private List<String> specialHosts = new ArrayList<>();
	private CircularFifoQueue<String> basicQueue = null;
	private CircularFifoQueue<String> specialQueue = null;
	private JsonObjectIO jsonObjectIO = null;
	String fileName;

	public QueueServer(JsonObjectIO jsonObjectIO) {	
		this.basicQueue = new CircularFifoQueue<>();
		this.specialQueue = new CircularFifoQueue<>();
		this.jsonObjectIO = jsonObjectIO;
		
		load();
	}

	public String getServer(String server){
		
		TipoServidor tipoServidor = TipoServidor.valueOf(server);
		String s = tipoServidor.getClassName().split("\\.")[1];
		
		switch (s) {
		case "ServidorBasico":
			s = this.basicQueue.poll();
			System.out.println("-> Get Server Pool: " + this.basicQueue);
			break;
		case "ServidorEspecial":
			s = this.specialQueue.poll();
			System.out.println("-> Get Server Pool: " + this.specialQueue);
			break;
		default:
			break;
		}

		return s;
	}
	
	public void setServer(String server, String host){
		
		switch (server) {
		case "BASICO":
			this.basicQueue.add(host);
			System.out.println("-> Set BasicServer Pool: " + this.basicQueue);
			break;
		case "ESPECIAL":
			this.specialQueue.add(host);
			System.out.println("-> Set SpecialServer Pool: " + this.specialQueue);
			break;
		default:
			break;
		}

	}
	
	private void load(){
		
		this.basicHosts = jsonObjectIO.readSlaveJson("ServidorBasico");		
		for (String host : basicHosts) {
			basicQueue.add(host);
		}
		
		this.specialHosts = jsonObjectIO.readSlaveJson("ServidorEspecial");
		for (String host : specialHosts) {
			specialQueue.add(host);
		}
		
		System.out.println("-> Servidores Basicos ativos: " + this.basicQueue.size());
		System.out.println("-> Servidores Especiais ativos: " + this.specialQueue.size());
	}
	
	public void update(){
		System.out.println("-> Queue Update! ");
	}

}
