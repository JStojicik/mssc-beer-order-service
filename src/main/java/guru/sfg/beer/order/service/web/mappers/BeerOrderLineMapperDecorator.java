package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import com.brewery.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {
    private BeerService beerInventoryService;
    private BeerOrderLineMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerOrderLineMapper mapper) {
        this.mapper = mapper;
    }

//    @Override
//    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
//        return mapper.beerOrderLineToDto(line);
//    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        BeerOrderLineDto beerOrderLineDto = mapper.beerOrderLineToDto(line);
        beerInventoryService.getBeerByUpc(line.getUpc()).ifPresent(beer -> {
            beerOrderLineDto.setBeerId(beer.getId());
            beerOrderLineDto.setBeerName(beer.getBeerName());
            beerOrderLineDto.setBeerStyle(beer.getBeerStyle());
            beerOrderLineDto.setPrice(beer.getPrice());
        });
        return beerOrderLineDto;
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto) {
        return mapper.dtoToBeerOrderLine(dto);
    }

}
