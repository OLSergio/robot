package com.bbva.mx.pojos;

import java.util.ArrayList;

public class Suites {

	private String id;
	private String name;
	private String parallel;
	private String timeout;
	private ArrayList<String> tests;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
	}
	public String getParallel() {
		return parallel;
	}
	public void setParallel(String parallel) {
		this.parallel = parallel;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public ArrayList<String> getTests() {
		return tests;
	}
	public void setTests(ArrayList<String> tests) {
		this.tests = tests;
	}
	
	
}
