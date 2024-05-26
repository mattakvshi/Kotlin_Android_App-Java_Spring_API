package ru.dima.onlinestore.service;


import ru.dima.onlinestore.models.Department;

import javax.transaction.Transactional;
import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    @Transactional
    Department addDepartment(Department department);

    @Transactional
    Department updateDepartment(Department department);
    @Transactional
    void deleteDepartment(Department department);
}

