package com.deusgmbh.xcusatio.data.lecturer;

import java.util.Optional;

import com.deusgmbh.xcusatio.data.StorageUnit;
import com.deusgmbh.xcusatio.data.tags.Tag;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class LecturerManager extends StorageUnit<Lecturer> {

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
    public StorageUnit<Lecturer> addDefaultValues() {
        this.add(new Lecturer("Stroetmann").addLecture("Algorithmen")
                .addLecture("Statistik")
                .addLecture("Logik")
                .addLecture("Lineare Algebra")
                .addLecture("Analysis")
                .addTag(Tag.FUNNY)
                .addTag(Tag.HIGH_TRAFFIC))
                .add(new Lecturer("Kruse").addLecture("C")
                        .addLecture("Softwareengineering 1")
                        .addLecture("Softwareengineering 2")
                        .addTag(Tag.FUNNY)
                        .addTag(Tag.SUCKUP));
        return this;
    }
}
