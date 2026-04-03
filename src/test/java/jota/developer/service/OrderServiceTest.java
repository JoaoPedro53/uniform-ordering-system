package jota.developer.service;

import jota.developer.domain.Client;
import jota.developer.domain.Order;
import jota.developer.domain.School;
import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import jota.developer.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService service;
    @Mock
    private OrderRepository repository;
    private final List<Order> ordersList = new ArrayList<>();

    @BeforeEach
    void init(){
        var order1 = Order.builder().orderId(10L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order2 = Order.builder().orderId(2L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        var order3 = Order.builder().orderId(9L).moneyGiven(50.0).statusPayment(StatusPayment.PENDING_PAYMENT)
                .deliveryDate(LocalDate.of(2026, 03, 20)).purchaseDate(LocalDateTime.now())
                .details("").quantity(1).school(new School("Livramento")).uniformType(UniformType.SHIRT)
                .uniformSizeUp(UniformSizeUp.M).client(new Client("João", "82 99760-2347")).build();
        ordersList.addAll(List.of(order1, order2, order3));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("findAll return list with all orders when successful")
    void findAll_ReturnListWithAllOrders_WhenSuccessful(){
        BDDMockito.when(repository.findAll()).thenReturn(ordersList);

        var emptyOrderList = service.listAll(null);
        Assertions.assertThat(emptyOrderList).hasSameElementsAs(ordersList);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("findAll return list orders by give client name")
    void findAll_ReturnListWithOrdersByGiveName_WhenSuccessful(){
        var orderExpected = ordersList.getFirst();
        BDDMockito.when(repository.findByName(orderExpected.getClient().getName())).thenReturn(List.of(orderExpected));

        var emptyOrdersList = service.listAll(orderExpected.getClient().getName());
        org.assertj.core.api.Assertions.assertThat(emptyOrdersList).contains(orderExpected);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("findAll return empty list orders when client name give not found")
    void findAll_ReturnEmptyListWhenClientNameGiveNotFound_WhenSuccessful(){
        BDDMockito.when(repository.findByName("x")).thenReturn(List.of());

        var emptyStudentsList = service.listAll("x");
        Assertions.assertThat(emptyStudentsList).isEmpty();
    }


}