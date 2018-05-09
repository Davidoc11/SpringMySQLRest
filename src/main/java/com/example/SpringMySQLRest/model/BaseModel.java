package com.example.SpringMySQLRest.model;

import com.example.SpringMySQLRest.model.enums.Status;
import java.util.List;
import lombok.Data;

/**
 * @author David
 */
@Data
public class BaseModel<T> {

    private Status status;
    private Integer code;
    private T result;
}
