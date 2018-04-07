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
        // TODO: add more default values
        this.add(new Excuse("Die $tramLineLabel$ kam zu spät", ScenarioType.LATE_ARRIVAL).addTag(Tag.TRAIN_DELAYED)
                .addTag(Tag.MALE)
                .addTag(Tag.FEMALE)
                .addTag(Tag.HIGH_TRAFFIC))
                .add(new Excuse(
                        "Ich habe von einem Fußballspiel geträumt und als ich aufstehen musste, gab es eine Verlängerung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.FOOTBALL)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21))
                .add(new Excuse(
                        "Ich habe von einem Fußballspiel geträumt und als ich aufstehen musste, gab es eine Verlängerung. Seien Sie froh, dass es nicht zum Elfmeterschießen kam.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY)
                                .addTag(Tag.AGGRESSIVE)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.FOOTBALL)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30))
                .add(new Excuse("Sorry, aber mein Bus hat sich verfahren", ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.HIGH_TRAFFIC)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGE_UNDER_30)
                        .addTag(Tag.AGE_UNDER_40)
                        .addTag(Tag.AGE_UNDER_50)
                        .addTag(Tag.AGE_OVER_50)
                        .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Der Bus hatte ein Hydraulikproblem mit den Türen, sodass diese nicht mehr zu gingen und wir warten mussten, bis ein Ersatzbus kam.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30))
                .add(new Excuse("Der Regen hat unser Projekt zerstört", ScenarioType.DELAYED_SUBMISSION)
                        .addTag(Tag.RAINY)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGE_UNDER_30)
                        .addTag(Tag.AGE_UNDER_40)
                        .addTag(Tag.AGE_UNDER_50)
                        .addTag(Tag.AGE_OVER_50)
                        .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Jaja, der erste Schnee! $snowHourly$ das ist unglaublich. Da hatte natürlich gleich wieder die Bahn Verspätung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.SNOW)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("$rainHourly$ und direkt ist die Bahn überfordert. Mir fehlen die Worte.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.RAINY)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse("$windSpeed$. Trump nimm das. Von wegen es würde die Klimaerwärmung nicht geben.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.STORM)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich gebe die Arbeit nur aus Rücksicht auf Sie nicht ab. Ich weiß ja, dass Sie mindestens bis $nextWeekDate$ oben hin eingedeckt sind und jetzt sicherlich nicht auch noch meine Hausarbeit korrigieren wollen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse(
                        "Ich hätte die Hausarbeit ohne Weiteres pünktlich abgeben können, aber sie soll doch inhaltlich auch brillieren.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse(
                        "Die Arbeit muss heute abgegeben werden? Das habe ich völlig missverstanden, ich habe mir den $nextWeekDate$ notiert. Das ist mir ja noch nie passiert.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.SUCKUP)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Mein Vater hat die Hausaufgabe so genial gelöst, dass er unbedingt vor seinen Kollegen angeben will. Da hat er mein Heft heute mit zur Arbeit genommen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse(
                        "Ich habe geträumt, wie mein Wecker klingelt und ich ihn ausmache. War wohl leider kein Traum.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich wollte meine Kommilitonennicht mit klausurbedingten, negativen Schwingungen meinerseits belasten.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Meine kleine Schwester hat gestern ausprobiert, wie ein Tintenkiller funktioniert.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("In der Nacht war Stromausfall und mein Radiowecker hat nicht geklingelt.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich habe die Hausaufgaben gemacht, aber leider mit der Zauber-Tinte meiner kleinen Schwester. Nun ist der Zettel wieder leer, wollen sie ihn sehen?",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Als ich heute Morgen in der Tiefgarage aus dem Auto gestiegen bin, ist mir doch glatt die Hose gerissen. Da musste ich nochmal nach Hause fahren und die Hose wechseln.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.CAR))
                .add(new Excuse(
                        "Ich bin vor der Arbeit in eine Polizeikontrolle geraten und die wollten alles sehen. Und das am frühen Morgen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.CAR))
                .add(new Excuse(
                        "Ich bin heute Nacht schlafgewandelt und war schon in der DHBW. Dann muss ich heute doch nicht in $lectureEvent$ kommen, oder?",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Ich würde gerne Ihnen die Antwort sagen, aber mein PC zieht gerade Updates.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Schon 10 Uhr? Was wollen wir denn zum Mittag heute machen?",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Entschuldigen Sie bitte, aber mein Hund hat meine Unterlagen gefressen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.DOG)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21))
                .add(new Excuse(
                        "Der Kaffee war heute Morgen so schlecht, dass mich jegliche Energie verlassen hat. Mit der Laune hätten Sie mich nicht in der Vorlesung haben wollen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich habe bis in die frühen Morgenstunden ein geniales Konzept entworfen. Leider hab ich dann verschlafen und das Konzept vergessen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Ich dachte die Uhr wäre umgestellt worden.", ScenarioType.LATE_ARRIVAL)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.FUNNY))
                .add(new Excuse("Aliens haben mich gestern entführt.", ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.FUNNY))
                .add(new Excuse("Entschuldigung, aber die Schlange bei meinem Bio-Bäcker wird jeden Morgen länger.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich konnte leider nicht rechtzeitig kommen. Benzin ist schon wieder teurer geworden. Ich musste warten bis es günstiger wurde.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.CAR)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich habe verschlafen, weil ich gestern SetlX installiert habe und davon fasziniert war und somit nicht schlafen gehen konnte.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse(
                        "Ich habe leider heute Morgen meine Bahn verpasst, da ich kurz vorher erst etwas gegessen hatte und somit nicht rennen konnte.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.TRAIN_DELAYED))
                .add(new Excuse("Entschuldigen Sie bitte, aber mein Rollator hatte einen Platten.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Auf leeren Magen lässt sich nicht lernen, da musste ich mir heute Morgen etwas zu essen machen und habe somit die Bahn verpasst.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.TRAIN_DELAYED))
                .add(new Excuse(
                        "Ich konnte das Projekt leider nicht fertigstellen, da mein Hamster zum Zahnarzt musste.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Ich konnte das Projekt leider nicht fertigstellen, da meine Oma im Krankenhaus liegt.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30))
                .add(new Excuse(
                        "Ich konnte das Projekt leider nicht abgeben, da ich alles in C programmiert hatte und erst danach festgestellt habe, dass man es nicht in dieser tollen Sprache realisieren darf.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse(
                        "Ich bin im Bus eingeschlafen und musste somit nachzahlen und konnte das Ticket zur Uni nicht bezahlen. Deswegen musste ich laufen und bin nun zu spät gekommen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.TRAIN_DELAYED)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Der Studiendekan hat mich gerade darum gebeten ihm zu helfen die Damen und Herren des Kultusministeriums herumzuführen, da sie dazu da waren neue Gelder zu bewilligen, die für eine Lohnerhöhung der Dozenten bestimmt sind.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse(
                        "Ich bin leider zu spät, da ich verschlafen habe. Dies resultiert aus der Tatsache, dass ich gestern Abend den Date-Assistenten Eve zu intensiv genutzt habe.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse(
                        "Warum ich zu spät bin? Haben Sie nichts besseres zu tun, als sich darüber Gedanken zu machen?",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "$lectureEvent$. Pff, das ist die langweiligste und unnötigste Vorlesung des ganzen Studiums. Dafür werde ich keinerlei kognitive Leistungen verschwenden.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Ich bin zu spät, weil eine Frau meinte sie könnte einparken und musste dann meinen Autospiegel abfahren.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse("Ich bin zu spät, weil mein Freund die Männergrippe hat.", ScenarioType.LATE_ARRIVAL)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_30)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGE_UNDER_40)
                        .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich bin zu spät, weil die Herren der Schöpfung sich in der Bahn unbedingt prügeln mussten und somit die Bahn anhalten musste um diese raus zu werfen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse("Ich bin zu spät, weil mein Hubschrauber zur Inspektion musste.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Ich sage nur $newEntryTitle$.", ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGE_UNDER_30)
                        .addTag(Tag.AGE_UNDER_40)
                        .addTag(Tag.AGE_UNDER_50)
                        .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "$temperature$. Bei dieser Temperatur ist mein Gehirn leider nicht in der Lage eine sinnvolle Konversation mit Ihnen zu führen. Probieren Sie es später wieder.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Sie wollen von mir eine Begründung, weil ich nur $minutesPassed$ zu spät bin? Merken Sie selbst, oder?",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse("Haben Sie überhaupt Beweise, dass ich zu spät bin?", ScenarioType.LATE_ARRIVAL)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Warum ich das Projekt nicht abgeben kann? Warum sollte ich das denn abgeben wollen? Wer sind Sie überhaupt, dass Sie mich das fragen? Lassen Sie mich in Ruhe! Ich muss mich da jetzt reinsteigern.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse("Entschuldigen Sie bitte meine Verspätung, aber die Straßenbahn hatte eine Verspätung.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.TRAIN)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Entschuldigen Sie bitte meine Verspätung, aber es gab einen $trafficIncidentTyp$ in $incidentLocationStreet$ ,$incidentLocationCity$.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.CAR)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Entschuldigen Sie bitte meine Verspätung, aber es gab einen $trafficIncidentTyp$ in $incidentLocationStreet$ ,$incidentLocationCity$.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.CAR)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Finden Sie es nicht auch besorgniserregend wie es auf den Straße in letzter Zeit zugeht? Schon wieder $trafficIncidentTyp$.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.CAR)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Ich bin ja auch schon mal viel zu früh gekommen und jetzt wollte ich mal die Überstunden abfeiern.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Warum ich zu spät bin? Das ist doch vollkommen egal, aber lassen Sie uns über Ihre neue schöne Frisur unterhalten.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse("Haben Sie ein neues Parfüm? Das wirkt augenscheinlich besonders gut auf Frauen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse("Lassen Sie mich bitte in Ruhe. Ich bin auf Mayo-Diät.", ScenarioType.WHEELOFFORTUNE)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.FUNNY))
                .add(new Excuse("Ich bin nicht zu spät! Sie sind zu früh!", ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGE_UNDER_30)
                        .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Entschuldigen Sie bitte, dass ich mein Projekt nicht abgeben kann, aber ich wollte Ihnen das Projekt natürlich in der vorgegebenen Frist per Mail zusenden und dabei hat sich Lotus Notes aufgehängt. Nun funktioniert mein PC nicht mehr und die Festplatte ist formatiert.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.FUNNY)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse("Ich hatte ein Problem mit dem Raum-Zeit Kontinuum: Zu viel Raum, zu wenig Zeit.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Mein Wecker hat geklingelt, als ich noch geschlafen habe.", ScenarioType.LATE_ARRIVAL)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGE_UNDER_30)
                        .addTag(Tag.AGE_UNDER_40)
                        .addTag(Tag.AGE_UNDER_50)
                        .addTag(Tag.AGE_OVER_50))
                .add(new Excuse("Sorry, aber mein Bus hat sich verfahren.", ScenarioType.DELAYED_SUBMISSION)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE)
                        .addTag(Tag.AGE_UNDER_18)
                        .addTag(Tag.AGE_UNDER_21)
                        .addTag(Tag.AGE_UNDER_30)
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.BUS))
                .add(new Excuse(
                        "Tut mir Leid, ich war schon so spät dran, da konnte ich mir nicht auch noch eine unnötige Ausrede einfallen lassen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGGRESSIVE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse("Entschuldigen Sie, aber mein Fahrrad wurde gestohlen.",
                        ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.BICICLE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Die Zigarette war mal wieder zu lang und ich kann's mir nicht leisten immer halbe Kippen weg zuschmeißen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Ich hatte gestern Streit mit meiner Freundin. Aus Wut hat sie sich den Wecker gegriffen und nach mir geworfen. Als ich ausgewichen bin, ist er auf den Boden gefallen und kaputt gegangen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.FUNNY))
                .add(new Excuse(
                        "Mir war heute früh schon wieder so merkwürdig übel. Da musste ich erst mal einen Schwangerschaftstest machen.",
                        ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30))
                .add(new Excuse("Fällt das sonst niemandem auf, dass immer die gleichen Personen dran genommen werden?",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50))
                .add(new Excuse(
                        "Ich glaube Sie sind Sexist. Sie nehmen immer nur Frauen dran. Ich werde mich bei dem Dekan beschweren müssen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.AGGRESSIVE))
                .add(new Excuse(
                        "Ich kann das nicht. Ich will mich nicht mit fremden Lorbeeren schmücken. Mein Sitznachbar hat mir gerade die Lösung vorgesagt. Bitte fragen Sie lieber diesen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50)
                                .addTag(Tag.SUCKUP))
                .add(new Excuse("Ja, das ist eine sehr gute Frage. Das wollte ich Sie gerade auch fragen.",
                        ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
                                .addTag(Tag.FEMALE)
                                .addTag(Tag.AGE_UNDER_21)
                                .addTag(Tag.AGE_UNDER_18)
                                .addTag(Tag.AGE_UNDER_30)
                                .addTag(Tag.AGE_UNDER_40)
                                .addTag(Tag.AGE_UNDER_50)
                                .addTag(Tag.AGE_OVER_50));
        return this;
    }

}
