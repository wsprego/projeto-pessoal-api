package com.example.series_api.EpisodeDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class EpisodeUpdateDTO {
    @NotEmpty(message = "O título da série é obrigatório")
    @Size(max = 100, message = "O título da série não pode ter mais de 100 caracteres")
    private String seriesTitle;

    @NotEmpty(message = "O título do episódio é obrigatório")
    @Size(max = 100, message = "O título do episódio não pode ter mais de 100 caracteres")
    private String episodeTitle;

    @NotEmpty(message = "A descrição do episódio é obrigatória")
    private String episodeDescription;

    @NotEmpty(message = "A imagem do episódio é obrigatória")
    private String episodeImage;




    //getters and setters
    public String getEpisodeTitle() {
        return episodeTitle;
    }
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
