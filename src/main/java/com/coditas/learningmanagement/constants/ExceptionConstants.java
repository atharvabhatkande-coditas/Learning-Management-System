package com.coditas.learningmanagement.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ExceptionConstants {
    private ExceptionConstants(){
    }
    public static final String USER_NOT_FOUND="User Not Found";
    public static final String USER_EXISTS="Account already Exists. “Already have an account? Login";
    public static final String RE_LOGIN="Session Expired. Please Re login";
    public static final String LOGOUT="Logged Out Successfully";
    public static final String PASSWORD_IDENTICAL="New Password cannot be same as old password";


}
