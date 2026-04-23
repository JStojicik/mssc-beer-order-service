package guru.sfg.beer.order.service.services;

import com.brewery.model.CustomerPagedList;
import org.springframework.data.domain.Pageable;

/**
 * Created by jt on 3/7/20.
 */
public interface CustomerService {

    CustomerPagedList listCustomers(Pageable pageable);

}
