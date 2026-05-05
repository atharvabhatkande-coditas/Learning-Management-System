package com.coditas.learningmanagement.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class AuthConstants {
    private AuthConstants(){

    }
    public static final String REGISTRATION_SUCCESS="Account Registered";
    public static final String UNAUTHORIZED="Unauthorized";
    public static final String OTP_EXPIRED="Otp Expired Please Reverify your email";
    public static final String OTP_INVALID="Otp Invalid";
    public static final String EMAIL_NOT_VERIFIED="Please Verify your email";
    public static final String CODE_EXPIRED="Code Expired Please Reverify your email";
    public static final String CODE_INVALID="Code Invalid";


}
