package no.hvl.dat110.ac.rest;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

import sun.jvm.hotspot.debugger.cdbg.AccessControl;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// Get collection of logs
		get("/accessdevice/log", (req, res) -> {

		});

		// Get specific log entry
		get("/accessdevice/log/:id", (req, res) -> {

		});

		// Add log entry
		post("/accessdevice/log", (req, res) -> {
			Gson gson = new Gson();
			Gson request = new Gson();

			request.fromJson(req, AccessMessage.class);

			return gson.toJson("gg");
		});

		// Delete all log entries and return empty collection
		delete("/accessdevice/log", (req, res) -> {

			
		});

		// Get current accesscode
		get("/accessdevice/code", (req, res) -> {

		});

		// Update accesscode
		put("/accessdevice/code", (req, res) -> {

		});
    }
}
