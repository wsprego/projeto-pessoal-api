package service;

import model.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EpisodeRepository;

import java.util.List;
import java.util.Optional;

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

    public Episode saveEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    public void deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
    }
}
