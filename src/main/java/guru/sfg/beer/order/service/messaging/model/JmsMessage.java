package guru.sfg.beer.order.service.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JmsMessage implements Serializable {
    private static final long serialVersionUID = 5998043351088704079L;

    private UUID id;
    private String message;
}
