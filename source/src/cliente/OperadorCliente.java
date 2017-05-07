package cliente;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import base.conn.Conexao;
import base.servidor.TipoServidor;
import cliente.conn.ClienteConexaoPrincipal;

/**
 * Classe que ira submeter e receber json 
 * com as operacoes
 */
public class OperadorCliente {

	private List<Double> params = null;
	private TipoServidor tipoServidor = null;

	private Conexao conexao = null;

	public OperadorCliente(List<Double> params, TipoServidor tipoServidor) {
		this.params = params;
		this.tipoServidor = tipoServidor;
	}

	/**
	 * Le o resultado da operacao
	 * @return Valor da operacao
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws JSONException
	 */
	public Double getValue() {

		if (!isParamsValid()) {
			throw new IllegalArgumentException("Parâmetros inválidos!");
		}
		
		Double returnValue = 0d;
		try {
			conexao = new ClienteConexaoPrincipal(".\\cliente.properties");
			conexao.openByProperties();
			JSONObject serverJson = jsonParams();			
			conexao.sendJsonToServer(serverJson);
			JSONObject clienteJson = conexao.getJsonFromServer();
			returnValue = getValueFromJson(clienteJson);
			
		} catch (JSONException | IOException | ClassNotFoundException e) {
//			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * Le os parametros e gera um Json que sera enviado ao servidor principal
	 * @return json JSONObject, baseado nos parametros
	 */
	private JSONObject jsonParams() {

		JSONObject json = new JSONObject();
		json.put("valor1", this.params.get(0).toString());
		json.put("valor2", this.params.get(1).toString());
		json.put("operacao", this.tipoServidor);
		
		return json;
	}

	/**
	 * Verifica/Valida o calculo com os argumentos para saber se e possivel calcular
	 * @return verdadeiro se for possivel realizar o calculo, falso se nao for possivel
	 */
	private boolean isParamsValid() {
		return this.params != null && this.params.size() > 0 && this.tipoServidor != null;
	}

	/**
	 * Le o resultado da operacao que veio do servidor principal pelo Json
	 * @param obj JSONObject que veio do servidor principal
	 * @return valor da operacao
	 */
	private Double getValueFromJson(JSONObject obj) {
		Double returnValue = obj.getDouble("valor");
		return returnValue;
	}

}