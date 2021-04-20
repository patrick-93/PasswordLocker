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

        /* Commenting out this block, regular users should have access to view the logs page
           to see who changed an accounts password
        // Check if current user is a regular user, return unauthorized page if true
        if (currentUser.getRoles().equals("USER")) {
            return "errors/unauthorized-access";
        }
        */

        // User has access so show logs
        model.addAttribute("logs", logRepository.findAllByOrderByTimestampDesc());
        return "logs/logs";
    }

    @PostMapping(value = "/search-year-month-day") //, params = {"year", "month", "day"}
    public String searchLogs(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "day") int day,
            Model model) {

        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        /* Commenting out this block, regular users should have access to view the logs page
           to see who changed an accounts password
        // Return an error if the currentUser isn't an ADMIN or AUDITOR
        if (currentUser.getRoles().equals("USER")) { return "errors/unauthorized-access"; }
         */

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
                if (
                        cal.get(Calendar.YEAR) == year
                                && cal.get(Calendar.MONTH) + 1 == month
                                && cal.get(Calendar.DAY_OF_MONTH) == day) {
                    searchedLogs.add(log);
                }
            }
        } else {
            // Validation of the year/month/date params failed, show generic error
            return "errors/generic-error";
        }
        model.addAttribute("logs", searchedLogs);
        return "logs/logs";
    }

    @PostMapping(value = "/search-year-month") //, params = {"year", "month", "day"}
    public String searchLogs(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            Model model) {

        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        /* Commenting out this block, regular users should have access to view the logs page
           to see who changed an accounts password
        // Return an error if the currentUser isn't an ADMIN or AUDITOR
        if (currentUser.getRoles().equals("USER")) { return "errors/unauthorized-access"; }
         */


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

                if (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) + 1 == month) {
                    searchedLogs.add(log);
                }
            }
        } else {
            // Validation of the year/month/date params failed, show generic error
            return "errors/generic-error";
        }
        model.addAttribute("logs", searchedLogs);
        return "logs/logs";
    }

    @PostMapping(value = "/search-year") //, params = {"year", "month", "day"}
    public String searchLogs(
            @RequestParam(name = "year") int year,
            Model model) {

        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        /* Commenting out this block, regular users should have access to view the logs page
           to see who changed an accounts password
        // Return an error if the currentUser isn't an ADMIN or AUDITOR
        if (currentUser.getRoles().equals("USER")) { return "errors/unauthorized-access"; }
         */

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
        } else {
            // Validation of the year/month/date params failed, show generic error
            return "errors/generic-error";
        }
        model.addAttribute("logs", searchedLogs);
        return "logs/logs";
    }

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
