package com.deusgmbh.xcusatio.api.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.LectureEvent;
import com.deusgmbh.xcusatio.context.wildcard.CalendarContext;
import com.deusgmbh.xcusatio.controller.MainController;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
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

public class CalendarAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    // Name of registered google calendar application
    private final String APPLICATION_NAME = "XCUSATIO";

    private final java.io.File DATA_STORE_DIR = new java.io.File("src/main/resources/calendar_api_token");
    private final String CLIENT_SECRET_RESOURCE_STRING = "/client_secret.json";
    private FileDataStoreFactory DATA_STORE_FACTORY;
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private HttpTransport HTTP_TRANSPORT;
    private final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    private static final String JSONSTR_DATE_TIME = "dateTime";

    private Credential credentials;

    public CalendarAPI() {
        try {
            this.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            this.DATA_STORE_FACTORY = new FileDataStoreFactory(this.DATA_STORE_DIR);
            InputStream in = CalendarAPI.class.getResourceAsStream(this.CLIENT_SECRET_RESOURCE_STRING);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(this.JSON_FACTORY, new InputStreamReader(in));
            if (Arrays.asList(this.DATA_STORE_DIR.list())
                    .contains("StoredCredential")) {
                this.credentials = new GoogleCredential.Builder().setTransport(this.HTTP_TRANSPORT)
                        .setJsonFactory(this.JSON_FACTORY)
                        .setClientSecrets(clientSecrets)
                        .build();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public boolean hasCredentials() {
        return this.credentials == null ? false : true;
    }

    public void authorize() throws IOException {
        InputStream in = CalendarAPI.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(this.JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(this.HTTP_TRANSPORT,
                this.JSON_FACTORY, clientSecrets, this.SCOPES).setDataStoreFactory(this.DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        LOGGER.info("Credentials saved to " + this.DATA_STORE_DIR.getAbsolutePath());
        this.credentials = credential;

    }

    public void removeCredentials() throws IOException {
        if (delete(this.DATA_STORE_DIR)) {
            this.credentials = null;
            this.DATA_STORE_FACTORY = new FileDataStoreFactory(this.DATA_STORE_DIR);
        }
    }

    private boolean delete(File file) {
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

    public com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        return new com.google.api.services.calendar.Calendar.Builder(this.HTTP_TRANSPORT, this.JSON_FACTORY,
                this.credentials).setApplicationName(this.APPLICATION_NAME)
                        .build();
    }

    private Event getCurrentLectureEvent() throws IOException {
        List<Event> events = getEvents();
        Event firstEvent = events.get(0);
        return firstEvent;
    }

    private String extractLectureTitle(Event firstEvent) {
        String eventSummary = firstEvent.getSummary();

        String[] lectureDetails = Pattern.compile(", ")
                .split(eventSummary);
        return lectureDetails[0];
    }

    private String extractLecturerName(Event firstEvent) {
        String eventSummary = firstEvent.getSummary();

        String[] lectureDetails = Pattern.compile(", ")
                .split(eventSummary);
        return lectureDetails[1];
    }

    private Date[] extractLectureTimes(Event firstEvent) throws ParseException {
        String startTime = firstEvent.getStart()
                .get(JSONSTR_DATE_TIME)
                .toString();
        startTime = startTime.substring(0, startTime.indexOf('+'));
        String endTime = firstEvent.getEnd()
                .get(JSONSTR_DATE_TIME)
                .toString();
        endTime = endTime.substring(0, endTime.indexOf('+'));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
        Date[] lectureTimes = new Date[2];

        lectureTimes[0] = sdf.parse(startTime);
        lectureTimes[1] = sdf.parse(endTime);

        return lectureTimes;
    }

    private LectureEvent extractLectureEvent(Event firstEvent) throws ParseException {
        String title = extractLectureTitle(firstEvent);
        String lecturerName = extractLecturerName(firstEvent);
        Date startTime = extractLectureTimes(firstEvent)[0];
        Date endTime = extractLectureTimes(firstEvent)[1];
        return new LectureEvent(title, lecturerName, startTime, endTime);
    }

    public List<Event> getEvents() throws IOException {
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
                // System.out.println("No upcoming events found.");
            } else {
                // System.out.println("Upcoming events");
                for (Event event : items) {
                    DateTime start = event.getStart()
                            .getDateTime();
                    if (start == null) {
                        start = event.getStart()
                                .getDate();
                    }
                    // System.out.printf("%s (%s)\n", event.getSummary(),
                    // start);
                }
                return items;
            }

        } catch (Exception e) {
            throw new RuntimeException("No calendar connected");
        }
        return null;

    }

    private long extractMinutesPassed(Date startTime) {
        long now = System.currentTimeMillis();
        long startTimeMillis = startTime.getTime();
        return (((now - startTimeMillis) / 1000) / 60);
    }

    private long extractMinutesLeft(Date endTime) {
        long now = System.currentTimeMillis();
        long endTimeMillis = endTime.getTime();
        return (((endTimeMillis - now) / 1000) / 60);
    }

    public static void main(String[] abcdefg) throws IOException, ParseException {
        CalendarAPI cApi = new CalendarAPI();
        CalendarContext calendarContext = cApi.get(null);
        System.out.println(calendarContext.getLectureEvent()
                .getLectureTitle());
    }

    @Override
    public CalendarContext get(UserSettings usersettings) throws IOException, JSONException, ParseException {
        CalendarAPI calendarAPI = new CalendarAPI();
        calendarAPI.authorize();

        Event currentEvent = calendarAPI.getCurrentLectureEvent();
        String lectureTitle = extractLectureTitle(currentEvent);
        String lecturerName = extractLecturerName(currentEvent);
        Date startTime = extractLectureTimes(currentEvent)[0];
        Date endTime = extractLectureTimes(currentEvent)[1];

        LectureEvent currentLecture = new LectureEvent(lectureTitle, lecturerName, startTime, endTime);

        long minutesLeft = extractMinutesLeft(endTime);
        long minutesPassed = extractMinutesPassed(startTime);

        CalendarContext calendarContext = new CalendarContext(currentLecture, minutesLeft, minutesPassed);

        return calendarContext;
    }

}