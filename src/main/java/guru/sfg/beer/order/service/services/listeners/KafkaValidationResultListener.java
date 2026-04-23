package guru.sfg.beer.order.service.services.listeners;

import com.brewery.model.events.ValidateOrderResult;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("kafka")
public class KafkaValidationResultListener {
    private final BeerOrderManager beerOrderManager;

    @KafkaListener(topics = "${saga.validate-order-response}", groupId = "beer-order-service")
    public void listen(ValidateOrderResult result) {
        final UUID orderId = result.getOrderId();
        log.debug("Kafka - Validation result for order id: {}", orderId);
        beerOrderManager.processValidationResult(orderId, result.getIsValid());
    }
}

