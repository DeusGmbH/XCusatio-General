package com.deusgmbh.xcusatio.context;

import java.util.List;

import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class ContextHandler {

    public Context buildContext(UserSettings userSettings, List<Lecturer> lecturers, Scenario scenario) {
        Context context = new Context();

        return context;
    }

}
