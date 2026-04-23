package guru.sfg.beer.order.service.web.exceptions;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@RequiredArgsConstructor
@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        String fullMessage = constraintViolationException.getMessage().split("\\.")[1];
        String field = fullMessage.split(":")[0];
        String errorMessage = fullMessage.split(":")[1];
        HashMap<String, HashMap<String, String>> exception = new HashMap<>();
        HashMap<String, String> fieldMap = new HashMap<>();
        fieldMap.put("field:", field);
        fieldMap.put("message:", errorMessage);
        exception.put("fieldErrors:", fieldMap);

        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(exception)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        HashMap<String, HashMap<String, String>> exception = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(fieldError -> {
            HashMap<String, String> fieldMap = new HashMap<>();
            fieldMap.put("field:", fieldError.getField());
            fieldMap.put("message:", fieldError.getDefaultMessage());
            exception.put("fieldErrors:", fieldMap);
        });

        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(exception)));
    }
}
