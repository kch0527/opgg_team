package opgg.opgg.controller;


import opgg.opgg.config.Version;
import opgg.opgg.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;


@Controller
public class UserController {
    final static String API_KEY = "";

    @RequestMapping(value="/search", method= RequestMethod.GET)
    public String searchUser(HttpServletRequest httpServletRequest, Model model){
        Version.version();
        BufferedReader bufferedReader = null;
        User temporary = null;
        LeagueEntrydto[] leagueInfo = null;
        try{
            String urlstr = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+
                    SummonerName.replace(" ", "")		+"?api_key="+API_KEY;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8")); // 여기에 문자열을 받아와라.
            String result = "";
            String line;
            while((line = br.readLine()) != null) { // 그 받아온 문자열을 계속 br에서 줄단위로 받고 출력하겠다.
                result = result + line;
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject k = (JsonObject) jsonParser.parse(result);
            int profileIconId = k.get("profileIconId").getAsInt();
            String name = k.get("name").getAsString();
            String puuid = k.get("puuid").getAsString();
            long summonerLevel = k.get("summonerLevel").getAsLong();
            long revisionDate = k.get("revisionDate").getAsLong();
            String id = k.get("id").getAsString();
            String accountId = k.get("accountId").getAsString();
            temp = new Summoner(profileIconId,name,puuid,summonerLevel,revisionDate,id,accountId);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        SearchLogDao dao = new SearchLogDao();
        dao.addSearchLog(temp);
        String[] leagueName = null;
        try{
            String urlstr = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"+
                    temp.getId()		+"?api_key="+API_KEY;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8")); // 여기에 문자열을 받아와라.
            String result = "";
            String line;
            while((line = br.readLine()) != null) { // 그 받아온 문자열을 계속 br에서 줄단위로 받고 출력하겠다.
                result = result + line;
            }
            JsonParser jsonParser = new JsonParser();
            JsonArray arr = (JsonArray) jsonParser.parse(result);
            leagueInfo = new LeagueEntrydto[arr.size()];
            leagueName = new String[arr.size()];
            for(int i=0; i<arr.size(); i++) {
                JsonObject k =  (JsonObject) arr.get(i);
                int wins = k.get("wins").getAsInt();
                int losses = k.get("losses").getAsInt();
                String rank = k.get("rank").getAsString();
                String tier = k.get("tier").getAsString();
                String queueType = k.get("queueType").getAsString();
                int leaguePoints = k.get("leaguePoints").getAsInt();
                String leagueId = k.get("leagueId").getAsString();
                leagueInfo[i] = new LeagueEntrydto(queueType, wins, losses, leagueId, rank,tier, leaguePoints);
                urlstr = "https://kr.api.riotgames.com/lol/league/v4/leagues/"+
                        leagueInfo[i].getLeagueId()		+"?api_key="+API_KEY;
                url = new URL(urlstr);
                urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestMethod("GET");
                br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8")); // 여기에 문자열을 받아와라.
                result = "";
                line ="";
                while((line = br.readLine()) != null) { // 그 받아온 문자열을 계속 br에서 줄단위로 받고 출력하겠다.
                    result = result + line;
                }
                jsonParser = new JsonParser();
                k = (JsonObject) jsonParser.parse(result);
                leagueName[i] = k.get("name").getAsString();
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.print(leagueInfo[0]);
        model.addAttribute("summoner", temp);
        model.addAttribute("profileImgURL", "http://ddragon.leagueoflegends.com/cdn/"+VersionCheck.profileiconVersion+"/img/profileicon/"+temp.getProfileIconId()+".png");
        model.addAttribute("leagueInfo", leagueInfo);
        model.addAttribute("tierImgURL", "img/emblems/Emblem_"+leagueInfo[0].getTier()+".png");
        model.addAttribute("leagueName", leagueName);
        return "result";
    }
    }


}
