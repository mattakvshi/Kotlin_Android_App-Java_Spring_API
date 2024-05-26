package ru.mattakvshi.ShcoolOlympiads.RequestDividers;

import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ru.mattakvshi.ShcoolOlympiads.Models.Pupils;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PupilsActionRequest {
    private int action;
    private Pupils pupils;
}
