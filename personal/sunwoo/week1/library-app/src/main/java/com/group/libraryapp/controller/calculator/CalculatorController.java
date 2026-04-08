package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import com.group.libraryapp.dto.user.response.CalculatorCalcResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculatorController {

    @GetMapping("/add") //GET /add
    public int addTwoNumbers(CalculatorAddRequest request) {
        return request.getNumber1() + request.getNumber2();
    }

    @PostMapping("/multiply") // POST /multiply
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNumber1() * request.getNumber2();
    }

    @GetMapping("/api/v1/calc") //GET /api/v1/calc
    public CalculatorCalcResponse calcTwoNumbers(CalculatorAddRequest request) {
        int add = request.getNumber1() + request.getNumber2();
        int multiply = request.getNumber1() * request.getNumber2();
        int minus= request.getNumber1() - request.getNumber2();

        return new CalculatorCalcResponse(add, multiply, minus);
    }
}
