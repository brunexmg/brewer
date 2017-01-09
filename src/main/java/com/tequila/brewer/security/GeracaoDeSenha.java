package com.tequila.brewer.security;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeracaoDeSenha {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
		
		LocalDate date = LocalDate.of(2016, Month.AUGUST, 1);
		System.out.println(date.toEpochDay() * 24 * 60 * 60);
		
	}
}
