package ru.dima.onlinestore.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name="departments", indexes = @Index(name = "index_department_id", columnList="id"))
public class Department {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name="department_name")
    private String name;
}