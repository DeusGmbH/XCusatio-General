package com.deusgmbh.xcusatio.data.lecturer;

import java.util.Optional;

import com.deusgmbh.xcusatio.data.StorageUnit;

/**
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */
public class LecturerManager extends StorageUnit<Lecturer> {

    public LecturerManager(Class<Lecturer> parameterType) {
        super(parameterType);
    }

    public Optional<Lecturer> getByLecture(String lecture) {
        return this.get()
                .stream()
                .filter(Lecturer.hasLecture(lecture))
                .findFirst();
    }
}
