package guru.sfg.beer.order.service.sm.actions;

import com.brewery.model.events.DeallocateOrderRequest;
import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.services.BeerOrderManagerImpl;
import guru.sfg.beer.order.service.sm.messaging.OrderMessagingService;
import guru.sfg.beer.order.service.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DealocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    //    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final OrderMessagingService messagingService;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            messagingService.sendDeallocateOrder(UUID.fromString(beerOrderId),
                    DeallocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
            log.debug("Sent Deallocation Request for BeerOrder with ID: {}", beerOrderId);
        }, () -> log.error("Beer Order not found for ID: {}", beerOrderId));
    }

//    @Override
//    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
//        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
//        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));
//
//        beerOrderOptional.ifPresentOrElse(beerOrder -> {
//            jmsTemplate.convertAndSend(JmsConfig.DEALLOCATE_ORDER_QUEUE,
//                    DeallocateOrderRequest.builder()
//                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
//                            .build());
//            log.debug("Sent Deallocation Request for BeerOrder with ID: {}", beerOrderId);
//        }, () -> log.error("Beer Order not found for ID: {}", beerOrderId));
//    }
}
