package ru.mattakvshi.ShcoolOlympiads.RequestDividers;

import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ru.mattakvshi.ShcoolOlympiads.Models.Olympiads;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OlympiadsActionRequest {
    private int action;
    private Olympiads olympiads;
}