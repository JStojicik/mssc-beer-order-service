package guru.sfg.beer.order.service.web.mappers;

import com.brewery.model.CustomerDto;
import com.brewery.model.CustomerDto.CustomerDtoBuilder;
import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.Customer;
import guru.sfg.beer.order.service.domain.Customer.CustomerBuilder;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-23T17:03:40+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.31 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public CustomerDto customerToDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDtoBuilder customerDto = CustomerDto.builder();

        customerDto.id( customer.getId() );
        if ( customer.getVersion() != null ) {
            customerDto.version( customer.getVersion().intValue() );
        }
        customerDto.createdDate( dateMapper.asOffsetDateTime( customer.getCreatedDate() ) );
        customerDto.lastModifiedDate( dateMapper.asOffsetDateTime( customer.getLastModifiedDate() ) );
        customerDto.customerName( customer.getCustomerName() );

        return customerDto.build();
    }

    @Override
    public Customer dtoToCustomer(Customer dto) {
        if ( dto == null ) {
            return null;
        }

        CustomerBuilder customer = Customer.builder();

        customer.id( dto.getId() );
        customer.version( dto.getVersion() );
        customer.createdDate( dto.getCreatedDate() );
        customer.lastModifiedDate( dto.getLastModifiedDate() );
        customer.customerName( dto.getCustomerName() );
        customer.apiKey( dto.getApiKey() );
        Set<BeerOrder> set = dto.getBeerOrders();
        if ( set != null ) {
            customer.beerOrders( new HashSet<BeerOrder>( set ) );
        }

        return customer.build();
    }
}
