package guru.sfg.beer.order.service.web.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsOrderAllocatedValidator.class)
public @interface IsOrderAllocated {
    String message() default "{guru.sfg.beer.order.service.IsOrderAllocated.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
