package com.ds.expensetracker.authentication.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Testmain {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        // Add 15 minutes to the current date and time
        LocalDateTime newDateTime = now.plusMinutes(15);

        // Convert LocalDateTime to Date
        ZonedDateTime zonedDateTime = newDateTime.atZone(ZoneId.systemDefault());
        Date newDate = Date.from(zonedDateTime.toInstant());

        // Print the result
        System.out.println("Current date and time: " + Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println("Date and time after 15 minutes: " + newDate);
    }
}
