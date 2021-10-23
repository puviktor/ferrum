package eu.playerunion.ferrum.data;

import org.eclipse.jdt.annotation.NonNull;

public class ServerData {
	
	private @NonNull String ip;
	private @NonNull String motd;
	private @NonNull String serverId;
	
	public @NonNull String getHostName() {
		return this.ip;
	}
	
	public @NonNull String getMotd() {
		return this.motd;
	}
	
	public @NonNull String getServerId() {
		return this.serverId;
	}

}
