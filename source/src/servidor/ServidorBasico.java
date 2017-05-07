package servidor;

import org.json.JSONObject;

/**
 * Servidor Basico
 */
public class ServidorBasico implements Servidor {  
  
    private JSONObject json = null;
    private final String TAG = this.getClass().getName() + " -> ";

	@Override
	public String getNomeServidor() {
		return this.getClass().getSimpleName();  
	} 

	@Override
	public void setJsonObject(JSONObject json) {
		this.json = json;
	}

	@Override
	public JSONObject getTargetJson() {
		System.out.println(TAG + "Efetuando operação...");  
        
        String valor1 = this.json.getString("valor1");
        String valor2 = this.json.getString("valor2");
        String operador = this.json.getString("operacao");
        Double add = 0d;
        
        switch (operador) {
		case "ADD":
			add = Double.parseDouble(valor1) + Double.parseDouble(valor2);
			System.out.println(TAG + "Efetuando operação de adição..."); 
			break;
		case "SUB":
			add = Double.parseDouble(valor1) - Double.parseDouble(valor2);
			System.out.println(TAG + "Efetuando operação de subtração..."); 
			break;
		case "MUL":
			add = Double.parseDouble(valor1) * Double.parseDouble(valor2);
			System.out.println(TAG + "Efetuando operação de multiplicação..."); 
			break;
		case "DIV":
			add = Double.parseDouble(valor1) / Double.parseDouble(valor2);
			System.out.println(TAG + "Efetuando operação de divisão..."); 
			break;
		} 
        
        JSONObject json = new JSONObject();
        json.put("valor", add);
        
        return json;
	}
 
}  