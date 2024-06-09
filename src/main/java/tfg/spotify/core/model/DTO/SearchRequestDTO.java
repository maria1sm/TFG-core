package tfg.spotify.core.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequestDTO {
    private String query;
    private String type;
}
