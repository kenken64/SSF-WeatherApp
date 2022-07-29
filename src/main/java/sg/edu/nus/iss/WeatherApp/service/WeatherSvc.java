package sg.edu.nus.iss.WeatherApp.service;

import javax.annotation.PostConstruct;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sg.edu.nus.iss.WeatherApp.models.Weather;

@Service
public class WeatherSvc {
    private static final Logger logger = LoggerFactory.getLogger(WeatherSvc.class);
    
    private static String URL = "https://api.openweathermap.org/data/2.5/weather";

    // @Value("${open.weather.map}")
    // private String apiKey;

    // private boolean hasKey;

    // @PostConstruct
    // private void init(){
    //     hasKey = null != apiKey;
    //     logger.info(
    //         ">>> API KEY set : "  + hasKey
    //     );
    // }

    public Optional<Weather> getWeather (String city){
        String apiKey = System.getenv("OPEN_WEATHER_MAP_API_KEY");
        String weatherUrl = UriComponentsBuilder.fromUriString(URL)
            .queryParam("q", city.replaceAll(" ", "+"))
            .queryParam("units", "metric")
            .queryParam("appid", apiKey)
            .toUriString();

        logger.info(
            ">>> Complete Weather URI API address  : "  + weatherUrl
        );

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try{
            resp = template.getForEntity(weatherUrl, String.class);
            Weather w = Weather.create(resp.getBody());
            return Optional.of(w);
        }catch(Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
