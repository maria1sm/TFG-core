package tfg.spotify.core.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaylistDTO {
    private String name;
    private String[] trackUris;
}
