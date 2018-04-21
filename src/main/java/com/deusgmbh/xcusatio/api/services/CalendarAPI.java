package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.calendar.CalendarAPIConfig;
import com.deusgmbh.xcusatio.api.data.calendar.LectureEvent;
import com.deusgmbh.xcusatio.context.data.CalendarContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * 
 * @author Pascal.Schroeder@de.ibm.com, jan.leiblein@gmail.com
 *
 */

public class CalendarAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(CalendarAPI.class.getName());

    private static final String JSONSTR_DATE_TIME = "dateTime";

    private static final Integer MAX_EVENTS = 1;

    public com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        return new com.google.api.services.calendar.Calendar.Builder(CalendarAPIConfig.HTTP_TRANSPORT,
                CalendarAPIConfig.JSON_FACTORY, CalendarAPIConfig.credentials)
                        .setApplicationName(CalendarAPIConfig.APPLICATION_NAME)
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
        com.google.api.services.calendar.Calendar service = getCalendarService();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events()
                .list("primary")
                .setMaxResults(MAX_EVENTS)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            LOGGER.info("No upcoming events found.");
        } else {
            LOGGER.info("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart()
                        .getDateTime();
                if (start == null) {
                    start = event.getStart()
                            .getDate();
                }
            }
        }
        return items;

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

    @Override
    public CalendarContext get(UserSettings usersettings) throws IOException, JSONException, ParseException {
        if (CalendarAPIConfig.hasCredentials()) {
            Event currentEvent = this.getCurrentLectureEvent();
            String lectureTitle = extractLectureTitle(currentEvent);
            String lecturerName = extractLecturerName(currentEvent);
            Date startTime = extractLectureTimes(currentEvent)[0];
            Date endTime = extractLectureTimes(currentEvent)[1];

            LectureEvent currentLecture = new LectureEvent(lectureTitle, lecturerName, startTime, endTime);

            long minutesLeft = extractMinutesLeft(endTime);
            long minutesPassed = extractMinutesPassed(startTime);

            return new CalendarContext(currentLecture, minutesLeft, minutesPassed);
        }
        return null;
    }

}