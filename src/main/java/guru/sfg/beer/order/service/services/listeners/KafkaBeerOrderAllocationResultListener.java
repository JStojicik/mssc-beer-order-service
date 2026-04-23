package guru.sfg.beer.order.service.services.listeners;

import com.brewery.model.events.AllocateOrderResult;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("kafka")
public class KafkaBeerOrderAllocationResultListener {
    private final BeerOrderManager beerOrderManager;

    @KafkaListener(topics = "${saga.allocate-order-response}", groupId = "beer-order-service")
    public void listen(AllocateOrderResult result) {
        if (!result.getAllocationError() && !result.getPendingInventory()) {
            //allocated normally
            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrderDto());
        } else if (!result.getAllocationError() && result.getPendingInventory()) {
            //pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(result.getBeerOrderDto());
        } else if (result.getAllocationError()) {
            //allocation error
            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrderDto());
        }
    }
}

 