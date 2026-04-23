package guru.sfg.beer.order.service.sm.messaging;

import com.brewery.model.BeerOrderDto;
import com.brewery.model.events.AllocationFailureEvent;
import com.brewery.model.events.DeallocateOrderRequest;
import com.brewery.model.events.ValidateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("kafka")
@Service
@RequiredArgsConstructor
public class KafkaOrderMessagingService implements OrderMessagingService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendValidateOrder(UUID orderId, ValidateOrderRequest request) {
        kafkaTemplate.send(SagaChannel.VALIDATE_ORDER.getQueueName(), orderId.toString(), request);
    }

    @Override
    public void sendAllocateOrder(UUID orderId, BeerOrderDto dto) {
        kafkaTemplate.send(SagaChannel.ALLOCATE_ORDER.getQueueName(), orderId.toString(), dto);
    }

    @Override
    public void sendDeallocateOrder(UUID orderId, DeallocateOrderRequest request) {
        kafkaTemplate.send(SagaChannel.DEALLOCATE_ORDER.getQueueName(), orderId.toString(), request);
    }

    @Override
    public void sendAllocationFailure(UUID orderId, AllocationFailureEvent event) {
        kafkaTemplate.send(SagaChannel.ALLOCATE_FAILURE.getQueueName(), orderId.toString(), event);
    }
}
