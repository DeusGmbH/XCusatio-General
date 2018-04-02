package com.deusgmbh.xcusatio.data.lecturer;

import java.util.Optional;

import com.deusgmbh.xcusatio.data.StorageUnit;
import com.deusgmbh.xcusatio.data.tags.TagText;

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
                .addTag(TagText.FUNNY)
                .addTag(TagText.HIGH_TRAFFIC))
                .add(new Lecturer("Kruse").addLecture("C")
                        .addLecture("Softwareengineering 1")
                        .addLecture("Softwareengineering 2")
                        .addTag(TagText.FUNNY)
                        .addTag(TagText.SUCKUP));
        return this;
    }
}
