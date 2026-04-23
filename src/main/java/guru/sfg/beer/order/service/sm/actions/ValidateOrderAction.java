package guru.sfg.beer.order.service.sm.actions;

import com.brewery.model.events.ValidateOrderRequest;
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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final OrderMessagingService messagingService;
//    private final JmsTemplate jmsTemplate;

    //    @Override
//    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
//        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
//        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));
//        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
//                .beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
//                .build());
//        log.debug("Sent Validation request to queue for order id: {}", beerOrderId);
//    }
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    messagingService.sendValidateOrder(UUID.fromString(beerOrderId), ValidateOrderRequest.builder()
                            .beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
                }
            });
        }, () -> log.error("Order Not Found. Id: " + beerOrderId));

        log.debug("Sent Validation request to queue for order id " + beerOrderId);
    }
}
