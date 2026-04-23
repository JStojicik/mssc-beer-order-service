package com.brewery.model.events;

import guru.sfg.beer.order.service.domain.BeerOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NewBeerOrderEvent {
    private final BeerOrder beerOrder;
}
