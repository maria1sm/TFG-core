package tfg.spotify.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import tfg.spotify.core.model.DTO.UserDTO;

import java.io.IOException;

public interface UserService {

    public Mono<Object> getUserProfile(String token);

    public void insertUser(String token);
}
