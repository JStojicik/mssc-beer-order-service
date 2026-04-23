package guru.sfg.beer.order.service.sm.actions;

import com.brewery.model.events.AllocationFailureEvent;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.services.BeerOrderManagerImpl;
import guru.sfg.beer.order.service.sm.messaging.OrderMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    //    private final JmsTemplate jmsTemplate;
    private final OrderMessagingService messagingService;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        messagingService.sendAllocationFailure(UUID.fromString(beerOrderId), AllocationFailureEvent.builder()
                .orderId(UUID.fromString(beerOrderId))
                .build());
        log.debug("Sent Allocation Failure Message to queue for order ID: {}", beerOrderId);
    }

//    @Override
//    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
//        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
//        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_FAILURE_QUEUE, AllocationFailureEvent.builder()
//                .orderId(UUID.fromString(beerOrderId))
//                .build());
//        log.debug("Sent Allocation Failure Message to queue for order ID: {}", beerOrderId);
//    }
}
