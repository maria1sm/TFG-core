package tfg.spotify.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import tfg.spotify.core.model.DTO.UserDTO;
import tfg.spotify.core.service.UserService;

import javax.net.ssl.SSLException;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    private final String clientId;
    private final String clientSecret;
    private final SpotifyApi spotifyApi;
    @Value("${users.api}")
    private String usersApiUrl;
    public UserServiceImpl(@Value("${spotify.client.id}") String clientId,
                                     @Value("${spotify.client.secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .build();
    }


    public User getUser(String token) {
        try {
            System.out.println(token);
            // Set the access token from the provided token
            spotifyApi.setAccessToken(token);

            // Make API call to Spotify to get the user's profile
            GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
            User userProfile = request.execute();

            return userProfile; // Return the User object
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch user profile", e); // Handle error appropriately
        }
    }

    public Mono<Object> getUserProfile(String token) {
        return Mono.fromCallable(() -> {
            User userProfile = getUser(token); // Call the extracted method
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(userProfile); // Convert to JSON string and return
        });
    }

    public void insertUser(String token) {
        try{
            System.out.println("Intentado crear usuario...");
            User userProfile = getUser(token); // Call the extracted method

            // Convert User to UserDTO
            UserDTO userDTO = UserDTO.builder()
                    .id(userProfile.getId())
                    .email(userProfile.getEmail())
                    .country(userProfile.getCountry().getAlpha2())
                    .build();

            // Use the WebClient to post the user data
            WebClient webClient = WebClient.builder()
                    .baseUrl(usersApiUrl)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Joaquin_Apruebame01")
                    .build();
            webClient.post()
                    .bodyValue(userDTO)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            System.out.println("Usuario insertado");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
