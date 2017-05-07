package servidorZumbi;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;

import base.json.JsonObjectIO;
import servidor.Servidor;

public class ZumbiRunner implements Runnable {  
	  
    private Servidor servidor = null; 
    private JsonObjectIO jsonObjectIO = null;  
      
      
    public ZumbiRunner(Socket socket, Servidor servidor) throws IOException {  
        jsonObjectIO = new JsonObjectIO(socket);
        this.servidor = servidor;  
    }  
      
    public void run() {  
        JSONObject jsonFromServer;
        try {  
            jsonFromServer = jsonObjectIO.getJsonObject();
            servidor.setJsonObject(jsonFromServer);  
            JSONObject jsonToServer = servidor.getTargetJson();
            jsonObjectIO.sendJson(jsonToServer);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
