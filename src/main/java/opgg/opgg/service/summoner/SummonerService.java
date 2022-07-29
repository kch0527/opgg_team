package opgg.opgg.service.summoner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import opgg.opgg.web.dto.league.LeagueEntryDTO;
import opgg.opgg.web.dto.summoner.SummonerDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SummonerService {

    final static String API_KEY = "";

    SummonerDTO summonerDTO = null;
    LeagueEntryDTO leagueInfo = null;

    BufferedReader br = null;

    public SummonerDTO SearchSummoner(String suName) {

        //request 로 name 값을 가져와서 url 에 합칠거임.
        String SummonerName = suName;

        try {
            String urlstr = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" +
                    SummonerName.replaceAll(" ", "%20") + "?api_key=" + API_KEY;

            URL url = new URL(urlstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            //이 부분에서 문자열을 받아옴.
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;

            //그 받아온 문자열을 계속 br에서 줄 단위로 받고 출력하겠다.
            //이 부분을 바꿔줘야 띄어쓰기가 될듯.
            while ((line = br.readLine()) != null) {
                result = result + line;
            }

            //Json parser로 재파싱하는 부분.
            JsonParser jsonParser = new JsonParser();
            JsonObject k = (JsonObject) jsonParser.parse(result);

            int profileIconId = k.get("profileIconId").getAsInt();
            String name = k.get("name").getAsString();
            String puuid = k.get("puuid").getAsString();
            long summonerLevel = k.get("summonerLevel").getAsLong();
            long revisionDate = k.get("revisionDate").getAsLong();
            String id = k.get("id").getAsString();
            String accountId = k.get("accountId").getAsString();

            summonerDTO = new SummonerDTO(profileIconId, name, puuid, summonerLevel, revisionDate, id, accountId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return summonerDTO;

    }

    public LeagueEntryDTO searchLeagueEntry(String id) {

        try {
            String urlstr = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" +
                    id + "?api_key=" + API_KEY;

            URL url = new URL(urlstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;

            while((line = br.readLine()) != null) {
                result = result + line;
            }

            //Json parser로 재파싱하는 부분.
            JsonParser jsonParser = new JsonParser();
            JsonArray arr = (JsonArray) jsonParser.parse(result);
            JsonObject k = (JsonObject) arr.get(0);

            int wins = k.get("wins").getAsInt();
            int losses = k.get("losses").getAsInt();
            String rank = k.get("rank").getAsString();
            String tier = k.get("tier").getAsString();
            String queueType = k.get("queueType").getAsString();
            int leaguePoints = k.get("leaguePoints").getAsInt();

            leagueInfo = new LeagueEntryDTO(queueType, tier, rank, leaguePoints, wins, losses);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return leagueInfo;
    }
}
