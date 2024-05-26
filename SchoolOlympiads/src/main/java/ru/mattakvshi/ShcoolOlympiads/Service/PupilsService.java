package ru.mattakvshi.ShcoolOlympiads.Service;


import org.springframework.transaction.annotation.Transactional;
import ru.mattakvshi.ShcoolOlympiads.Models.Pupils;

import java.util.List;

public interface PupilsService {
    List<Pupils> getAllPupils();

    @Transactional
    Pupils addPupils(Pupils pupils);

    @Transactional
    Pupils updatePupils(Pupils pupils);

    @Transactional
    void deletePupils(Pupils pupils);
}