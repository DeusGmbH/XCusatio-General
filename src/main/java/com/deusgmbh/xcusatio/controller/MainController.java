package com.deusgmbh.xcusatio.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.usersettings.Address;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

/**
 * This class handles inputs of the userinterface via an event listener
 * interface and serves as mediator between the userinterface and the text
 * processing
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class MainController {
	private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());
	private Wildcards wildcards;

	public MainController() {
		wildcards = new Wildcards();
	}

	public void generateExcuse(Scenario scenario) {
		// TODO: Write generateExcuse method
	}

	public List<String> getWildcardNames() {
		return wildcards.getNames();
	}

	public void generateExcuse(String excuseType) {
		// TODO: Write generateExcuse method
	}

	public List<Scenario> getScenarios() {
		List<Scenario> scenarioList = new ArrayList<Scenario>();
		// TODO Get all scenarios
		return scenarioList;
	}

	public UserSettings getUserSettings() {
		// Following section is only for testing purposes
		// TODO: getUserSettings from DataStorage
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		LocalDate dt = LocalDate.parse("1989-nov-09", dtf);
		UserSettings userSettings = new UserSettings("", dt, Sex.Male,
				new Address("Strasse", "1", "68165", "Olafhausen"));
		return userSettings;
	}

	public void editUserSettings(UserSettings editedUserSettingsObj) {
		// TODO: writeEditUserSettings method (via storageUnit)

	}
}
