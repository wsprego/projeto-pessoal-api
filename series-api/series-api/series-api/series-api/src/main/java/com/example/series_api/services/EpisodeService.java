package com.example.series_api.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.series_api.models.Episode;
import com.example.series_api.repositories.EpisodeRepository;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    public Page<Episode> getAllEpisodes(Pageable pageable) {
        return episodeRepository.findAll(pageable);
    }


    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    public Optional<Episode> getEpisodeById(Long id) {
        return episodeRepository.findById(id);
    }

    @Transactional
    public Episode saveEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    @Transactional
    public void deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
    }


    public boolean existsByTitle(String episodeTitle) {
        return episodeRepository.existsByEpisodeTitle(episodeTitle);
    }

    public Optional<Episode> getEpisodeByTitle(String episodeTitle) {
        return episodeRepository.findByEpisodeTitle(episodeTitle);
    }


     //Método para obter todos os episódios de forma reativa.
    public Flux<Episode> getAllEpisodesReactive() {
        return Flux.fromIterable(episodeRepository.findAll());
    }
}
