package com.example.series_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.series_api.models.Episode;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
