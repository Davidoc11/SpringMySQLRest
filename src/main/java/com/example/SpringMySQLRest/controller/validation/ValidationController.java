package com.example.SpringMySQLRest.controller.validation;

import com.example.SpringMySQLRest.model.ErrorMessage;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author David
 */
@ControllerAdvice
public class ValidationController {

    @Autowired
    private MessageSource msgSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ErrorMessage> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return processFieldError(result.getFieldErrors());
    }

    private List<ErrorMessage> processFieldError(List<FieldError> fieldErrors) {

        List<ErrorMessage> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            if (fieldError != null) {
                Locale currentLocale = LocaleContextHolder.getLocale();
                String msg = msgSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale);
                ErrorMessage error = new ErrorMessage(MessageType.ERROR, msg);
                errors.add(error);
            }
        }

        return errors;
    }
}
