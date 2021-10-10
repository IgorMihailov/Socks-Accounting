package com.accounting.sock.rest;


import com.accounting.sock.entity.Sock;
import com.accounting.sock.service.SockService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class SockRestController {

    @Autowired
    private SockService sockService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/socks/income",
            consumes = "application/json"
    )
    public ResponseEntity<String> socksIncome(@RequestBody Sock sockIncome) {

        sockService.registerSockIncome(sockIncome);
        return new ResponseEntity<>("Income was successful", HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/socks/outcome",
            consumes = "application/json"
    )
    public ResponseEntity<String> socksOutcome(@RequestBody Sock sockOutcome) {

        sockService.registerSockOutcome(sockOutcome);
        return new ResponseEntity<>("Outcome was successful", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/socks")
    public ResponseEntity<String> getSocksCount(@RequestParam(value = "color") String color,
                              @RequestParam(value = "operation") String operation,
                              @RequestParam(value = "cottonPart") Integer cottonPart) {

        Long totalQuantity = sockService.getTotalSockQuantity(color, operation, cottonPart);
        if (totalQuantity == -1) {
            return new ResponseEntity<>("Error: Bad operation!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(totalQuantity.toString(), HttpStatus.OK);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}