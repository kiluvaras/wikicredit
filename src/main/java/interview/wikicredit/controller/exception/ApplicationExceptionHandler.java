package interview.wikicredit.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<Object> handleApplicationExceptions(ApplicationException e) {
        logException(e.getErrorCode(), e.getMessage());
        RestErrorMessage restErrorMessage = new RestErrorMessage(e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleAllOtherExceptions(Exception e) {
        logException(ErrorCode.SERVER_RUNTIME_ERROR, e.getMessage());
        RestErrorMessage restErrorMessage = new RestErrorMessage(ErrorCode.SERVER_RUNTIME_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    private void logException(ErrorCode errorCode, String message) {
        log.error("{}: {}", errorCode, message);
    }
}