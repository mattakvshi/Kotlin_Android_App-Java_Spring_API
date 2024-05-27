package ru.dima.onlinestore.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.dima.onlinestore.typeconverters.DateToLongSerializer;
import ru.dima.onlinestore.typeconverters.LongToDateDeserializer;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name="orders", indexes = {@Index(name = "index_order_id", columnList = "id"), @Index(name = "index_order_department_id", columnList = "department_id")})
public class Order {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "department_id")
    private UUID departmentID;

    @JsonSerialize(using = DateToLongSerializer.class)
    @JsonDeserialize(using = LongToDateDeserializer.class)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "address")
    private String address;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "pay_method")
    private int payMethod;

    @Column(name = "product_count")
    private int count;

    //@ManyToOne
    //@JoinColumn(name="department_id", referencedColumnName="id", insertable = false, updatable = false)
    //private Department department;
}