package com.example.series_api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.series_api.models.Episode;
import com.example.series_api.services.EpisodeService;

@RestController
@RequestMapping("/api/episodes")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    //Retorna uma lista de todos os episódios.
    @GetMapping
    public List<Episode> getAllEpisodes() {
        return episodeService.getAllEpisodes();
    }

    //Retorna um episódio específico por ID.
    @GetMapping("/{id}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable Long id) {
        Optional<Episode> episode = episodeService.getEpisodeById(id);
        return episode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Cria um novo episódio.
    @PostMapping
    public Episode createEpisode(@RequestBody Episode episode) {
        return episodeService.saveEpisode(episode);
    }


 // Atualiza um episódio existente.
 // Tenho que resolver um pequno problema aqui
    @PutMapping("/{id}")
    public ResponseEntity<Episode> updateEpisode(@PathVariable Long id, @RequestBody Episode episodeDetails) {
        Optional<Episode> optionalEpisode = episodeService.getEpisodeById(id);
        if (optionalEpisode.isPresent()) {
            Episode episode = optionalEpisode.get();
            episode.setSeriesTitle(episodeDetails.getSeriesTitle());
            episode.setEpisodeTitle(episodeDetails.getEpisodeTitle());
            episode.setEpisodeDescription(episodeDetails.getEpisodeDescription());
            episode.setEpisodeImage(episodeDetails.getEpisodeImage());
            return ResponseEntity.ok(episodeService.saveEpisode(episode));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Deleta um episódio por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.noContent().build();
    }
}
