package servidorZumbi.app;

import base.servidor.TipoServidor;
import servidorZumbi.ZumbiServer;

/**
 * aplicacao zumbi
 */
public class ServidorZumbiApp {

	public static void main(String[] args) throws Exception {
		
		if (!isArgsValid(args)) {
			showUsageMode();
			System.exit(1);
		 }

		TipoServidor tipoServidor = TipoServidor.valueOf(args[0].toUpperCase());
//		TipoServidor tipoServidor = TipoServidor.valueOf("ESPECIAL");

		try {
			ZumbiServer operatorServer = new ZumbiServer(tipoServidor);
			Thread thread = new Thread(operatorServer);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean isArgsValid(String[] args) {
		boolean returnValue = true;

		if (args.length != 1) {
			returnValue = false;
		} else {
			try {
				TipoServidor.valueOf(args[0]);
				returnValue = true;
			} catch (Exception e) {
				returnValue = false;
			}
		}

		return returnValue;
	}

	private static void showUsageMode() {
		
		String msg = " # Use o comando: java -jar ServidorZumbi.jar [tipo] para ativar o serviço\n"
				+ " # [tipo] BASICO | ESPECIAL";
		
		System.out.println(msg);
	}
}