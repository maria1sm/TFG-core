package tfg.spotify.core.controller;

import com.google.gson.Gson;
import org.apache.hc.core5.http.ParseException;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import tfg.spotify.core.model.DTO.PlaylistDTO;
import tfg.spotify.core.model.DTO.RecommendationDTO;
import tfg.spotify.core.model.DTO.SearchRequestDTO;
import tfg.spotify.core.service.RecommendationService;
import tfg.spotify.core.service.UserService;


import java.io.IOException;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class SpotifyController {
    private final RecommendationService recommendationService;
    private final UserService userService;

    public SpotifyController(RecommendationService recommendationService, UserService userService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
    }


    @GetMapping(path = "/user-profile")
    public Mono<ResponseEntity<Object>> getUserProfile(@RequestHeader("Authorization") String token) {
        System.out.println("Trying to fetch user");
        return userService.getUserProfile(token)
                .map(response -> ResponseEntity.ok().body(response))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while fetching user")));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestHeader("Authorization") String token){
        try {
            System.out.println("Loging in...");
            userService.insertUser(token);
            System.out.println(("Success"));
            return ResponseEntity.ok("Login success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @PostMapping("/search")
    public ResponseEntity<String> searchItem(@RequestBody SearchRequestDTO request, @RequestHeader("Authorization") String token) {
        try {
            System.out.println(request.getQuery());
            SearchResult searchResult = recommendationService.searchItem(request.getQuery(), request.getType(), token);
            Gson gson =  new Gson();
            System.out.println("Search Performed");
            return ResponseEntity.ok(gson.toJson(searchResult));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/recommendations")
    public ResponseEntity<String> getRecommendations(@RequestBody RecommendationDTO request, @RequestHeader("Authorization") String token) {
        try {
            Recommendations response = recommendationService.getRecommendations(request.getTracks(), request.getArtists(), token);
            Arrays.stream(response.getTracks()).forEach(System.out::println);
            Gson gson =  new Gson();
            return ResponseEntity.ok(gson.toJson(response.getTracks()));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/create-playlist")
    public ResponseEntity<String> createPlaylist(@RequestBody PlaylistDTO playlistDTO, @RequestHeader("Authorization") String token) {
        try {
            Playlist playlist = recommendationService.createAndSavePlaylist(token, playlistDTO.getName(), playlistDTO.getTrackUris());
            Gson gson =  new Gson();
            return ResponseEntity.ok(gson.toJson(playlist));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
