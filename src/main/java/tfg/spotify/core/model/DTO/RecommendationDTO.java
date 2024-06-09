package tfg.spotify.core.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationDTO {
    private String tracks;
    private String artists;
    //private String genres;
}
