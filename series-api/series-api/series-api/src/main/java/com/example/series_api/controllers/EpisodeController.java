package com.example.series_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.series_api.EpisodeDTO.EpisodeCreateDTO;
import com.example.series_api.EpisodeDTO.EpisodeUpdateDTO;
import com.example.series_api.EpisodeDTO.EpisodeViewDTO;
import com.example.series_api.exception.ResourceAlreadyExistsException;
import com.example.series_api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<EpisodeViewDTO> getAllEpisodes() {
        return episodeService.getAllEpisodes().stream().map(this::convertToViewDTO).collect(Collectors.toList());
    }

    //Retorna um episódio específico por ID.
    @GetMapping("/{id}")
    public ResponseEntity<EpisodeViewDTO> getEpisodeById(@PathVariable Long id) {
        Optional<Episode> episode = episodeService.getEpisodeById(id);
        if (episode.isPresent()) {
            return ResponseEntity.ok(convertToViewDTO(episode.get()));
        } else {
            throw new ResourceNotFoundException("Episódio não encontrado com o ID: " + id);
        }
    }

    //Retorna um episódio específico por titulo do episódio.
    @GetMapping("/title/{episodeTitle}")
    public ResponseEntity<EpisodeViewDTO> getEpisodeByTitle(@PathVariable String episodeTitle) {
        Optional<Episode> episode = episodeService.getEpisodeByTitle(episodeTitle);
        if (episode.isPresent()) {
            return ResponseEntity.ok(convertToViewDTO(episode.get()));
        } else {
            throw new ResourceNotFoundException("Episódio não encontrado com o título: " + episodeTitle);
        }
    }


     //Cria um novo episódio.
    @PostMapping
    public ResponseEntity<EpisodeViewDTO> createEpisode(@RequestBody EpisodeCreateDTO episodeCreateDTO) {
        if (episodeService.existsByTitle(episodeCreateDTO.getEpisodeTitle())) {
            throw new ResourceAlreadyExistsException("Episódio já existe com o título: " + episodeCreateDTO.getEpisodeTitle());
        }
        Episode episode = convertToEntity(episodeCreateDTO);
        Episode createdEpisode = episodeService.saveEpisode(episode);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToViewDTO(createdEpisode));
    }

     //Atualiza um episódio existente.
    @PutMapping("/{id}")
    public ResponseEntity<EpisodeViewDTO> updateEpisode(@PathVariable Long id, @RequestBody EpisodeUpdateDTO episodeUpdateDTO) {
        Optional<Episode> optionalEpisode = episodeService.getEpisodeById(id);
        if (optionalEpisode.isPresent()) {
            Episode episode = optionalEpisode.get();
            episode.setSeriesTitle(episodeUpdateDTO.getSeriesTitle());
            episode.setEpisodeTitle(episodeUpdateDTO.getEpisodeTitle());
            episode.setEpisodeDescription(episodeUpdateDTO.getEpisodeDescription());
            episode.setEpisodeImage(episodeUpdateDTO.getEpisodeImage());
            Episode updatedEpisode = episodeService.saveEpisode(episode);
            return ResponseEntity.ok(convertToViewDTO(updatedEpisode));
        } else {
            throw new ResourceNotFoundException("Episódio não encontrado com o ID: " + id);
        }
    }


    //Deleta um episódio por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        if (!episodeService.getEpisodeById(id).isPresent()) {
            throw new ResourceNotFoundException("Episódio não encontrado com o ID: " + id);
        }
        episodeService.deleteEpisode(id);
        return ResponseEntity.noContent().build();
    }


    // Métodos conversão de DTOs

    //ajusta os padroes para o DTO
    private Episode convertToEntity(EpisodeCreateDTO episodeCreateDTO) {
        Episode episode = new Episode();
        episode.setSeriesTitle(episodeCreateDTO.getSeriesTitle());
        episode.setEpisodeTitle(episodeCreateDTO.getEpisodeTitle());
        episode.setEpisodeDescription(episodeCreateDTO.getEpisodeDescription());
        episode.setEpisodeImage(episodeCreateDTO.getEpisodeImage());
        return episode;
    }

    //ajusta os padroes para o DTO
    private EpisodeViewDTO convertToViewDTO(Episode episode) {
        EpisodeViewDTO episodeViewDTO = new EpisodeViewDTO();
        episodeViewDTO.setId(episode.getId());
        episodeViewDTO.setSeriesTitle(episode.getSeriesTitle());
        episodeViewDTO.setEpisodeTitle(episode.getEpisodeTitle());
        episodeViewDTO.setEpisodeDescription(episode.getEpisodeDescription());
        episodeViewDTO.setEpisodeImage(episode.getEpisodeImage());
        return episodeViewDTO;
    }
}
