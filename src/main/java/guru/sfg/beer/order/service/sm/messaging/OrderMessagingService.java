package guru.sfg.beer.order.service.sm.messaging;

import com.brewery.model.BeerOrderDto;
import com.brewery.model.events.AllocationFailureEvent;
import com.brewery.model.events.DeallocateOrderRequest;
import com.brewery.model.events.ValidateOrderRequest;

import java.util.UUID;

public interface OrderMessagingService {
    void sendValidateOrder(UUID orderId, ValidateOrderRequest request);

    void sendAllocateOrder(UUID orderId, BeerOrderDto dto);

    void sendDeallocateOrder(UUID orderId, DeallocateOrderRequest request);

    void sendAllocationFailure(UUID orderId, AllocationFailureEvent event);
}
