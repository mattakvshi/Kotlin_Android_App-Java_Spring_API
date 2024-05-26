package ru.mattakvshi.ShcoolOlympiads.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mattakvshi.ShcoolOlympiads.Models.Olympiads;
import ru.mattakvshi.ShcoolOlympiads.Models.Pupils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Slf4j
@Service
public class ServicesImpl implements OlympiadsService, PupilsService{

    @PersistenceContext
    private EntityManager entityManager;


    //DEPARTMENTS

    @Override
    public List<Olympiads> getAllOlympiads() {
        String sql = "FROM Olympiads";
        List<Olympiads> data = entityManager.createQuery(sql, Olympiads.class).getResultList();
        System.out.println(data);
        return data;
    }

    @Transactional
    @Override
    public Olympiads addOlympiads(Olympiads olympiads) {
        entityManager.persist(olympiads);
        log.info("Новая запись в таблицу Olympiads вставлена!");
        return olympiads;
    }

    @Transactional
    @Override
    public Olympiads updateOlympiads(Olympiads olympiads) {
        entityManager.merge(olympiads);
        log.info("Запись в таблице Olympiads изменена!");
        return olympiads;
    }

    @Transactional
    @Override
    public void deleteOlympiads(Olympiads olympiads) {
        entityManager.remove(entityManager.contains(olympiads) ? olympiads : entityManager.merge(olympiads));
        log.info("Запись в таблице Olympiads удалена!");
    }

    //ORDERS

    @Override
    public List<Pupils> getAllPupils() {
        String sql = "FROM Pupils";
        List<Pupils> data = entityManager.createQuery(sql, Pupils.class).getResultList();
        System.out.println(data);
        return data;
    }

    @Transactional
    @Override
    public Pupils addPupils(Pupils pupils) {
        entityManager.persist(pupils);
        log.info("Новая запись в таблицу Pupils вставлена!");
        return pupils;
    }

    @Transactional
    @Override
    public Pupils updatePupils(Pupils pupils) {
        entityManager.merge(pupils);
        log.info("Запись в таблице Pupils изменена!");
        return pupils;
    }

    @Transactional
    @Override
    public void deletePupils(Pupils pupils) {
        entityManager.remove(entityManager.contains(pupils) ? pupils : entityManager.merge(pupils));
        log.info("Запись в таблице Pupils удалена!");
    }
}
