package servidor;

import org.json.JSONObject;

/**
 * Servidor Especial
 */
public class ServidorEspecial implements Servidor {

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
		case "PORC":
			add = (Double.parseDouble(valor1) * Double.parseDouble(valor2) / 100);
			System.out.println(TAG + "Efetuando operação de porcentagem...");
			break;
		case "POTE":
			add = Math.pow(Double.parseDouble(valor1), 2);
			System.out.println(TAG + "Efetuando operação de potencia...");
			break;
		case "RAIZ":
			add = Math.sqrt(Double.parseDouble(valor1));
			System.out.println(TAG + "Efetuando operação de raiz quadrada...");
			break;
		}

		JSONObject json = new JSONObject();
		json.put("valor", add);

		return json;
	}
}