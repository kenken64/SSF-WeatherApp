package sg.edu.nus.iss.WeatherApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import sg.edu.nus.iss.WeatherApp.service.WeatherSvc;
import sg.edu.nus.iss.WeatherApp.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Controller
@RequestMapping(path="/weather")
public class WeatherController {
    @Autowired
    private WeatherSvc weatherSvc;

    @GetMapping
    public String getWeather(@RequestParam(required = true) String city, Model model){
        Optional<Weather> opt = weatherSvc.getWeather(city);
        if(opt.isEmpty()){
            model.addAttribute("weather", new Weather());
            return "weather";
        }

        model.addAttribute("weather", opt.get());
        return "weather";
    }

}
