package tfg.spotify.core.service.impl;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;

import java.io.IOException;

@Service
public class RecommendationServiceImpl {
    private final String clientId;
    private final String clientSecret;
    private final SpotifyApi spotifyApi;
    private final UserServiceImpl userService;

    @Autowired
    public RecommendationServiceImpl(@Value("${spotify.client.id}") String clientId,
                                     @Value("${spotify.client.secret}") String clientSecret, UserServiceImpl userService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userService = userService;
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .build();
    }

    public SearchResult searchItem(String query, String type, String token) throws IOException, SpotifyWebApiException, ParseException {
        System.out.println("search");
        try{
            userService.insertUser(token);
        }catch (Exception e) {
            System.out.println("Error al guardar usuario");
        }

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token)
                .build();

        SearchItemRequest searchItemRequest = spotifyApi.searchItem(query, type)
                .limit(15)
                .build();

        return searchItemRequest.execute();
    }

    public Recommendations getRecommendations(String seedTracks, String seedArtists, String token) throws IOException, SpotifyWebApiException, ParseException {
        System.out.println("recommendation");
        try{
            userService.insertUser(token);
        }catch (Exception e) {
            System.out.println("Error al guardar usuario");
        }
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token)
                .build();

        GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
                .seed_tracks(seedTracks)
                .seed_artists(seedArtists)
                .limit(20)
                .build();

        return getRecommendationsRequest.execute();
    }

    public Playlist createAndSavePlaylist(String token, String playlistName, String[] trackUris) throws Exception {

        spotifyApi.setAccessToken(token);
        String userId = userService.getUser(token).getId();
        // Create the playlist
        CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userId, playlistName)
                .public_(false)
                .build();

        Playlist playlist = createPlaylistRequest.execute();

        // Add tracks to the playlist
        AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(playlist.getId(), trackUris)
                .build();

        addItemsToPlaylistRequest.execute();

        return playlist;
    }
}
