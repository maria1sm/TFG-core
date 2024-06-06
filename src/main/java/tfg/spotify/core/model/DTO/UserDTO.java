package tfg.spotify.core.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String country;
}
