package tfg.spotify.core.service;

import org.apache.hc.core5.http.ParseException;
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

public interface RecommendationService {
    public SearchResult searchItem(String query, String type, String token) throws IOException, SpotifyWebApiException, ParseException;

    public Recommendations getRecommendations(String seedTracks, String seedArtists, String token) throws IOException, SpotifyWebApiException, ParseException;

    public Playlist createAndSavePlaylist(String token, String playlistName, String[] trackUris) throws Exception;

}
