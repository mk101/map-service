package kolesov.maksim.mapping.map.annotation;

import kolesov.maksim.mapping.map.model.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasRole {

    Role[] value();

}
