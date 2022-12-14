package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Film;
import com.example.servingwebcontent.domain.User;
import com.example.servingwebcontent.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.logging.Filter;

@Controller
public class MainController {
    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Film> films = filmRepository.findAll();

        if (filter != null && !filter.isEmpty()) {
            films = filmRepository.findByTag(filter);
        } else {
            films = filmRepository.findAll();
        }

        model.addAttribute("films", films);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String film,
            @RequestParam String date,
            @RequestParam String tag,  Map<String, Object> model
    ){
        Film filmInfo = new Film(film, date, tag, user);

        filmRepository.save(filmInfo);

        Iterable<Film> films = filmRepository.findAll();

        model.put("films", films);

        return "main";
    }


}
