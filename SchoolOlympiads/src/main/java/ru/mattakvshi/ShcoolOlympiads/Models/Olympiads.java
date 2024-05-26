package ru.mattakvshi.ShcoolOlympiads.Models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name="olympiads", indexes = @Index(name = "index_olympiads_id", columnList="olympiad_id"))
public class Olympiads {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")  //Здесь мы не генерируем его, так как генерируем в мобилке
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "olympiad_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name="olympiad_name")
    private String olympiadName;
}