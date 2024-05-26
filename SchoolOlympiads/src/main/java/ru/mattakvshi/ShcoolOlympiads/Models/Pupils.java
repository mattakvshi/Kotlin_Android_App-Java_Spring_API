package ru.mattakvshi.ShcoolOlympiads.Models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import ru.mattakvshi.ShcoolOlympiads.TypeConverters.DateToLongSerializer;
import ru.mattakvshi.ShcoolOlympiads.TypeConverters.LongToDateDeserializer;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name="pupils", indexes = {@Index(name = "index_pupils_id", columnList = "pupils_id"), @Index(name = "index_pupils_olympiads_id", columnList = "olympiad_id")})
public class Pupils {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")  //Здесь мы не генерируем его, так как генерируем в мобилке
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "pupils_id", updatable = false, nullable = false)
    private UUID id;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "olympiad_id") //Идентификатор олимпиады
    private UUID olympiadID;

    @Column(name = "pupils_name") //Имя школьника
    private String pupilsName;

    @Column(name = "pupils_region") //Регион школьника
    private String pupilsRegion;

    @Column(name = "pupils_phone") //Телефон школьника
    private String pupilsPhone;

    @Column(name = "pupils_grade") //Класс школьника
    private Integer pupilsGrade;

    @JsonSerialize(using = DateToLongSerializer.class)
    @JsonDeserialize(using = LongToDateDeserializer.class)
    @Column(name = "event_date")  //Дата участия в олимпиаде
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;

    @Column(name = "school_subject") //Школьный предмет
    private String schoolSubject;

    @Column(name = "pupils_scores") //Баллы школьника
    private Integer pupilsScores;

    @Column(name = "pupils_mark") //Оценка школьника
    private Integer pupilsMark;
}