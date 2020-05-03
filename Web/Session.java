package com.Project3.Web;
import com.Project3.Facade.ClientFacade;

public class Session {
	private ClientFacade facade;
	private long lastAccessed;
	
	public Session(ClientFacade facade, long lastAccessed) {
		super();
		this.facade = facade;
		this.lastAccessed = lastAccessed;
	}

	public ClientFacade getFacade() {
		return facade;
	}

	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public long getLastAccessed() {
		return lastAccessed;
	}
}