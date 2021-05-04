import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Map;
import java.util.Set;

public class ChampionData {
    public static double getWinRateByChamp(String champ) throws Exception{
        String champPage = "https://na.op.gg/champion/" + champ + "/statistics/" ;

        Document doc = Jsoup.connect(champPage).get();
        Element ele = doc.getElementsByClass("champion-stats-trend-rate").first();
        String winRate = ele.text();

        winRate = winRate.substring(0, winRate.indexOf("%"));

        return Double.parseDouble(winRate);
    }

    public static String getChampName(int id) throws Exception{
        String champList = "http://ddragon.leagueoflegends.com/cdn/11.5.1/data/en_US/champion.json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(champList)).build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        String str = response.body();

        JsonObject champ_dict = new JsonParser().parse(str).getAsJsonObject();
        champ_dict = champ_dict.getAsJsonObject("data");

        Set<Map.Entry<String, JsonElement>> entrySet = champ_dict.entrySet();
        for(Map.Entry<String, JsonElement> iter : entrySet){
            String tempS = iter.getValue().getAsJsonObject().get("key").toString();
            tempS = tempS.replaceAll("\"", "");
            int temp = Integer.parseInt(tempS);

            if(temp == id){
                return iter.getKey();
            }
        }

        return "Could not find Champion";
    }

}

