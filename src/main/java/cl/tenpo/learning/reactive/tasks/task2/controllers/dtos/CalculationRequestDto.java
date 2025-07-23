package cl.tenpo.learning.reactive.tasks.task2.controllers.dtos;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequestDto {

    @NonNull
    private Double number1;
    @NonNull
    private Double number2;


}
