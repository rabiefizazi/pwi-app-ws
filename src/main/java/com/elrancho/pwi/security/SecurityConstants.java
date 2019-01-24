package com.elrancho.pwi.security;

import com.elrancho.pwi.SpringApplicationContext;

public class SecurityConstants {

	public  static final String SIGN_UP_URL="/users/new";
	public  static final String STORES_URL="/stores/";
	public  static final String VERIFICATION_EMAIL_URL="/users/email-verification";
	
	public static final String TOKEN_PREFIX= "Bearer ";
	public static final String HEADER_STRING = "authorization";
	public static final String TOKEN_REQUEST_PARAM = "token";
	
	public static final long EXPIRATION_TIME = 86400000;
	public static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
	public static final String PASSWORD_RESET_URL = "/users/password-reset";
	
	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties)SpringApplicationContext.getBean("appProperties");
		
		return appProperties.getTokenSecret();
	}
	
	public static String getAccessKey() {
		AppProperties appProperties = (AppProperties)SpringApplicationContext.getBean("appProperties");
		return appProperties.getAwsSesAccessKey();
	}
	public static String getSecretKey() {
		AppProperties appProperties = (AppProperties)SpringApplicationContext.getBean("appProperties");
		return appProperties.getAwsSesSecretKey();
	}
	
}
