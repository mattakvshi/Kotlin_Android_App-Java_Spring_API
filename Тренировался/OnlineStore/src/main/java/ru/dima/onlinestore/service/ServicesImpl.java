package ru.dima.onlinestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.onlinestore.models.Department;
import ru.dima.onlinestore.models.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Slf4j
@Service
public class ServicesImpl implements DepartmentService, OrderService{

    @PersistenceContext
    private EntityManager entityManager;


    //DEPARTMENTS

    @Override
    public List<Department> getAllDepartments() {
        String sql = "FROM Department";
        List<Department> data = entityManager.createQuery(sql, Department.class).getResultList();
        System.out.println(data);
        return data;
    }

    @Transactional
    @Override
    public Department addDepartment(Department department) {
        entityManager.persist(department);
        log.info("Новая запись в таблицу Department вставлена!");
        return department;
    }

    @Transactional
    @Override
    public Department updateDepartment(Department department) {
        entityManager.merge(department);
        log.info("Запись в таблице Department изменена!");
        return department;
    }

    @Transactional
    @Override
    public void deleteDepartment(Department department) {
        entityManager.remove(entityManager.contains(department) ? department : entityManager.merge(department));
        log.info("Запись в таблице Department удалена!");
    }

    //ORDERS

    @Override
    public List<Order> getAllOrders() {
        String sql = "FROM Order";
        List<Order> data = entityManager.createQuery(sql, Order.class).getResultList();
        System.out.println(data);
        return data;
    }

    @Transactional
    @Override
    public Order addOrder(Order order) {
        entityManager.persist(order);
        log.info("Новая запись в таблицу Order вставлена!");
        return order;
    }

    @Transactional
    @Override
    public Order updateOrder(Order order) {
        entityManager.merge(order);
        log.info("Запись в таблице Order изменена!");
        return order;
    }

    @Transactional
    @Override
    public void deleteOrder(Order order) {
        entityManager.remove(entityManager.contains(order) ? order : entityManager.merge(order));
        log.info("Запись в таблице Order удалена!");
    }
}
