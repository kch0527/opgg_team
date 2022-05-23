package opgg.opgg.config;

import org.springframework.boot.json.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Version {
    public static String profileiconVersion = null;
    public static String summonerVersion = null;
    public static String championVersion = null;
    public static String mapVersion = null;
    public static String languageVersion = null;
    public static String stickerVersion = null;
    public static String itemVersion = null;

    public static void version() {
        BufferedReader bufferedReader = null;
        try{
            String urlStr = "";
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            String result = "";
            String line;
            while((line = bufferedReader.readLine()) != null) {
                result = result + line;
            }
            // ~2022.05.20
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
