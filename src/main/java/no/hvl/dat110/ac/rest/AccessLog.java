package no.hvl.dat110.ac.rest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class AccessLog {

	private AtomicInteger cid;
	protected ConcurrentHashMap<Integer, AccessEntry> log;

	public AccessLog() {
		this.log = new ConcurrentHashMap<Integer, AccessEntry>();
		cid = new AtomicInteger(0);
	}

	public int add(String message) {
		AccessEntry entry = new AccessEntry(cid.get(), message);

		log.put(cid.get(), entry);

		return cid.getAndIncrement();
	}

	public AccessEntry get(int id) {
		return log.get(id);
	}

	public void clear() {
		log.clear();
	}

	public String toJson() {
		Gson gson = new Gson();

		return gson.toJson(log);
	}
}
