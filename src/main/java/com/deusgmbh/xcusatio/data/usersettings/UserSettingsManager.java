package com.deusgmbh.xcusatio.data.usersettings;

import com.deusgmbh.xcusatio.data.StorageUnit;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcusesVibesMode;
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
        this.add(new UserSettings(null, 18, Sex.MALE, null, null, ExcusesVibesMode.AUTOMATIC,
                new ExcusesVibes(true, false, true)));
        return this;
    }

}
