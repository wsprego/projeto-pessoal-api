package com.example.series_api.controllers;

import java.util.Optional;

import com.example.series_api.EpisodeDTO.EpisodeCreateDTO;
import com.example.series_api.EpisodeDTO.EpisodeUpdateDTO;
import com.example.series_api.EpisodeDTO.EpisodeViewDTO;
import com.example.series_api.exception.ResourceAlreadyExistsException;
import com.example.series_api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.series_api.models.Episode;
import com.example.series_api.services.EpisodeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/episodes")
@RequiredArgsConstructor
public class EpisodeController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private final EpisodeService episodeService;

    //coisas do consumo da api
    //Método para listar todos os episódios de forma reativa
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<EpisodeViewDTO> getAllEpisodesReactive() {
        return episodeService.getAllEpisodesReactive()
                .map(this::convertToViewDTO);
    }

    //Método para consumir o próprio endpoint "/episodes" de forma reativa.
    @GetMapping("/consume")
    public Flux<EpisodeViewDTO> consumeOwnApi() {
        return webClient.get()
                .uri("http://localhost:8080/api/episodes")
                .retrieve()
                .bodyToFlux(EpisodeViewDTO.class);
    }



    // Método para listar todos os episódios com paginação
    @GetMapping
    public Page<EpisodeViewDTO> getAllEpisodes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return episodeService.getAllEpisodes(pageable).map(this::convertToViewDTO);
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
    @PostMapping("/save")
    public ResponseEntity<EpisodeViewDTO> createEpisode(@RequestBody EpisodeCreateDTO episodeCreateDTO) {
        if (episodeService.existsByTitle(episodeCreateDTO.getEpisodeTitle())) {
            throw new ResourceAlreadyExistsException("Episódio já existe com o título: " + episodeCreateDTO.getEpisodeTitle());
        }
        Episode episode = convertToEntity(episodeCreateDTO);
        Episode createdEpisode = episodeService.saveEpisode(episode);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToViewDTO(createdEpisode));
    }

     //Atualiza um episódio existente.
    @PutMapping("edit/{id}")
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
    @DeleteMapping("delete/{id}")
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
