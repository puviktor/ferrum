package eu.playerunion.ferrum.data.request;

import org.eclipse.jdt.annotation.Nullable;

public class ServerListRequest {
	
	public @Nullable String uuid;
	
	/**
	 * Szerverlista lekérdezéséhez használt objektum.
	 * 
	 * @param uuid A játékos uuid-ja.
	 */
	
	public ServerListRequest(@Nullable String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * A játékos egyedi azonosítójának kikérése.
	 * 
	 * @return A játékos egyedi azonosítója Stringként.
	 */
	
	public @Nullable String getUniqueId() {
		return this.uuid;
	}

}
