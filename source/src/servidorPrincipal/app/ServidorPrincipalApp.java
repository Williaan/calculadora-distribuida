package servidorPrincipal.app;

import java.io.IOException;

import base.thread.PoolExecutorRunnable;
import servidorPrincipal.PrincipalServer;

public class ServidorPrincipalApp {

	private static PoolExecutorRunnable poolExecutor = null;

	public static void main(String[] args) {

		 String s = "10000";

//		if (!isArgsValid(args)) {
//			showUsageMode();
//			System.exit(1);
//		}

		int serverPort = Integer.parseInt(s);

		try {

			PrincipalServer mainServer = new PrincipalServer(serverPort);
			poolExecutor = new PoolExecutorRunnable(mainServer);
			poolExecutor.start();

		} catch (IOException e) {
			poolExecutor.stop();
			e.printStackTrace();
		}

	}

	private static boolean isArgsValid(String[] args) {
		boolean returnValue = true;

		if (args.length != 1) {
			returnValue = false;
		} else {
			try {
				Integer.parseInt(args[0]);
				returnValue = true;
			} catch (Exception e) {
				returnValue = false;
			}
		}

		return returnValue;
	}

	private static void showUsageMode() {
		String msg = "Use o comando: java -jar ServidorPrincipal.jar [porta]\n";
		msg += "[porta] \nonde porta � a porta onde o servidor vai esperar por conexoes.\n";
		System.out.println(msg);
	}
}
