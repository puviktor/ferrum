package eu.playerunion.ferrum;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.playerunion.ferrum.data.ServerData;
import eu.playerunion.ferrum.data.ServerList;
import eu.playerunion.ferrum.data.request.ServerListRequest;
import eu.playerunion.ferrum.data.response.ServerInfoResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerListLoader {
	
	private static final String checkUrl = "https://auth.playerunion.eu/servers.php";
	
	private static Gson gson = new GsonBuilder().create();
	
	/**
	 * Játékosnak elérhető szerverek lekérdezése UUID alapján.
	 * Mivel nem minden játékos számára elérhető minden szerver, így a UUID segítségével döntjük el, kik, milyen szerverhez jogosultak.
	 * Ez a metódus sosem tér vissza null-ra. Amennyiben a játékosnak egyetlen szerverhez sincs jogosultsága, vagy a szerver meghibásodik, a lista üres marad.
	 * 
	 * @param uuid A játékos raw UUID-je.
	 * null, amennyiben a publikus listát kívánjuk lekérdezni.
	 * @return ServerList Mely tartalmazza a játékos számára elérhető szervereket.
	 */
	
	public static @NonNull ServerList getServerList(@Nullable String uuid) {
		ArrayList<ServerData> list = new ArrayList<ServerData>();
		ServerListRequest request = new ServerListRequest(uuid);
		
		OkHttpClient httpClient = new OkHttpClient();
		RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), gson.toJson(request));
		
		Request process = new Request.Builder()
				.url(checkUrl)
				.post(body)
				.build();
		
		try (Response response = httpClient.newCall(process).execute()) {
			String resp = response.body().string();
			JsonObject json = new JsonParser().parse(resp).getAsJsonObject();
			
			json.keySet().forEach(key -> list.add(gson.fromJson(new String(json.get(key).getAsString().getBytes(), StandardCharsets.UTF_8), ServerData.class)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ServerList(list);
	}
	
	/**
	 * Szerver lepingelése IP-cím alapján. Amennyiben a szerver nem elérhető, a metódus egy null-ra tér vissza.
	 * A kód származási helye: <a href="https://github.com/alvarocdg/Pingtest/blob/master/src/main/java/de/zh32/pingtest/ServerListPingB18.java">Github reference</a>
	 * 
	 * 
	 * @param ip A lepingelni kívánt szerver IP-címe. Példa: "testserver.com:25565"
	 * @return ServerInfoResponse, mely tartalmazza magában a jelenleg online játékosokat és a maximális játékosszámot.
	 */
	
	public static @Nullable ServerInfoResponse ping(@NonNull String ip) {
		try {
			Socket socket = new Socket();
	        OutputStream outputStream;
	        DataOutputStream dataOutputStream;
	        InputStream inputStream;
	        InputStreamReader inputStreamReader;

	        socket.setSoTimeout(2500);
	        socket.setKeepAlive(false);

	        socket.connect(new InetSocketAddress(ip.split(":")[0], Integer.parseInt(ip.split(":")[1])), 2500);
	        outputStream = socket.getOutputStream();
	        dataOutputStream = new DataOutputStream(outputStream);

	        inputStream = socket.getInputStream();
	        inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-16BE"));
	        
	        int packetId = inputStream.read();
	        int length = inputStreamReader.read();
	        
	        dataOutputStream.write(new byte[]{
	            (byte) 0xFE
	        });

	        if (packetId == -1)
	                throw new Exception("Premature end of stream.");

	        if (packetId != 0xFF)
	                throw new Exception("Invalid packet ID (" + packetId + ").");

	        if (length == -1)
	                throw new Exception("Premature end of stream.");

	        if (length == 0)
	                throw new Exception("Invalid string length.");

	        char[] chars = new char[length];

	        if (inputStreamReader.read(chars,0,length) != length)
	                throw new Exception("Premature end of stream.");

	        String string = new String(chars);
	        String[] data = string.split("§");
	        
	        dataOutputStream.close();
	        outputStream.close();

	        inputStreamReader.close();
	        inputStream.close();
	        socket.close();
	        
	        return new ServerInfoResponse(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
		} catch(Exception e) {
			e.printStackTrace();
		}
        
        return null;
    }

}
