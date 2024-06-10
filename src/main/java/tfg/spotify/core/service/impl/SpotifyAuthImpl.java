package tfg.spotify.core.service.impl;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import tfg.spotify.core.model.DTO.RefreshTokenDTO;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class SpotifyAuthImpl {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    public SpotifyAuthImpl ()  {

    }
    //
    public RefreshTokenDTO refreshToken(RefreshTokenDTO refreshToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken.getRefresh_token())
                .build();

        AuthorizationCodeRefreshRequest authRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();

        try {
            AuthorizationCodeCredentials newTokenData = authRefreshRequest.execute();

            //spotifyApi.setAccessToken(newTokenData.getAccessToken());
            return setNewTokenData(refreshToken, newTokenData);


        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Failed to fetch token", e);
        }
    }

    private RefreshTokenDTO setNewTokenData (RefreshTokenDTO refreshTokenDTO, AuthorizationCodeCredentials newTokenData) {
        Timestamp newExpirationTime = Timestamp.from(Instant.now().plusSeconds(newTokenData.getExpiresIn()));
        refreshTokenDTO.setExpiration_time(newExpirationTime);
        refreshTokenDTO.setAccess_token(newTokenData.getAccessToken());

        return refreshTokenDTO;
    }
}
