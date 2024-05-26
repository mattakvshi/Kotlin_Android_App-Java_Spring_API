package ru.dima.onlinestore.RequestDividers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ru.dima.onlinestore.models.Department;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DepartmentActionRequest {
    private int action;
    private Department department;
}