package com.coditas.learningmanagement.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ExceptionConstants {
    private ExceptionConstants(){
    }
    public static final String USER_NOT_FOUND="User Not Found";
    public static final String USER_EXISTS="User already registered";

}
