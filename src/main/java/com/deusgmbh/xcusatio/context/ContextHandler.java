package com.deusgmbh.xcusatio.context;

import java.util.List;

import com.deusgmbh.xcusatio.api.APIManager;
import com.deusgmbh.xcusatio.context.wildcard.CalendarContext;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcuseVibeMode;

public class ContextHandler {

    public Context buildContext(UserSettings userSettings, List<Lecturer> lecturers, Scenario scenario) {
        Context context = new Context().setAge(userSettings.getAge())
                .setSex(userSettings.getSex());

        if (userSettings.getExcuseVibeMode()
                .equals(ExcuseVibeMode.MANUALLY)) {
            context.setManuellExcusesVibes(userSettings.getExcusesVibes());
        }

        context.setApiContext(new APIManager(scenario).getAPIData(userSettings));

        if (context.getApiContext() != null) {

            CalendarContext calendar = context.getApiContext()
                    .getCalendar();

            if (calendar != null && calendar.getLectureEvent() != null) {
                String lectureName = calendar.getLectureEvent()
                        .getLectureName();
                context.setLecturer(lecturers.stream()
                        .filter(Lecturer.hasLecture(lectureName))
                        .findAny()
                        .get());
            }
        }
        return context;
    }

}
