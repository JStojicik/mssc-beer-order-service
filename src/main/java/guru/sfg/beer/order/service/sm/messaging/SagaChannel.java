package guru.sfg.beer.order.service.sm.messaging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SagaChannel {

    VALIDATE_ORDER("validate-order"),
    VALIDATE_ORDER_RESPONSE("validate-order-response"),
    ALLOCATE_ORDER("allocate-order"),
    ALLOCATE_ORDER_RESPONSE("allocate-order-response"),
    ALLOCATE_FAILURE("allocation-failure"),
    DEALLOCATE_ORDER("deallocate-order");

    private final String queueName;
}
