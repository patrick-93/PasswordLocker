package com.example.passwordlocker.controllers;

import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String showLogs(Model model) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if current user is a regular user, return unauthorized page if true
        if (currentUser.getRoles().equals("USER")) {
            return "errors/unauthorized-access";
        }
        // User has access so show logs
        model.addAttribute("logs", logRepository.findAllByOrderByTimestampDesc());
        return "logs/logs";
    }

    @PostMapping(value = "/search") //, params = {"year", "month", "day"}
    public String searchLogs(
            @RequestParam(name = "year", required = false) int year,
            @RequestParam(name = "month", required = false) int month,
            @RequestParam(name = "day", required = false) int day,
            Model model) {

        System.out.println("\n\nReceived year: " + year + ", received month: " + month + ", received day: " + day + "\n\n");

        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Return an error if the currentUser isn't an ADMIN or AUDITOR
        if (currentUser.getRoles().equals("USER")) { return "errors/unauthorized-access"; }

        // Now Check if we have a valid year, month, and day
        boolean checkYear = validateYear(year);
        boolean checkMonth = validateMonth(month);
        boolean checkDay = validateDay(day);


        // Get a list of all logs and create a new ArrayList to add logs to by year
        List<Log> allLogs = logRepository.findAllByOrderByTimestampDesc();
        ArrayList<Log> searchedLogs = new ArrayList<>();

        // Create a new calendar instance to get year/month/day of timestamp
        Calendar cal = Calendar.getInstance();

        // We received a valid year, month, and day so compare all
        if (checkYear && checkMonth && checkDay) {
            for (Log log : allLogs) {
                // Set the calendar instance to the current timestamp of each log
                cal.setTimeInMillis(log.getTimestamp().getTime());

                System.out.println("\n\nLooping through lots, currently have time of " +
                        "Year: " + cal.get(Calendar.YEAR) + ", Month: " + cal.get(Calendar.MONTH) +
                        ", Day: " + cal.get(Calendar.DAY_OF_MONTH));

                if (
                        cal.get(Calendar.YEAR) == year
                                && cal.get(Calendar.MONTH) + 1 == month
                                && cal.get(Calendar.DAY_OF_MONTH) == day) {
                    searchedLogs.add(log);
                }
            }
        }
        model.addAttribute("logs", searchedLogs);
        return "logs/logs";
    }


/*
    @GetMapping(value = "/search", params = {"year", "month"})
    public String searchLogs(
            @RequestParam(name = "year", required = false) int year,
            @RequestParam(name = "month", required = false) int month,
            Model model) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Return an error if the currentUser isn't an ADMIN or AUDITOR
        if (currentUser.getRoles().equals("USER")) { return "errors/unauthorized-access"; }

        // Now Check if we have a valid year, month, and day
        boolean checkYear = validateYear(year);
        boolean checkMonth = validateMonth(month);

        // Get a list of all logs and create a new ArrayList to add logs to by year
        List<Log> allLogs = logRepository.findAllByOrderByTimestampDesc();
        ArrayList<Log> searchedLogs = new ArrayList<>();

        // Create a new calendar instance to get year/month/day of timestamp
        Calendar cal = Calendar.getInstance();

        // We received a valid year, month, and day so compare all
        if (checkYear && checkMonth) {
            for (Log log : allLogs) {
                // Set the calendar instance to the current timestamp of each log
                cal.setTimeInMillis(log.getTimestamp().getTime());
                if (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month) {
                    searchedLogs.add(log);
                }
            }
        }
        model.addAttribute("logs", searchedLogs);
        return "logs/logs";
    }

    @GetMapping(value = "/search", params = {"year"})
    public String searchLogs(
            @RequestParam(name = "year", required = false) int year,
            Model model) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Return an error if the currentUser isn't an ADMIN or AUDITOR
        if (currentUser.getRoles().equals("USER")) { return "errors/unauthorized-access"; }

        // Now Check if we have a valid year, month, and day
        boolean checkYear = validateYear(year);

        // Get a list of all logs and create a new ArrayList to add logs to by year
        List<Log> allLogs = logRepository.findAllByOrderByTimestampDesc();
        ArrayList<Log> searchedLogs = new ArrayList<>();

        // Create a new calendar instance to get year/month/day of timestamp
        Calendar cal = Calendar.getInstance();

        // We received a valid year, month, and day so compare all
        if (checkYear) {
            for (Log log : allLogs) {
                // Set the calendar instance to the current timestamp of each log
                cal.setTimeInMillis(log.getTimestamp().getTime());
                if (cal.get(Calendar.YEAR) == year) {
                    searchedLogs.add(log);
                }
            }
        }
        model.addAttribute("logs", searchedLogs);
        return "logs/logs";
    }
*/

    private boolean validateYear(int year) {
        return year > 2000 && year < 3000;
    }

    private boolean validateMonth(int month) {
        return month > 0 && month < 13;
    }

    private boolean validateDay(int day) {
        return day > 0 && day < 31;
    }
}
