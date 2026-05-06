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
    public static final String EMPLOYEE ="/lms/v1/employee/**";
    public static final String ASSIGNMENT="/lms/v1/assignment/**";
    public static final String ASSIGNMENT_SUBMISSION="/lms/v1/submission/**";
    public static final String CERTIFICATE="/lms/v1/certificate/**";

}
