package guru.sfg.beer.order.service.web.validations;

import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@RequiredArgsConstructor
public class IsOrderAllocatedValidator implements ConstraintValidator<IsOrderAllocated, UUID> {

    private final BeerOrderRepository beerOrderRepository;

    @Override
    public void initialize(IsOrderAllocated constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID orderId, ConstraintValidatorContext context) {
        if (orderId == null || beerOrderRepository.findById(orderId).isEmpty()) {
            return true;
        }
        return beerOrderRepository.findById(orderId)
                .map(order -> order.getOrderStatus() == BeerOrderStatusEnum.ALLOCATED)
                .orElse(false);
    }
}
