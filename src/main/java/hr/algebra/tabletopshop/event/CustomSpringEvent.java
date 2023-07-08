package hr.algebra.tabletopshop.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class CustomSpringEvent extends ApplicationEvent {
    private String message;
    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    
}
