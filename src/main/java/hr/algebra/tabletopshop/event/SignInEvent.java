package hr.algebra.tabletopshop.event;

import org.springframework.context.ApplicationEvent;

public class SignInEvent extends ApplicationEvent {
    public SignInEvent(Object source) {
        super(source);
    }
}
