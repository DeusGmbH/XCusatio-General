package com.deusgmbh.xcusatio.data.usersettings;

import com.deusgmbh.xcusatio.data.StorageUnit;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcuseVibeMode;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */

public class UserSettingsManager extends StorageUnit<UserSettings> {

    public UserSettingsManager() {
        super(UserSettings.class);
    }

    @Override
    public StorageUnit<UserSettings> addDefaultValues() {
        this.add(new UserSettings().setAge(18)
                .setSex(Sex.MALE)
                .setExcuseVibeMode(ExcuseVibeMode.AUTOMATIC)
                .setExcusesVibes(new ExcuseVibes(true, false, true))
                .setHome(new Address().setCity("Mannheim")
                        .setZip("68159")
                        .setStreetname("Akademiestr.")
                        .setStreetnum("6")));
        return this;
    }

}
