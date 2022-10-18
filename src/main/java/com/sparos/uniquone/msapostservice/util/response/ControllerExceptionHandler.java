package com.sparos.uniquone.msapostservice.util.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /*@ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.FAIL_CODE);
        response.setData(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
*/
    @ExceptionHandler(UniquOneServiceException.class)
    public ResponseEntity<?> applicationHandler(UniquOneServiceException e){
        log.error("Error : {}", e.toString());
//        UniquOneServiceException response = UniquOneServiceException.of(e.getExceptionCode(), e.getResult());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("response");
    }
}
