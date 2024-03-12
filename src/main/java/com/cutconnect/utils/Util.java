package com.cutconnect.utils;

import com.cutconnect.controllers.ScheduleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final Pattern datePattern = Pattern.compile(DATE_REGEX);

    public static boolean isEmailValid(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isDateValid(String date) {
        Matcher matcher = datePattern.matcher(date);
        return matcher.matches();
    }

    public static LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public static void loggerException(String code, String message) {
        logger.error("Erro interno na aplicação. Código do erro: " + code + " " + "Mensage do erro: " + message);
    }
}
