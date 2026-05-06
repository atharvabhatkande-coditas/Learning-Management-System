package com.coditas.learningmanagement.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class AssignmentConstants {
    private AssignmentConstants(){

    }

    public static final String ASSIGNMENT_EXIST="Assignment Already Exists";
    public static final String ASSIGNMENT_NOT_FOUND="Assignment Not Found";

    public static final String ALREADY_SUBMITTED="Assignment already submitted";
    public static final String ASSIGNMENT_SUBMITTED="Assignment submitted successfully";
    public static final String SUBMISSION_FORBIDDEN="Assignment Cannot be Submitted, Please complete the course";
    public static final String ASSIGNMENT_NOT_SUBMITTED="Assignment Not Submitted";
    public static final String ASSIGNMENT_NOT_REVIEWED="Assignment Not Yet Reviewed";


    public static final String CERTIFICATE_NOT_ISSUED="Certificate Not Issued";



}
