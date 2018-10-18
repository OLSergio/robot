package com.bbva.mx.pojos;
import java.util.ArrayList;
import java.util.List;

public class Testeo {
	
	private String id;
	private String name;
	private String url;
	private List<Tests> tests;
	private List<Suites> suites;
	private ArrayList<String> urls;
	private ArrayList<String> pugins;
	private String version;
	
	
	
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
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Tests> getTests() {
		return tests;
	}
	public void setTests(List<Tests> tests) {
		this.tests = tests;
	}
	public List<Suites> getSuites() {
		return suites;
	}
	public void setSuites(List<Suites> suites) {
		this.suites = suites;
	}
	public ArrayList<String> getUrls() {
		return urls;
	}
	public void setUrls(ArrayList<String> urls) {
		this.urls = urls;
	}
	public ArrayList<String> getPugins() {
		return pugins;
	}
	public void setPugins(ArrayList<String> pugins) {
		this.pugins = pugins;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
