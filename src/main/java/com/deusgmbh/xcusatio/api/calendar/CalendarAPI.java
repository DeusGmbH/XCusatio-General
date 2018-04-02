package com.deusgmbh.xcusatio.api.calendar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.controller.MainController;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * 
 * TODO @Jan Aufräumen, für benötigten Nutzen anpassen (nur 1
 * Event/Parameterübergabe Anzahl Events)...
 * 
 * @author Pascal.Schroeder@de.ibm.com, jan.leiblein@gmail.com
 *
 */

public class CalendarAPI {
    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    // Name of registered google calendar application
    private static final String APPLICATION_NAME = "XCUSATIO";

    private static final java.io.File DATA_STORE_DIR = new java.io.File("src/main/resources/calendar_api_token");
    private static final String CLIENT_SECRET_RESOURCE_STRING = "/client_secret.json";
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    private static Credential credentials;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
            InputStream in = CalendarAPI.class.getResourceAsStream(CLIENT_SECRET_RESOURCE_STRING);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            if (Arrays.asList(DATA_STORE_DIR.list())
                    .contains("StoredCredential")) {
                credentials = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
                        .setJsonFactory(JSON_FACTORY)
                        .setClientSecrets(clientSecrets)
                        .build();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static boolean hasCredentials() {
        return credentials == null ? false : true;
    }

    public static void authorize() throws IOException {
        InputStream in = CalendarAPI.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        LOGGER.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        credentials = credential;
    }

    public static void removeCredentials() throws IOException {
        if (delete(DATA_STORE_DIR)) {
            credentials = null;
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        }
    }

    private static boolean delete(File file) {
        if (file.isDirectory()) {
            Arrays.asList(file.listFiles())
                    .stream()
                    .forEach(subFile -> {
                        delete(subFile);
                    });
        } else if (file.isFile()) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void getEvents() throws IOException {
        try {
            com.google.api.services.calendar.Calendar service = getCalendarService();

            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = service.events()
                    .list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            if (items.size() == 0) {
                System.out.println("No upcoming events found.");
            } else {
                System.out.println("Upcoming events");
                for (Event event : items) {
                    DateTime start = event.getStart()
                            .getDateTime();
                    if (start == null) {
                        start = event.getStart()
                                .getDate();
                    }
                    System.out.printf("%s (%s)\n", event.getSummary(), start);
                }
            }
        } catch (Exception e) {
            System.out.println("No calendar connected");
        }

    }

}