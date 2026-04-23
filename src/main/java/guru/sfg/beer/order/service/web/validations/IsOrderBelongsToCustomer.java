package guru.sfg.beer.order.service.web.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsOrderBelongsToCustomerValidator.class)
public @interface IsOrderBelongsToCustomer {
    String message() default "Order does not belong to the specified customer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
