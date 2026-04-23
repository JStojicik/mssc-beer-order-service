package guru.sfg.beer.order.service.services.listeners;

import com.brewery.model.events.ValidateOrderResult;
import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.beer.order.service.sm.messaging.SagaChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Profile("jms")
@RequiredArgsConstructor
@Component
public class ValidationResultListener {
    private final BeerOrderManager beerOrderManager;
    @JmsListener(destination = "${saga.validate-order-response}")
    public void listen(ValidateOrderResult result) {
        final UUID orderId = result.getOrderId();
        log.debug("Validation result for order id: {}", orderId);
        beerOrderManager.processValidationResult(orderId, result.getIsValid());
    }
}
