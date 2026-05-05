package com.coditas.learningmanagement.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class EndPoints {
    private EndPoints(){

    }

    public static final String COURSE="/lms/v1/course/**";
    public static final String LECTURE="/lms/v1/lecture/**";
    public static final String LECTURE_PROGRESS="/lms/v1/progress/**";
    public static final String ENROLL="/lms/v1/enroll/**";
    public static final String AUTH="/lms/v1/auth/**";

}
