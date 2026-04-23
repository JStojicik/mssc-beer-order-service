package guru.sfg.beer.order.service.web.validations;

import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class IsOrderBelongsToCustomerValidator implements ConstraintValidator<IsOrderBelongsToCustomer, Object[]> {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(Object[] parameters, ConstraintValidatorContext context) {
        UUID customerId = (UUID) parameters[0];
        UUID orderId = (UUID) parameters[1];

        if (customerId == null || orderId == null) {
            return true;
        } else {
            if (customerRepository.findById(customerId).isEmpty() || beerOrderRepository.findById(orderId).isEmpty()) {
                return true;
            }
        }

        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

        if (beerOrderOptional.isEmpty()) {
            return false;
        }

        BeerOrder beerOrder = beerOrderOptional.get();
        return beerOrder.getCustomer() != null &&
                beerOrder.getCustomer().getId().equals(customerId);
    }
}
