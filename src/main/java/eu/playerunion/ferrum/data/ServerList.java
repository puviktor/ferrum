package eu.playerunion.ferrum.data;

import java.util.ArrayList;

import org.eclipse.jdt.annotation.NonNull;

public class ServerList {
	
	public @NonNull ArrayList<ServerData> serverList = new ArrayList<ServerData>();

	/**
	 * A különféle szerverlisták tárolására készült objektum.
	 * 
	 * @param serverList A szerverlista egy ArrayList-ben összefoglalva.
	 */
	
	public ServerList(@NonNull ArrayList<ServerData> serverList) {
		this.serverList = serverList;
	}
	
	/**
	 * Szerverlista lekérdezése.
	 * 
	 * @return Szerverlista.
	 */
	
	public @NonNull ArrayList<ServerData> getServerList() {
		return this.serverList;
	}
	
}
