package ru.dima.onlinestore.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.dima.onlinestore.RequestDividers.DepartmentActionRequest;
import ru.dima.onlinestore.RequestDividers.OrderActionRequest;
import ru.dima.onlinestore.models.Department;
import ru.dima.onlinestore.models.Order;
import ru.dima.onlinestore.service.DepartmentService;
import ru.dima.onlinestore.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping( "/onlinestore")
public class OnlineStoreController {

    private final DepartmentService departmentService;
    private final OrderService orderService;

    @Autowired
    public OnlineStoreController(DepartmentService departmentService, OrderService orderService) {
        this.departmentService = departmentService;
        this.orderService = orderService;
    }

//    @GetMapping( "/departments")
//    public List<Department> getAllDepartments() {
//        List<Department> response = departmentService.getAllDepartments();
//        System.out.println(response);
//        return response;
//    }
//
//    @GetMapping("/orders")
//    public List<Order> getAllOrders() {
//        return orderService.getAllOrders();
//    }

//        @GetMapping( "/departments")
//    public ResponseEntity<?> getAllDepartments() {
//        return new ResponseEntity<> (departmentService.getAllDepartments(), HttpStatus.OK);
//    }

    @GetMapping( "/departments")
    public ResponseEntity<?> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        Map<String, List<Department>> response = new HashMap<>();
        response.put("result", departments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<> (orderService.getAllOrders(), HttpStatus.OK);
    }


    @PostMapping("/departments")
    public ResponseEntity<?> handleDepartmentPost(@RequestBody DepartmentActionRequest request) {
        int action = request.getAction();
        Department department = request.getDepartment();

        switch (action) {
            case 11:
                return new ResponseEntity<>(departmentService.addDepartment(department), HttpStatus.CREATED);
            case 12:
                return new ResponseEntity<>(departmentService.updateDepartment(department), HttpStatus.OK);
            case 13:
                departmentService.deleteDepartment(department);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> handleOrderPost(@RequestBody OrderActionRequest request) {
        int action = request.getAction();
        Order order = request.getOrder();

        switch (action) {
            case 11:
                return new ResponseEntity<>(orderService.addOrder(order), HttpStatus.CREATED);
            case 12:
                return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
            case 13:
                orderService.deleteOrder(order);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
