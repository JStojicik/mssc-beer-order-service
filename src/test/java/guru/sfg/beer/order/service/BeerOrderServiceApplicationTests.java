package guru.sfg.beer.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestMessagingConfig.class)
public class BeerOrderServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

}
