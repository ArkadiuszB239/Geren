package org.brycom.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@Configuration
public class CredentialsConfiguration {

    private static final String APPLICATION_NAME = "Geren";

    private static final List<String> SCOPES = List.of(CalendarScopes.CALENDAR_READONLY);
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    @Value("${google.client.client_id}")
    private String clientId = "test";
    @Value("${google.client.client_secret}")
    private String clientSecret = "test";

    @Bean
    public Calendar googleCalendarService(Credential credentials, NetHttpTransport httpTransport) {
        return new Calendar.Builder(httpTransport, GsonFactory.getDefaultInstance(), credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    //Tried do integrate AWS with google calendar api, but it's too hard for now, so integration is only possible from local environment
    @Bean
    public Credential calendarApiCredentials(NetHttpTransport httpTransport) throws IOException {
        GoogleClientSecrets.Details details = new GoogleClientSecrets.Details();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setWeb(details);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, GsonFactory.getDefaultInstance(), clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Bean
    public NetHttpTransport httpTransport() throws IOException, GeneralSecurityException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }
}
