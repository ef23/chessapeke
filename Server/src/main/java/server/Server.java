package server;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.google.gson.Gson;

/**
 * Represents: a server which responds to HTTP requests.
 */
public class Server {

    /**
     * Effect: starts running a server
     */
    public void run() {
        // Make the server run on port 8080
        port(8080);
        	
        }
}