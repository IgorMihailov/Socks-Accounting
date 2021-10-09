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
    public ResponseEntity socksIncome(@RequestBody Sock sockIncome) {

        sockService.registerSockIncome(sockIncome);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/socks/outcome",
            consumes = "application/json"
    )
    public ResponseEntity socksOutcome(@RequestBody Sock sockOutcome) {

        sockService.registerSockOutcome(sockOutcome);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/socks")
    public long getSocksCount(@RequestParam(value = "color") String color,
                              @RequestParam(value = "operation") String operation,
                              @RequestParam(value = "cottonPart") Integer cottonPart) {

        return sockService.getTotalSockQuantity(color, operation, cottonPart);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}