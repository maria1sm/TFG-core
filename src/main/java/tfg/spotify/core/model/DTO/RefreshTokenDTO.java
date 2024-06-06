package tfg.spotify.core.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class RefreshTokenDTO {

    private String access_token;
    private String refresh_token;
    private Timestamp expiration_time;
}
