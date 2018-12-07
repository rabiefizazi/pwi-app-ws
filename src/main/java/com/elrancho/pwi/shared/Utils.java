package com.elrancho.pwi.shared;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.elrancho.pwi.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Utils {

	public static final Random RANDOM = new SecureRandom();

	public static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public String generateItemId(int length) {

		return generateRandomString(length);
	}

	public String generateInventoryCountId(int length) {

		return generateRandomString(length);
	}

	public String generateUserId(int length) {
		return generateRandomString(length);

	}

	public String generateRandomString(int length) {

		StringBuilder randomString = new StringBuilder();

		for (int i = 0; i < length; i++)
			randomString.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));

		return new String(randomString);
	}

	public LocalDateTime getTodaysDate() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String s = LocalDateTime.now().format(formatter);

		return LocalDateTime.parse(s, formatter);

	}

	public LocalDate getWeekEndDate() {

//		Added this condition to check if it is Sunday, so to allow the managers to still do inventory on that day as well.
		if (LocalDate.now().getDayOfWeek().toString().equals("SUNDAY"))
			return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));
		else
			return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

	}

	public static boolean hasTokenExpired(String token) {
		// TODO Auto-generated method stub

		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token).getBody();

		Date tokenExpirationDate = claims.getExpiration();
		Date todaysDate = new Date();

		return tokenExpirationDate.before(todaysDate);
	}

	public String generateEmailConfirmationToken(String publicUserId) {

		String token = Jwts.builder().setSubject(publicUserId)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();

		return token;
	}

	public String generatePasswordResetToken(String userIdString) {
		
		String token = Jwts.builder().setSubject(userIdString)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
		return token;
	}
}
