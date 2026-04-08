package com.group.libraryapp.controller.assign1;

import com.group.libraryapp.dto.assign1.response.DayOfTheWeekResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class DayOfTheWeekControler {
        @GetMapping("/api/v1/day-of-the-week")
        public DayOfTheWeekResponse getDayOfTheWeek(@RequestParam String date) {
            LocalDate localdate = LocalDate.parse(date);
            String dayOfTheWeek = localdate.getDayOfWeek().toString().substring(0,3);
            return new DayOfTheWeekResponse(dayOfTheWeek);

        }

    }

