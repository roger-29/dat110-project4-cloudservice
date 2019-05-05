package no.hvl.dat110.ac.rest;

import static spark.Spark.*;
import com.google.gson.Gson;

public class App {

	static AccessLog accesslog = null;
	static AccessCode accesscode = null;

	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(getHerokuAssignedPort());
		}

		// objects for data stored in the service

		accesslog = new AccessLog();
		accesscode = new AccessCode();

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
			return accesslog.toJson();
		});

		// Get specific log entry
		get("/accessdevice/log/:id", (req, res) -> {
			Gson gson = new Gson();

			int id = Integer.parseInt(req.params(":id"));

			return gson.toJson(accesslog.get(id));
		});

		// Add log entry
		post("/accessdevice/log", (req, res) -> {
			Gson gson = new Gson();

			AccessMessage message = gson.fromJson(req.body(), AccessMessage.class);

			int id = accesslog.add(message.getMessage());

			return gson.toJson(accesslog.get(id));
		});

		// Delete all log entries and return empty collection
		delete("/accessdevice/log", (req, res) -> {
			accesslog.clear();

			return accesslog.toJson();
		});

		// Get current accesscode
		get("/accessdevice/code", (req, res) -> {
			Gson gson = new Gson();

			return gson.toJson(accesscode);
		});

		// Update accesscode
		put("/accessdevice/code", (req, res) -> {
			Gson gson = new Gson();

			AccessCode code = gson.fromJson(req.body(), AccessCode.class);

			accesscode = code;

			return gson.toJson(accesscode);
		});
	}

	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
