package com.example.series_api.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.series_api.models.Episode;
import com.example.series_api.repositories.EpisodeRepository;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

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
        return episodeRepository.existsByTitle(episodeTitle);
    }

    public Optional<Episode> getEpisodeByTitle(String episodeTitle) {
        return episodeRepository.findByEpisodeTitle(episodeTitle);
    }

}
