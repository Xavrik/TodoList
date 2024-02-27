package xavr.todolist.domain.Exceprions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such element exist")
public class CustomEmptyDataException extends DataAccessException {


    public CustomEmptyDataException(String msg) {
        super(msg);
    }

    public CustomEmptyDataException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
