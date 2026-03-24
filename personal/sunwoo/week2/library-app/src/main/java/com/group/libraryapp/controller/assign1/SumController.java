package com.group.libraryapp.controller.assign1;

import com.group.libraryapp.dto.assign1.request.SumRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SumController {
    @PostMapping("/api/v1/sum")
    public int sumNumbers(@RequestBody SumRequest request) {
        int sum = 0;
        for (int number : request.getNumbers()) {
            sum += number;
        }
        return sum;
    }
}
