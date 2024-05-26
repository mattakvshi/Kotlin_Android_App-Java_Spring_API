package ru.mattakvshi.ShcoolOlympiads.Service;


import ru.mattakvshi.ShcoolOlympiads.Models.Olympiads;

import javax.transaction.Transactional;
import java.util.List;

public interface OlympiadsService {
    List<Olympiads> getAllOlympiads();

    @Transactional
    Olympiads addOlympiads(Olympiads olympiads);

    @Transactional
    Olympiads updateOlympiads(Olympiads olympiads);
    @Transactional
    void deleteOlympiads(Olympiads olympiads);
}

