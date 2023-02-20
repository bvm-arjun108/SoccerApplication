package com.cricket.User.Application.constants;

public class AppConstants {

    // Here we are mentioning the security key directly in the application.
    // In real world scenarios, you should ask your DevOps team to inject this value at runtime/deployment time using CI/CD tools like Githun or Jenkins.
    // Or you can also configure this value as an environment variable inside your production server and the same can be accessed from your code.
    public static final String SECURITY_JWT_SECRET_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String SECURITY_JWT_TOKEN_PREFIX = "Bearer";
    public static final String SECURITY_JWT_HEADER_STRING = "Authorization";
    public static final long SECURITY_JWT_EXPI = 300000000;
}
