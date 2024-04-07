package kolesov.maksim.mapping.map.config;

import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.exception.ForbiddenException;
import kolesov.maksim.mapping.map.exception.NotFoundException;
import kolesov.maksim.mapping.map.exception.ServiceException;
import kolesov.maksim.mapping.map.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseDto<Void>> forbidden(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto.<Void>builder()
                        .success(false)
                        .error(e.getMessage())
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto<Void>> notFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.<Void>builder()
                .success(false)
                .error(e.getMessage())
                .build());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseDto<Void>> serviceException(ServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<Void>builder()
                .success(false)
                .error(e.getMessage())
                .build());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<ResponseDto<Void>> serviceRuntimeException(ServiceRuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<Void>builder()
                .success(false)
                .error(e.getMessage())
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Void>> illegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseDto.<Void>builder()
                .success(false)
                .error(e.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> exception(Exception e) {
        log.error("Exception: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.<Void>builder()
                .success(false)
                .error("Internal server error")
                .build());
    }

}
