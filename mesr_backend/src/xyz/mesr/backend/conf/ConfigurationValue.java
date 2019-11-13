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

    /*
    This value is not saved to the configuration file and thus does not have any use at the moment
    TODO: Add comments for individual files
     */
    String description() default "[unassigned]";
}
