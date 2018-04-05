package com.deusgmbh.xcusatio.data.excuses;

import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.data.StorageUnit;
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

public class ExcusesManager extends StorageUnit<Excuse> {
	private static final long MAX_RECENTLY_USED_LIST_SIZE = 10;
	private ObservableList<String> lastUsedList;

	public ExcusesManager() {
		super(Excuse.class);
	}

	/**
	 * @param amount
	 *            the maximal amount of excuses to return
	 * @returns the most recently used excuses.
	 */
	public ObservableList<String> getSortedByLastUsed() {
		this.lastUsedList = FXCollections.observableArrayList(
				this.get().stream().sorted(Excuse.byLastUsed).map(Excuse::getText).collect(Collectors.toList()));

		this.get().addListener(new ListChangeListener<Excuse>() {
			@Override
			public void onChanged(Change<? extends Excuse> c) {
				while (c.next()) {
					c.getAddedSubList().stream().forEach(addedExcuse -> {
						lastUsedList.remove(addedExcuse.getText());
						lastUsedList.add(0, addedExcuse.getText());
					});
				}
			}
		});

		return this.lastUsedList;
	}

	@Override
	public ExcusesManager addDefaultValues() {
		// TODO: add more default values
		this.add(new Excuse("Die Bahn kam zu sp�t", ScenarioType.LATE_ARRIVAL).addTag(Tag.TRAIN).addTag(Tag.MALE)
				.addTag(Tag.FEMALE).addTag(Tag.HIGH_TRAFFIC))
				.add(new Excuse(
						"Ich habe von einem Fu�ballspiel getr�umt und als ich aufstehen musste, gab es eine Verl�ngerung.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY).addTag(Tag.MALE).addTag(Tag.FEMALE)
								.addTag(Tag.FOOTBALL).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21))
				.add(new Excuse(
						"Ich habe von einem Fu�ballspiel getr�umt und als ich aufstehen musste, gab es eine Verl�ngerung. Seien Sie froh, dass es nicht zum Elfmeterschie�en kam.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.FUNNY).addTag(Tag.AGGRESSIVE).addTag(Tag.MALE)
								.addTag(Tag.FEMALE).addTag(Tag.FOOTBALL).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30))
				.add(new Excuse("Sorry, aber mein Bus hat sich verfahren", ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS)
						.addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.HIGH_TRAFFIC).addTag(Tag.AGE_UNDER_18)
						.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
						.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Der Bus hatte ein Hydraulikproblem mit den T�ren, sodass diese nicht mehr zu gingen und wir warten mussten, bis ein Ersatzbus kam.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.BUS).addTag(Tag.MALE).addTag(Tag.FEMALE)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30))
				.add(new Excuse("Der Regen hat unser Projekt zerst�rt", ScenarioType.DELAYED_SUBMISSION)
						.addTag(Tag.RAINY).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
						.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
						.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse("Jaja, der erste Schnee! Da hatte nat�rlich gleich wieder die Bahn Versp�tung.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.SNOW).addTag(Tag.TRAIN).addTag(Tag.MALE)
								.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
								.addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Ich gebe die Arbeit nur aus R�cksicht auf Sie nicht ab. Ich wei� ja, dass Sie bis oben hin eingedeckt sind und jetzt sicherlich nicht auch noch meine Hausarbeit korrigieren wollen.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY).addTag(Tag.SUCKUP))
				.add(new Excuse(
						"Ich h�tte die Hausarbeit ohne Weiteres p�nktlich abgeben k�nnen, aber sie soll doch inhaltlich auch brillieren.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.FUNNY).addTag(Tag.SUCKUP))
				.add(new Excuse(
						"Die Arbeit muss heute abgegeben werden? Das habe ich v�llig missverstanden, ich habe mir den $nextWeekDate$ notiert. Das ist mir ja noch nie passiert.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.SUCKUP)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50)
								.addTag(Tag.FUNNY))
				.add(new Excuse(
						"Mein Vater hat die Hausaufgabe so genial gel�st, dass er unbedingt vor seinen Kollegen angeben will. Da hat er mein Heft heute mit zur Arbeit genommen.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.SUCKUP))
				.add(new Excuse(
						"Ich habe getr�umt, wie mein Wecker klingelt und ich ihn ausmache. War wohl leider kein Traum.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Ich wollte meine Kommilitonennicht mit klausurbedingten, negativen Schwingungen meinerseits belasten.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY).addTag(Tag.MALE)
								.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
								.addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse("Meine kleine Schwester hat gestern ausprobiert, wie ein Tintenkiller funktioniert.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.FUNNY))
				.add(new Excuse("In der Nacht war Stromausfall und mein Radiowecker hat nicht geklingelt.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Ich habe die Hausaufgaben gemacht, aber leider mit der Zauber-Tinte meiner kleinen Schwester. Nun ist der Zettel wieder leer, wollen sie ihn sehen?",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Als ich heute Morgen in der Tiefgarage aus dem Auto gestiegen bin, ist mir doch glatt die Hose gerissen. Da musste ich nochmal nach Hause fahren und die Hose wechseln.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
								.addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY).addTag(Tag.MALE).addTag(Tag.FEMALE)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50)
								.addTag(Tag.CAR))
				.add(new Excuse(
						"Ich bin vor der Arbeit in eine Polizeikontrolle geraten und die wollten alles sehen. Und das am fr�hen Morgen.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
								.addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY).addTag(Tag.MALE).addTag(Tag.FEMALE)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50)
								.addTag(Tag.CAR))
				.add(new Excuse(
						"Ich bin heute Nacht schlafgewandelt und war schon in der DHBW. Dann muss ich heute doch nicht kommen, oder?",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY).addTag(Tag.MALE)
								.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
								.addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse("Ich w�rde gerne Ihnen die Antwort sagen, aber mein PC zieht gerade Updates.",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY).addTag(Tag.MALE)
								.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
								.addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse("Schon 10 Uhr? Was wollen wir denn zum Mittag heute machen?",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse("Entschuldigen Sie bitte, aber mein Hund hat meine Unterlagen gefressen.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.DOG)
								.addTag(Tag.FUNNY).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21))
				.add(new Excuse(
						"Der Kaffee war heute Morgen so schlecht, dass mich jegliche Energie verlassen hat. Mit der Laune h�tten Sie mich nicht in der Vorlesung haben wollen.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Ich habe bis in die fr�hen Morgenstunden ein geniales Konzept entworfen. Leider hab ich dann verschlafen und das Konzept vergessen.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY).addTag(Tag.MALE)
								.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
								.addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse("Ich dachte die Uhr w�re umgestellt worden.", ScenarioType.LATE_ARRIVAL)
						.addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21).addTag(Tag.FUNNY))
				.add(new Excuse("Aliens haben mich gestern entf�hrt.", ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE)
						.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21).addTag(Tag.FUNNY))
				.add(new Excuse("Entschuldigung, aber die Schlange bei meinem Bio-B�cker wird jeden Morgen l�nger.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.FUNNY))
				.add(new Excuse("Ich konnte leider nicht rechtzeitig kommen� Benzin ist schon wieder teurer geworden.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.CAR))
				.add(new Excuse(
						"Ich habe verschlafen, weil ich gestern SetlX installiert habe und davon fasziniert war und somit nicht schlafen gehen konnte.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.FUNNY).addTag(Tag.SUCKUP))
				.add(new Excuse(
						"Ich habe leider heute Morgen meine Bahn verpasst, da ich kurz vorher erst etwas gegessen hatte und somit nicht rennen konnte.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.TRAIN))
				.add(new Excuse("Entschuldigen Sie bitte, aber mein Rollator hatte einen Platten.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_OVER_50)
								.addTag(Tag.FUNNY))
				.add(new Excuse(
						"Auf leeren Magen l�sst sich nicht lernen, da musste ich mir heute Morgen etwas zu essen machen und habe somit die Bahn verpasst.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.FUNNY).addTag(Tag.TRAIN))
				.add(new Excuse(
						"Ich konnte das Projekt leider nicht fertigstellen, da mein Hamster zum Zahnarzt musste.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.FUNNY))
				.add(new Excuse("Ich konnte das Projekt leider nicht fertigstellen, da meine Oma im Krankenhaus liegt.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30))
				.add(new Excuse(
						"Ich konnte das Projekt leider nicht abgeben, da ich alles in C programmiert hatte und erst danach festgestellt habe, dass man es nicht in dieser tollen Sprache realisieren darf.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.FUNNY).addTag(Tag.SUCKUP))
				.add(new Excuse(
						"Ich bin im Bus eingeschlafen und musste somit nachzahlen und konnte das Ticket zur Uni nicht bezahlen. Deswegen musste ich laufen und bin nun zu sp�t gekommen.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.TRAIN).addTag(Tag.AGE_UNDER_18).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Der Studiendekan hat mich gerade darum gebeten ihm zu helfen die Damen und Herren des Kultusministeriums herumzuf�hren, da sie dazu da waren neue Gelder zu bewilligen, die f�r eine Lohnerh�hung der Dozenten bestimmt sind.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.SUCKUP))
				.add(new Excuse(
						"Ich bin leider zu sp�t, da ich verschlafen habe. Dies resultiert aus der Tatsache, dass ich gestern Abend den Date-Assistenten Eve zu intensiv genutzt habe.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.FUNNY).addTag(Tag.SUCKUP))
				.add(new Excuse(
						"Warum ich zu sp�t bin? Haben Sie nichts besseres zu tun, als sich dar�ber Gedanken zu machen?",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGGRESSIVE))
				.add(new Excuse(
						"Das ist die langweiligste und unn�tigste Vorlesung des ganzen Studiums. Daf�r werde ich keinerlei kognitive Leistungen verschwenden.",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGGRESSIVE))
				.add(new Excuse(
						"Ich bin zu sp�t, weil eine Frau meinte sie k�nnte einparken und musste dann meinen Autospiegel abfahren.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGGRESSIVE))
				.add(new Excuse("Ich bin zu sp�t, weil mein Freund die M�nnergrippe hat.", ScenarioType.LATE_ARRIVAL)
						.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_40)
						.addTag(Tag.FUNNY))
				.add(new Excuse(
						"Ich bin zu sp�t, weil die Herren der Sch�pfung sich in der Bahn unbedingt pr�geln mussten und somit die Bahn anhalten musste um diese raus zu werfen.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50)
								.addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGGRESSIVE))
				.add(new Excuse("Ich bin zu sp�t, weil mein Hubschrauber zur Inspektion musste.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Warum ich das Projekt nicht abgeben kann? Warum sollte ich das denn abgeben wollen? Lassen Sie mich in Ruhe! Ich muss mich da jetzt reinsteigern.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGGRESSIVE))
				.add(new Excuse("Entschuldigen Sie bitte meine Versp�tung, aber die Stra�enbahn hatte eine Versp�tung.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.TRAIN)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50))
				.add(new Excuse(
						"Ich bin ja auch schon mal viel zu fr�h gekommen und jetzt wollte ich mal die �berstunden abfeiern.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Warum ich zu sp�t bin? Das ist doch vollkommen egal, aber lassen Sie uns �ber Ihre neue sch�ne Frisur unterhalten.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.SUCKUP))
				.add(new Excuse("Haben Sie ein neues Parf�m? Das wirkt aber besonnders gut auf Frauen.",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.SUCKUP))
				.add(new Excuse("Lassen Sie mich bitte in Ruhe. Ich bin auf Mayo-Di�t.", ScenarioType.WHEELOFFORTUNE)
						.addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21).addTag(Tag.FUNNY))
				.add(new Excuse("Ich bin nicht zu sp�t! Sie sind zu fr�h!", ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE)
						.addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30)
						.addTag(Tag.AGGRESSIVE))
				.add(new Excuse(
						"Entschuldigen Sie bitte, dass ich mein Projekt nicht abgeben kann, aber ich wollte Ihnen das Projekt nat�rlich in der vorgegebenen Frist per Mail zusenden und dabei hat sich Lotus Notes aufgeh�ngt. Nun funktioniert mein PC nicht mehr und die Festplatte ist formatiert.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.FUNNY).addTag(Tag.SUCKUP))
				.add(new Excuse("Ich hatte ein Problem mit dem Raum-Zeit Kontinuum: Zu viel Raum, zu wenig Zeit.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.FUNNY))
				.add(new Excuse("Mein Wecker hat geklingelt, als ich noch geschlafen habe.", ScenarioType.LATE_ARRIVAL)
						.addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
						.addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40).addTag(Tag.AGE_UNDER_50)
						.addTag(Tag.AGE_OVER_50))
				.add(new Excuse("Sorry, aber mein Bus hat sich verfahren.", ScenarioType.DELAYED_SUBMISSION)
						.addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
						.addTag(Tag.AGE_UNDER_30).addTag(Tag.FUNNY).addTag(Tag.BUS))
				.add(new Excuse(
						"Tut mir Leid, ich war schon so sp�t dran, da konnte ich mir nicht auch noch eine unn�tige Ausrede einfallen lassen.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGGRESSIVE)
								.addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.FUNNY))
				.add(new Excuse("Entschuldigen Sie, aber mein Fahrrad wurde gestohlen.",
						ScenarioType.DELAYED_SUBMISSION).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.BICICLE)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_30)
								.addTag(Tag.AGE_UNDER_40).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Die Zigarette war mal wieder zu lang und ich kann's mir nicht leisten immer halbe Kippen weg zuschmei�en.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Ich hatte gestern Streit mit meiner Freundin. Aus Wut hat sie sich den Wecker gegriffen und nach mir geworfen. Als ich ausgewichen bin, ist er auf den Boden gefallen und kaputt gegangen.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.MALE).addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_30).addTag(Tag.FUNNY))
				.add(new Excuse(
						"Mir war heute fr�h schon wieder so merkw�rdig �bel. Da musste ich erst mal einen Schwangerschaftstest machen.",
						ScenarioType.LATE_ARRIVAL).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21).addTag(Tag.AGE_UNDER_18)
								.addTag(Tag.AGE_UNDER_30))
				.add(new Excuse("F�llt das sonst niemandem auf, dass immer die gleichen Personen dran genommen werden?",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50))
				.add(new Excuse(
						"Ich glaube Sie sind Sexist. Sie nehmen immer nur Frauen dran. Ich werde mich bei dem Dekan beschweren m�ssen.",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.AGGRESSIVE))
				.add(new Excuse(
						"Ich kann das nicht. Ich will mich nicht mit fremden Lorbeeren schm�cken. Mein Sitznachbar hat mir gerade die L�sung vorgesagt. Bitte fragen Sie lieber diesen.",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50).addTag(Tag.SUCKUP))
				.add(new Excuse("Ja, das ist eine sehr gute Frage. Das wollte ich Sie gerade auch fragen.",
						ScenarioType.WHEELOFFORTUNE).addTag(Tag.MALE).addTag(Tag.FEMALE).addTag(Tag.AGE_UNDER_21)
								.addTag(Tag.AGE_UNDER_18).addTag(Tag.AGE_UNDER_30).addTag(Tag.AGE_UNDER_40)
								.addTag(Tag.AGE_UNDER_50).addTag(Tag.AGE_OVER_50));
		return this;
	}

}
