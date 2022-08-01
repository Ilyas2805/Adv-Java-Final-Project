package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.domain.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long> {

    List<Film> findByTag(String tag);

}
