package eu.playerunion.ferrum.data.response;

import org.eclipse.jdt.annotation.NonNull;

public class ServerInfoResponse {
	
	private @NonNull int online;
	private @NonNull int maxPlayers;
	
	/**
	 * A pingelést követően, a szervertől visszajövő információk tárolására alkalmazott objektum.
	 * 
	 * @param online Az online játékosok száma.
	 * @param maxPlayers A maximális játékosszám.
	 */
	
	public ServerInfoResponse(@NonNull int online, @NonNull int maxPlayers) {
		this.online = online;
		this.maxPlayers = maxPlayers;
	}
	
	/**
	 * Jelenleg online játékosok lekérezése.
	 * 
	 * @return Az online játékosok száma intként.
	 */
	
	public @NonNull int getOnline() {
		return this.online;
	}
	
	/**
	 * Maximális játékosszám lekérdezése.
	 * 
	 * @return A maximális játékosszám intként.
	 */
	
	public @NonNull int getMaxPlayers() {
		return this.maxPlayers;
	}

}
