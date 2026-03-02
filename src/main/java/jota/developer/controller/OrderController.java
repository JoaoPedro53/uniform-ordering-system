package jota.developer.controller;

import jota.developer.domain.Order;
import jota.developer.mapper.OrderMapper;
import jota.developer.request.OrderPostRequest;
import jota.developer.request.OrderPutRequest;
import jota.developer.response.OrderGetResponse;
import jota.developer.response.OrderPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/orders")
public class OrderController {
    private static final OrderMapper MAPPER = OrderMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<OrderGetResponse>> listAllOrdersOrOrdersByNameClient(@RequestParam(required = false) String name){
        log.info("request received to list all orders, param name: '{}'", name);

        var orders = Order.getOrders();
        var orderGetResponse = MAPPER.toListOrderGetResponse(orders);
        if (name == null) return ResponseEntity.ok(orderGetResponse);

        var response = orders.stream()
                .filter(order -> order.getClient().getName().equalsIgnoreCase(name))
                .map(MAPPER::toOrderGetResponse)
                .toList();

        return ResponseEntity.ok(response);

    }
    @GetMapping("{id}")
    public ResponseEntity<OrderGetResponse> findById(@PathVariable Long id){
        log.info("request received to list order by id: '{}'", id);

        var orders = Order.getOrders();
        OrderGetResponse response = orders.stream().filter(order -> order.getOrderId().equals(id))
                .findFirst()
                .map(MAPPER::toOrderGetResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not Found"));

        return ResponseEntity.ok(response);

    }
    @PostMapping
    public ResponseEntity<OrderPostResponse> create(@RequestBody OrderPostRequest orderPostRequest) {
        log.info("request received to create order: '{}'", orderPostRequest);

        var order = MAPPER.toOrder(orderPostRequest);
        Order.getOrders().add(order);
        var response = MAPPER.toOrderPostResponse(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        log.info("request received to delete order: '{}'", id);

        var orderToRemove = Order.getOrders().stream()
                .filter(order -> order.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not Found"));
        Order.getOrders().remove(orderToRemove);

        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody OrderPutRequest orderPutRequest){
        log.info("request received to update order: '{}'", orderPutRequest);

        var orderToRemove = Order.getOrders().stream()
                .filter(order -> order.getOrderId().equals(orderPutRequest.getOrderId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not Found"));

        var orderUpdate = MAPPER.toOrder(orderPutRequest, orderToRemove.getDeliveryDate(), orderToRemove.getPurchaseDate());
        Order.getOrders().add(orderUpdate);
        Order.getOrders().remove(orderToRemove);

        return ResponseEntity.noContent().build();
    }

}
