package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class BeerServiceRestTemplateImpl implements BeerService {
    private static final String BEER_PATH_V1 = "/api/v1/beer/";
    private static final String BEER_UPC_PATH_V1 = "/api/v1/beer/upc/";
    private final RestTemplate restTemplate;

    @Setter
    private String beerServiceHost;

    public BeerServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID beerId) {
        return Optional.ofNullable(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + beerId.toString(), BeerDto.class));
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.ofNullable(restTemplate.getForObject(beerServiceHost + BEER_UPC_PATH_V1 + upc, BeerDto.class));
    }
}
