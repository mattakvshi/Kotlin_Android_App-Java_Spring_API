package ru.dima.onlinestore.RequestDividers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ru.dima.onlinestore.models.Order;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderActionRequest {
    private int action;
    private Order order;
}
