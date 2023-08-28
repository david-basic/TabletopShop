package hr.algebra.tabletopshop.exceptions;

import java.util.function.Supplier;

public class DbEntityNotFoundException extends NotFoundException {
    public DbEntityNotFoundException() {
        super("There is no such entity in database!");
    }
    
    public DbEntityNotFoundException(Throwable cause) {
        super("There is no such entity in database!", cause);
    }
    
    public static Supplier<DbEntityNotFoundException> supplier() {
        return DbEntityNotFoundException::new;
    }
}
