package guru.sfg.beer.order.service.web.validations;

import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@RequiredArgsConstructor
public class IsOrderExistsValidator implements ConstraintValidator<IsOrderExists, UUID> {

    private final BeerOrderRepository beerOrderRepository;

    @Override
    public void initialize(IsOrderExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID orderId, ConstraintValidatorContext context) {
        return orderId != null &&
                beerOrderRepository.findById(orderId).isPresent();
    }
}
