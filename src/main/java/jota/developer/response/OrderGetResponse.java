package jota.developer.response;

import jota.developer.domain.Client;
import jota.developer.domain.School;
import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeDown;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGetResponse {
    private Long orderId;
    private Client client;
    private UniformType uniformType;
    private UniformSizeUp uniformSizeUp;
    private UniformSizeDown uniformSizeDown;
    private Integer quantity;
    private School school;
    private LocalDate deliveryDate;
    private LocalDate purchaseDate;
    private String details;
    private StatusPayment statusPayment;
    private Double moneyGiven;
}
