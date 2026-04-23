package guru.sfg.beer.order.service.sm.actions;

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

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final OrderMessagingService messagingService;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessageHeader(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));
        messagingService.sendAllocateOrder(UUID.fromString(beerOrderId),
                beerOrderMapper.beerOrderToDto(beerOrder));
        log.debug("Sent Allocation Request for BeerOrder with ID: " + beerOrderId);
    }
//    @Override
//    public void execute(org.springframework.statemachine.StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
//        String beerOrderId = (String) context.getMessageHeader(BeerOrderManagerImpl.ORDER_ID_HEADER);
//        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));
//        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE,
//                beerOrderMapper.beerOrderToDto(beerOrder));
//        log.debug("Sent Allocation Request for BeerOrder with ID: " + beerOrderId);
//    }
}
