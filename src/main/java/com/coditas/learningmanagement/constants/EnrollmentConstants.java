package com.coditas.learningmanagement.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class EnrollmentConstants {
    private EnrollmentConstants(){

    }
    public static final String ALREADY_ENROLLED="Course Already Enrolled";
    public static final String ENROLLED="Course Enrolled Successfully";
    public static final String NOT_ENROLLED="Course Not Enrolled";
    public static final String ASSIGNMENT_FORBIDEN="Please complete the course to access the assignment";

}
