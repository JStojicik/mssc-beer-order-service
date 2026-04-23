package guru.sfg.beer.order.service.web.validations;

import guru.sfg.beer.order.service.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@RequiredArgsConstructor
public class IsCustomerExistsValidator implements ConstraintValidator<IsCustomerExists, UUID> {
    private final CustomerRepository customerRepository;

    @Override
    public void initialize(IsCustomerExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID customerId, ConstraintValidatorContext context) {
        return customerId != null &&
                customerRepository.findById(customerId).isPresent();
    }
}
