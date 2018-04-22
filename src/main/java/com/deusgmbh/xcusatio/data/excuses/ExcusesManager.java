package com.deusgmbh.xcusatio.data.excuses;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.data.DataManager;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.data.tags.Tag;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com & Lars.Dittert@de.ibm.com
 *
 */

public class ExcusesManager extends DataManager<Excuse> {
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
        this.add(new Excuse("Die $tramLineLabel$ kam zu sp\u00e4t", ScenarioType.LATE_ARRIVAL).addTag(Tag.TRAIN_DELAYED)
                .addTag(Tag.MALE)
                .addTag(Tag.FEMALE)
                .addTag(Tag.HIGH_TRAFFIC))
                .add(new Excuse(
                        "Ich habe von einem Fu\u00dfballspiel getr\u00e4umt und als ich aufstehen musste, gab es eine Verl\u00e4ngerung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.FOOTBALL)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21))
                .add(new Excuse(
                        "Ich habe von einem Fu\u00dfballspiel getr\u00e4umt und als ich aufstehen musste, gab es eine Verl\u00e4ngerung. Seien Sie froh, dass es nicht zum Elfmeterschie\u00dfen kam.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY)
                                .addTag(Tag.AGGRESSIVE)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.FOOTBALL)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30))
                .add(new Excuse("Sorry, aber mein Bus hat sich verfahren", ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.HIGH_TRAFFIC)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.AGE_BETWEEN_21_AND_30)
                        .addTag(Tag.AGE_BETWEEN_30_AND_50)
                        .addTag(Tag.AGE_OVER_50)
                        .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Der Bus hatte ein Hydraulikproblem mit den T\u00fcren, sodass diese nicht mehr zu gingen und wir warten mussten, bis ein Ersatzbus kam.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30))
                .add(new Excuse("Der Regen hat unser Projekt zerst\u00f6rt", ScenarioType.DELAYED_SUBMISSION)
                        .addTag(Tag.RAINY)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.AGE_BETWEEN_21_AND_30)
                        .addTag(Tag.AGE_BETWEEN_30_AND_50)
                        .addTag(Tag.AGE_OVER_50)
                        .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Jaja, der erste Schnee! $snowHourly$ Niederschlag, das ist unglaublich. Da hatte nat\u00fcrlich gleich wieder die Bahn Versp\u00e4tung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.SNOW)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "$rainHourly$ Niederschlag und direkt ist die Bahn \u00fcberfordert. Mir fehlen die Worte.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.RAINY)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "$windSpeed$ Windgeschwindigkeit. Trump nimm das. Von wegen es w\u00fcrde die Klimaerw\u00e4rmung nicht geben.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.STORM)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich gebe die Arbeit nur aus R\u00fccksicht auf Sie nicht ab. Ich wei\u00df ja, dass Sie mindestens bis $nextWeekDate$ oben hin eingedeckt sind und jetzt sicherlich nicht auch noch meine Hausarbeit korrigieren wollen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse(
                        "Ich h\u00e4tte die Hausarbeit ohne Weiteres p\u00fcnktlich abgeben k\u00f6nnen, aber sie soll doch inhaltlich auch brillieren.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse(
                        "Die Arbeit muss heute abgegeben werden? Das habe ich v\u00f6llig missverstanden, ich habe mir den $nextWeekDate$ notiert. Das ist mir ja noch nie passiert.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.SUCK_UP)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Mein Vater hat die Hausaufgabe so genial gel\u00f6st, dass er unbedingt vor seinen Kollegen angeben will. Da hat er mein Heft heute mit zur Arbeit genommen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse(
                        "Ich habe getr\u00e4umt, wie mein Wecker klingelt und ich ihn ausmache. War wohl leider kein Traum.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich wollte meine Kommilitonennicht mit klausurbedingten, negativen Schwingungen meinerseits belasten.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Meine kleine Schwester hat gestern ausprobiert, wie ein Tintenkiller funktioniert.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("In der Nacht war Stromausfall und mein Radiowecker hat nicht geklingelt.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich habe die Hausaufgaben gemacht, aber leider mit der Zauber-Tinte meiner kleinen Schwester. Nun ist der Zettel wieder leer, wollen sie ihn sehen?",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Als ich heute Morgen in der Tiefgarage aus dem Auto gestiegen bin, ist mir doch glatt die Hose gerissen. Da musste ich nochmal nach Hause fahren und die Hose wechseln.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich bin vor der Arbeit in eine Polizeikontrolle geraten und die wollten alles sehen. Und das am fr\u00fchen Morgen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich bin heute Nacht schlafgewandelt und war schon in der DHBW. Dann muss ich heute doch nicht in $lectureEvent$ kommen, oder?",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Ich w\u00fcrde gerne Ihnen die Antwort sagen, aber mein PC zieht gerade Updates.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Kann ich kurz zur Toilette gehen?", ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.AGE_BETWEEN_21_AND_30)
                        .addTag(Tag.AGE_BETWEEN_30_AND_50)
                        .addTag(Tag.AGE_OVER_50)
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.SOFTWARE_ENGINEERING))
                .add(new Excuse("K\u00f6nnten Sie die Frage wiederholen?", ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                        .addTag(Tag.FEMALE))
                .add(new Excuse("Was ein tolles Wetter, oder? Finden Sie $temperature$ angenehm?",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.WARM)
                                .addTag(Tag.SOFTWARE_ENGINEERING))
                .add(new Excuse("Schon $lectureEvent$ Uhr? Was wollen wir denn heute noch machen?",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Entschuldigen Sie bitte, aber mein Hund hat meine Unterlagen gefressen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.DOG)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21))
                .add(new Excuse(
                        "Der Kaffee war heute Morgen so schlecht, dass mich jegliche Energie verlassen hat. Mit der Laune h\u00e4tten Sie mich nicht in der Vorlesung haben wollen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich habe bis in die fr\u00fchen Morgenstunden ein geniales Konzept entworfen. Leider hab ich dann verschlafen und das Konzept vergessen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Ich dachte die Uhr w\u00e4re umgestellt worden.", ScenarioType.LATE_ARRIVAL)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.FUNNY))
                .add(new Excuse("Aliens haben mich gestern entf\u00fchrt.", ScenarioType.WHEELOFFORTUNE)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Entschuldigung, aber die Schlange bei meinem Bio-B\u00e4cker wird jeden Morgen l\u00e4nger.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich konnte leider nicht rechtzeitig kommen. Benzin ist schon wieder teurer geworden. Ich musste warten bis es g\u00fcnstiger wurde.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich habe verschlafen, weil ich gestern SetlX installiert habe und davon fasziniert war und somit nicht schlafen gehen konnte.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCK_UP)
                                .addTag(Tag.SETLX))
                .add(new Excuse(
                        "Ich habe leider heute Morgen meine Bahn verpasst, da ich kurz vorher erst etwas gegessen hatte und somit nicht rennen konnte.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.TRAIN_DELAYED))
                .add(new Excuse("Entschuldigen Sie bitte, aber mein Rollator hatte einen Platten.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Auf leeren Magen l\u00e4sst sich nicht lernen, da musste ich mir heute Morgen etwas zu essen machen und habe somit die Bahn verpasst.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.TRAIN_DELAYED))
                .add(new Excuse(
                        "Ich konnte das Projekt leider nicht fertigstellen, da mein Hamster zum Zahnarzt musste.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Ich konnte das Projekt leider nicht fertigstellen, da meine Oma im Krankenhaus liegt.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30))
                .add(new Excuse(
                        "Ich konnte das Projekt leider nicht abgeben, da ich alles in C programmiert hatte und erst danach festgestellt habe, dass man es nicht in dieser tollen Sprache realisieren darf.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse(
                        "Ich bin im Bus eingeschlafen und musste somit nachzahlen und konnte das Ticket zur Uni nicht bezahlen. Deswegen musste ich laufen und bin nun zu sp\u00e4t gekommen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Der Studiendekan hat mich gerade darum gebeten ihm zu helfen die Damen und Herren des Kultusministeriums herumzuf\u00fchren, da sie dazu da waren neue Gelder zu bewilligen, die f\u00fcr eine Lohnerh\u00f6hung der Dozenten bestimmt sind.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse(
                        "Ich bin leider zu sp\u00e4t, da ich verschlafen habe. Dies resultiert aus der Tatsache, dass ich gestern Abend den Date-Assistenten Eve zu intensiv genutzt habe.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse(
                        "Warum ich zu sp\u00e4t bin? Haben Sie nichts besseres zu tun, als sich dar\u00fcber Gedanken zu machen?",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "$lectureEvent$. Pff, das ist die langweiligste und unn\u00f6tigste Vorlesung des ganzen Studiums. Daf\u00fcr werde ich keinerlei kognitive Leistungen verschwenden.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Ich bin zu sp\u00e4t, weil eine Frau meinte sie k\u00f6nnte einparken und musste dann meinen Autospiegel abfahren.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse("Ich bin zu sp\u00e4t, weil mein Freund die M\u00e4nnergrippe hat.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich bin zu sp\u00e4t, weil die Herren der Sch\u00f6pfung sich in der Bahn unbedingt pr\u00fcgeln mussten und somit die Bahn anhalten musste um diese raus zu werfen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse("Ich bin zu sp\u00e4t, weil mein Hubschrauber zur Inspektion musste.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "$temperature$. Bei dieser Temperatur ist mein Gehirn leider nicht in der Lage eine sinnvolle Konversation mit Ihnen zu f\u00fchren. Probieren Sie es sp\u00e4ter wieder.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Sie wollen von mir eine Begr\u00fcndung, weil ich nur $minutesPassed$ zu sp\u00e4t bin? Merken Sie selbst, oder?",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse("Haben Sie \u00fcberhaupt Beweise, dass ich zu sp\u00e4t bin?",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Warum ich das Projekt nicht abgeben kann? Warum sollte ich das denn abgeben wollen? Wer sind Sie \u00fcberhaupt, dass Sie mich das fragen? Lassen Sie mich in Ruhe! Ich muss mich da jetzt reinsteigern.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Entschuldigen Sie bitte meine Versp\u00e4tung, aber die Stra\u00dfenbahn hatte eine Versp\u00e4tung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Entschuldigen Sie bitte meine Versp\u00e4tung, aber es gab einen $trafficIncidentTyp$ in $incidentLocationStreet$ ,$incidentLocationCity$.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Finden Sie es nicht auch besorgniserregend wie es auf den Stra\u00dfe in letzter Zeit zugeht? Schon wieder $trafficIncidentTyp$.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Ich bin ja auch schon mal viel zu fr\u00fch gekommen und jetzt wollte ich mal die \u00dcberstunden abfeiern.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Warum ich zu sp\u00e4t bin? Das ist doch vollkommen egal, aber lassen Sie uns \u00fcber Ihre neue sch\u00f6ne Frisur unterhalten.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse("Haben Sie ein neues Parf\u00fcm? Das wirkt augenscheinlich besonders gut auf Frauen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse("Ich bin nicht zu sp\u00e4t! Sie sind zu fr\u00fch!", ScenarioType.LATE_ARRIVAL)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.AGE_BETWEEN_21_AND_30)
                        .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Entschuldigen Sie bitte, dass ich mein Projekt nicht abgeben kann, aber ich wollte Ihnen das Projekt nat\u00fcrlich in der vorgegebenen Frist per Mail zusenden und dabei hat sich Lotus Notes aufgeh\u00e4ngt. Nun funktioniert mein PC nicht mehr und die Festplatte ist formatiert.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse("Ich hatte ein Problem mit dem Raum-Zeit Kontinuum: Zu viel Raum, zu wenig Zeit.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Mein Wecker hat geklingelt, als ich noch geschlafen habe.", ScenarioType.LATE_ARRIVAL)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.AGE_BETWEEN_21_AND_30)
                        .addTag(Tag.AGE_BETWEEN_30_AND_50)
                        .addTag(Tag.AGE_OVER_50))
                .add(new Excuse("Sorry, aber mein Bus hat sich verfahren.", ScenarioType.DELAYED_SUBMISSION)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_BETWEEN_18_AND_21)
                        .addTag(Tag.AGE_BETWEEN_21_AND_30)
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.BUS))
                .add(new Excuse(
                        "Tut mir Leid, ich war schon so sp\u00e4t dran, da konnte ich mir nicht auch noch eine unn\u00f6tige Ausrede einfallen lassen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGGRESSIVE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Entschuldigen Sie, aber mein Fahrrad wurde gestohlen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.BICYCLE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Die Zigarette war mal wieder zu lang und ich kann's mir nicht leisten immer halbe Kippen weg zuschmei\u00dfen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich hatte gestern Streit mit meiner Freundin. Aus Wut hat sie sich den Wecker gegriffen und nach mir geworfen. Als ich ausgewichen bin, ist er auf den Boden gefallen und kaputt gegangen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Mir war heute fr\u00fch schon wieder so merkw\u00fcrdig \u00fcbel. Da musste ich erst mal einen Schwangerschaftstest machen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30))
                .add(new Excuse(
                        "F\u00e4llt das sonst niemandem auf, dass immer die gleichen Personen dran genommen werden?",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Ich glaube Sie sind Sexist. Sie nehmen immer nur Frauen dran. Ich werde mich bei dem Dekan beschweren m\u00fcssen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Ich kann das nicht. Ich will mich nicht mit fremden Lorbeeren schm\u00fccken. Mein Sitznachbar hat mir gerade die L\u00f6sung vorgesagt. Bitte fragen Sie lieber diesen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.SUCK_UP))
                .add(new Excuse("Ja, das ist eine sehr gute Frage. Das wollte ich Sie gerade auch fragen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_BETWEEN_18_AND_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_BETWEEN_21_AND_30)
                                .addTag(Tag.AGE_BETWEEN_30_AND_50)
                                .addTag(Tag.AGE_OVER_50));
        return this;
    }

}
