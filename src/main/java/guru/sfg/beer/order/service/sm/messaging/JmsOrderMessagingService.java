package guru.sfg.beer.order.service.sm.messaging;

import com.brewery.model.BeerOrderDto;
import com.brewery.model.events.AllocationFailureEvent;
import com.brewery.model.events.DeallocateOrderRequest;
import com.brewery.model.events.ValidateOrderRequest;
import guru.sfg.beer.order.service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("jms")
@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jmsTemplate;

    @Override
    public void sendValidateOrder(UUID orderId, ValidateOrderRequest request) {
        jmsTemplate.convertAndSend(SagaChannel.VALIDATE_ORDER.getQueueName(), request);
    }

    @Override
    public void sendAllocateOrder(UUID orderId, BeerOrderDto dto) {
        jmsTemplate.convertAndSend(SagaChannel.ALLOCATE_ORDER.getQueueName(), dto);
    }

    @Override
    public void sendDeallocateOrder(UUID orderId, DeallocateOrderRequest request) {
        jmsTemplate.convertAndSend(SagaChannel.DEALLOCATE_ORDER.getQueueName(), request);
    }

    @Override
    public void sendAllocationFailure(UUID orderId, AllocationFailureEvent event) {
        jmsTemplate.convertAndSend(SagaChannel.ALLOCATE_FAILURE.getQueueName(), event);
    }
}
