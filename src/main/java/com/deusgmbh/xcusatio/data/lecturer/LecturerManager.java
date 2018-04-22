package com.deusgmbh.xcusatio.data.lecturer;

import java.util.Optional;

import com.deusgmbh.xcusatio.data.DataManager;
import com.deusgmbh.xcusatio.data.tags.Tag;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class LecturerManager extends DataManager<Lecturer> {

    public LecturerManager() {
        super(Lecturer.class);
    }

    public Optional<Lecturer> getByLecture(String lecture) {
        return this.get()
                .stream()
                .filter(Lecturer.hasLecture(lecture))
                .findFirst();
    }

    @Override
    public DataManager<Lecturer> addDefaultValues() {
        this.add(new Lecturer("Stroetmann").addLecture("Algorithmen")
                .addLecture("Statistik")
                .addLecture("Logik")
                .addLecture("Lineare Algebra")
                .addLecture("Analysis")
                .addLecture("Algorithmen")
                .addTag(Tag.FUNNY)
                .addTag(Tag.SETLX))
                .add(new Lecturer("Kruse").addLecture("Programmieren in C")
                        .addLecture("Grundlagen des Softwareengineering 1")
                        .addLecture("Grundlagen des Softwareengineering 2")
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.SUCK_UP))
                .add(new Lecturer("Lohnert").addLecture("Betriebssysteme")
                        .addTag(Tag.BICYCLE)
                        .addTag(Tag.FUNNY))
                .add(new Lecturer("Hofmann").addLecture("Web-Engineering")
                        .addLecture("Programmieren in Java")
                        .addTag(Tag.SUCK_UP)
                        .addTag(Tag.FUNNY)

                        .addTag(Tag.AGGRESSIVE))
                .add(new Lecturer("Glaser").addLecture("Digitaltechnik")
                        .addLecture("Rechnerarchitektur")
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.FOOTBALL)
                        .addTag(Tag.HANGOVER)

                )
                .add(new Lecturer("Stargardt").addLecture("E-Business")
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.DOG)
                        .addTag(Tag.CAT))
                .add(new Lecturer("Reidelbach").addLecture("Netztechnik")

                )
                .add(new Lecturer("H\u00fcbl").addLecture("Statistik")
                        .addLecture("Codierungstheorie")
                        .addLecture("Angewandte Informatik")
                        .addTag(Tag.BICYCLE)
                        .addTag(Tag.FUNNY))
                .add(new Lecturer("Forster").addLecture("Intercultural Communication")
                        .addTag(Tag.AGGRESSIVE))
                .add(new Lecturer("Vilas").addLecture("Web-Engineering 2")
                        .addTag(Tag.HANGOVER)
                        .addTag(Tag.FUNNY))
                .add(new Lecturer("Runge").addLecture("Projektmanagement")
                        .addLecture("Projektmanagement 2")
                        .addLecture("Gesch\u00e4ftsprozesse")
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.FOOTBALL)
                        .addTag(Tag.HANGOVER)

        );
        return this;
    }
}
