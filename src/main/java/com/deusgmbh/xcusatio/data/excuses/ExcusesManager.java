package com.deusgmbh.xcusatio.data.excuses;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.data.StorageUnit;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.data.tags.Tag;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */

public class ExcusesManager extends StorageUnit<Excuse> {
    private ObservableList<String> mostRecentlyUsed;

    public ExcusesManager() {
        super(Excuse.class);
        this.mostRecentlyUsed = this.getTextSorted(Excuse.byLastUsed);
        this.get()
                .addListener(new ListChangeListener<Excuse>() {
                    @Override
                    public void onChanged(Change<? extends Excuse> c) {
                        mostRecentlyUsed.clear();
                        mostRecentlyUsed.addAll(getTextSorted(Excuse.byLastUsed));
                    }
                });
    }

    private ObservableList<String> getTextSorted(Comparator<Excuse> sorting) {
        return FXCollections.observableArrayList(this.get()
                .stream()
                .sorted(sorting)
                .map(Excuse::getText)
                .collect(Collectors.toList()));
    }

    /**
     * @param amount
     *            the maximal amount of excuses to return
     * @returns the most recently used excuses.
     */
    public ObservableList<String> getMostRecentlyUsed() {
        return this.mostRecentlyUsed;
    }

    @Override
    public ExcusesManager addDefaultValues() {
        // TODO: add more default values
        this.add(new Excuse("Die Bahn kam zu sp�t", ScenarioType.LATE_ARRIVAL).addTag(Tag.TRAIN)
                .addTag(Tag.MALE)
                .addTag(Tag.FEMALE))
                .add(new Excuse(
                        "Ich habe von einem Fu�ballspiel getr�umt und als ich aufstehen musste, gab es eine Verl�ngerung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE))
                .add(new Excuse(
                        "Ich habe von einem Fu�ballspiel getr�umt und als ich aufstehen musste, gab es eine Verl�ngerung. Seien Sie froh, dass es nicht zum Elfmeterschie�en kam.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY)
                                .addTag(Tag.AGGRESSIVE)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE))
                .add(new Excuse("Sorry, aber mein Bus hat sich verfahren", ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE))
                .add(new Excuse(
                        "Der Bus hatte ein Hydraulikproblem mit den T�ren, sodass diese nicht mehr zu gingen und wir warten mussten, bis ein Ersatzbus kam.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE))
                .add(new Excuse("Der Regen hat unser Projekt zerst�rt", ScenarioType.DELAYED_SUBMISSION)
                        .addTag(Tag.RAINY)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE))
                .add(new Excuse("Jaja, der erste Schnee! Da hatte nat�rlich gleich wieder die U-Bahn Versp�tung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.SNOW)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE))
                .add(new Excuse(
                        "Ich gebe die Arbeit nur aus R�cksicht auf Sie nicht ab. Ich wei� ja, dass Sie bis oben hin eingedeckt sind und jetzt sicherlich nicht auch noch meine Hausarbeit korrigieren wollen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.FUNNY)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE))
                .add(new Excuse(
                        "Ich h�tte die Hausarbeit ohne Weiteres p�nktlich abgeben k�nnen, aber sie soll doch inhaltlich auch brillieren.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.FUNNY)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE))
                .add(new Excuse(
                        "Die Arbeit muss heute abgegeben werden? Das habe ich v�llig missverstanden, ich habe mir den $nextWeekDate$ notiert. Das ist mir ja noch nie passiert.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE));

        return this;
    }

}
