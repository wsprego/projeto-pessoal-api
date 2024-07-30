package com.example.series_api.EpisodeDTO;

public class EpisodeUpdateDTO {
    private String seriesTitle;
    private String episodeTitle;
    private String episodeDescription;
    private String episodeImage;

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    //getters and setters
    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getEpisodeDescription() {
        return episodeDescription;
    }

    public void setEpisodeDescription(String episodeDescription) {
        this.episodeDescription = episodeDescription;
    }

    public String getEpisodeImage() {
        return episodeImage;
    }

    public void setEpisodeImage(String episodeImage) {
        this.episodeImage = episodeImage;
    }
}
