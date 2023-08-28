package hr.algebra.tabletopshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(final String message) {
        super(message);
    }
    
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public NotFoundException(final Throwable cause) {
        super(cause);
    }
    
    public static Supplier<NotFoundException> supplier(String message) {
        return () -> new NotFoundException(message);
    }
    
    public static Supplier<NotFoundException> supplier(String message, Throwable cause) {
        return () -> new NotFoundException(message, cause);
    }
    
    public static Supplier<NotFoundException> supplier(Throwable cause) {
        return () -> new NotFoundException(cause);
    }
    
}
