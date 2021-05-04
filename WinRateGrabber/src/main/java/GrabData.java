import com.google.gson.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GrabData {
    public static String getSumID(String name) throws Exception{
        String sumInfo = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name +
                "(API key goes here)";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(sumInfo)).build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        String res = response.body();

        String sumID = res.substring(7, res.indexOf(",") - 1);

        return sumID;
    }

    public static String getMatch(String sumID) throws Exception{
        String mReq = "https://na1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + sumID +
                "(API key goes here)";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(mReq)).build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());

        String result = response.body();
        JsonObject matchData = new JsonParser().parse(result).getAsJsonObject();

        Gson toPrint = new GsonBuilder().setPrettyPrinting().create();

        return toPrint.toJson(matchData);
    }

    public static int[][] parseMatch(String matchData){
        JsonObject match = new JsonParser().parse(matchData).getAsJsonObject();
        JsonArray participants = match.getAsJsonArray("participants");

        int[][] champIDs = new int[2][5];
        int blueIn = 0;
        int purpleIn = 0;

        for(int i = 0; i < participants.size(); i++){
            if(participants.get(i).getAsJsonObject().get("teamId").toString().equals("100")){
                champIDs[0][blueIn] = participants.get(i).getAsJsonObject().get("championId").getAsInt();
                blueIn++;
            }
            else{
                champIDs[1][purpleIn] = participants.get(i).getAsJsonObject().get("championId").getAsInt();
                purpleIn++;
            }
        }

        return champIDs;
    }
}
