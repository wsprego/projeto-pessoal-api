package com.example.series_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.series_api.models.Episode;

import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long>, PagingAndSortingRepository<Episode, Long> { 
    boolean existsByEpisodeTitle(String episodeTitle); // para veridicar de ja existe um episodio ja existe
    Optional<Episode> findByEpisodeTitle(String episodeTitle); // para listar o episodio por nome
}
