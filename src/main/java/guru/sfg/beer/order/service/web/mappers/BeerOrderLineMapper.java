package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import com.brewery.model.BeerOrderLineDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);
//    BeerOrderLineDto beerOrderLineToDtoEnriched(BeerOrderLine line);
    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
