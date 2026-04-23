package guru.sfg.beer.order.service;
import com.brewery.model.BeerOrderDto;
import com.brewery.model.events.AllocationFailureEvent;
import com.brewery.model.events.DeallocateOrderRequest;
import com.brewery.model.events.ValidateOrderRequest;
import guru.sfg.beer.order.service.sm.messaging.OrderMessagingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@TestConfiguration
public class TestMessagingConfig {

    @Bean
    public OrderMessagingService orderMessagingService() {
        return new OrderMessagingService() {
            @Override
            public void sendValidateOrder(UUID orderId, ValidateOrderRequest request) {}

            @Override
            public void sendAllocateOrder(UUID orderId, BeerOrderDto dto) {}

            @Override
            public void sendDeallocateOrder(UUID orderId, DeallocateOrderRequest request) {}

            @Override
            public void sendAllocationFailure(UUID orderId, AllocationFailureEvent event) {}
        };
    }
}
