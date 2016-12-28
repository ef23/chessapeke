import static spark.Spark.*;
import com.google.gson.Gson;

public class Main {
	private static String pass = "EE82529027BE4F3E64AD318D749DCED7";

	public static void main(String[] args) {
		try {
			if (args[0].equals(pass)) {
				Server server = new Server();
				server.run();
			} else {
				System.err.println("Invalid password");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Need to provide a password!");
		}
	}
}
