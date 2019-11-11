package xyz.mesr.backend.conf;

import java.lang.annotation.*;

/**
 * Is used to mark fields which are to be saved/loaded to/from the configuration file
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationValue {
    String name();
}
