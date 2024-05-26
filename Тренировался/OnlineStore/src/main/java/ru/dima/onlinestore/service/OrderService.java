package ru.dima.onlinestore.service;


import org.springframework.transaction.annotation.Transactional;
import ru.dima.onlinestore.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    @Transactional
    Order addOrder(Order order);

    @Transactional
    Order updateOrder(Order order);

    @Transactional
    void deleteOrder(Order order);
}